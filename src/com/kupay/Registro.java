package com.kupay;


import java.util.Calendar;
import java.util.jar.JarOutputStream;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import com.kupay.Post.OnResponseAsync;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import  android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.FeatureInfo;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;



public class Registro extends FragmentActivity {
    
	Button acept;
	Button cancel;
	//EditText celular;
	EditText nombre;
	EditText mail;
	EditText contrasena;
	EditText contraseña2;
	EditText apellido;
	//EditText ndia;
	//EditText pass1,pass2;
	//EditText nmes;
//	EditText nano;
	Post registro;
	 Button fechaNas;
	static int diaNas;
	static int mesNas;
	static int anioNas;
	private ProgressDialog progress;
	
	DatePickerDialog dpd;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("registro", "0.5");
        setContentView(R.layout.registro);
        Log.v("registro", "1");
        
        // Tomar los datos de los campos
   acept = (Button) findViewById(R.id.aceptar_r);
   cancel = (Button) findViewById(R.id.iniciar); 
   Log.v("registro", "2");
  
  // pass1 = (EditText) findViewById(R.id.pass1);
  // pass2 = (EditText) findViewById(R.id.pass2);
   //celular = (EditText) findViewById(R.id.telefono_registro);
   nombre = (EditText) findViewById(R.id.nombre_registro);
   apellido = (EditText) findViewById(R.id.apellido_registro);
   mail = (EditText) findViewById(R.id.mail_registro);
 //  ndia = (EditText) findViewById(R.id.ndia_registro);
  // nmes = (EditText) findViewById(R.id.nmes_registro);
  // nano = (EditText) findViewById(R.id.nano_registro);
   fechaNas = (Button) findViewById(R.id.buttonFecha);
   
   registro = new Post();
   eventos();
   }
    
private void eventos(){
		
		
	fechaNas.setOnClickListener(new View.OnClickListener() {
		
	
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			 DialogFragment newFragment = new DatePickerFragment();
             newFragment.show(getSupportFragmentManager(), "datePicker");
		}
	});
			
			
	 acept.setOnClickListener(new View.OnClickListener() {
				
		       	
				@Override
				public void onClick(View view) {
					
					Log.v("registro", "2.5");
					
					if (//!celular.getText().toString().equals("")&&
	    					!nombre.getText().toString().equals("")&&
	    					!apellido.getText().toString().equals("")&&
	    					!mail.getText().toString().equals("")&&
	    					//!celular.getText().toString().equals("")&&
	    					(anioNas!=0)&&
	    					(diaNas!=0)&&
	    					(mesNas !=0)//&&
	    				//	!pass1.getText().toString().equals("")&&
	    				//	!pass2.getText().toString().equals("")&&
	    				//	pass2.getText().toString().equals(pass1.getText().toString())
	    					)
					{	
							Log.v("registro", "3");
						
							if (anioNas < (int)1900 ){
								Log.v("registro", "4");	
								Toast.makeText(getApplicationContext(), "Año de nacimiento incorrecto", Toast.LENGTH_LONG).show();
										
							}
							else if (diaNas > (int)31){
								Log.v("registro", "6");
								Toast.makeText(getApplicationContext(), "Día incorrecto", Toast.LENGTH_LONG).show();							
							}
							else if (mesNas > (int)12){
								Log.v("registro", "5");
								Toast.makeText(getApplicationContext(), "Mes incorrecto", Toast.LENGTH_LONG).show();			
									
							}
							else {
								
								JSONObject data = new JSONObject();
								try {
					    			
					    				data.put("nombre",nombre.getText().toString());
					    			
					    			data.put("apellido",apellido.getText().toString());
					    			data.put("email", mail.getText().toString());
					    			data.put("fechaNas", Integer.toString(anioNas).toString()+"-"+Integer.toString(mesNas).toString()+"-"+Integer.toString(diaNas).toString());
					    			data.put("telefono", "");
					    			//data.put("pass", pass1.getText().toString());
					    			
					    			} catch (JSONException e) {
					    				// TODO Auto-generated catch block
					    				e.printStackTrace();
					    			}
								progress = ProgressDialog.show(Registro.this, "Registrando usuario", "cuenta hasta 10! ...1..");
								registro.setData(2, data);
								registro.execAsync(getApplicationContext());
							Toast.makeText(getApplicationContext(), "Registrado...", Toast.LENGTH_LONG).show();
							}
							
							//Toast.makeText(getApplicationContext(), "Si funciona", Toast.LENGTH_LONG).show();
					}
					else{
						
					Toast.makeText(getApplicationContext(), "Campos vacios, favor de llenar todos los campos", Toast.LENGTH_LONG).show();
					}
				}			
			});
	 
	 

			
	 cancel.setOnClickListener(new View.OnClickListener() {
				
		       	
				@Override
				public void onClick(View view) {
					finish();
				}


			});
	
	 registro.setOnResponseAsync(new OnResponseAsync() {
		
		@Override
		public void onResponseAsync(JSONObject response) {
			// TODO Auto-generated method stub
			progress.dismiss();
			try {
				if(response.getString("RESULTADO").equals("EXITO")){
					exitoRegistro();
				}else{
					Toast.makeText(getApplicationContext(), "Datos no validos", Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	});

   
   }

private void  exitoRegistro() {
	AlertDialog.Builder adb =new  AlertDialog.Builder(this);
	adb.setTitle("Registro exitoso");
	adb.setMessage("Gracias por registrarte en kupay. recibiras un email de confirmacion.");
	adb.setNeutralButton("Acepar", new OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub

			Intent in = new Intent (getApplicationContext(), Enlazar.class);
			startActivityForResult(in, 2);
			
			try {
				finish();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	});
AlertDialog ad = adb.create();
ad.show();
	
}

public void cambiarFechaBoton(String fecha){
	fechaNas.setText(fecha);
}



public  class DatePickerFragment extends DialogFragment
implements DatePickerDialog.OnDateSetListener {

    public EditText editText;
    DatePicker dpResult;

public Dialog onCreateDialog(Bundle savedInstanceState) {
// Use the current date as the default date in the picker
final Calendar c = Calendar.getInstance();
int year = c.get(Calendar.YEAR);
int month = c.get(Calendar.MONTH);
int day = c.get(Calendar.DAY_OF_MONTH);
DatePickerDialog DP = new DatePickerDialog(getActivity(), android.R.style.Theme_DeviceDefault_Dialog, this, year, month, day);
DP.getWindow().setBackgroundDrawable(new ColorDrawable(0));

return DP;
}

public void onDateSet(DatePicker view, int year, int month, int day) {
	diaNas = day;
	mesNas = month+1;
	anioNas =year;
	cambiarFechaBoton(Integer.toString(diaNas).toString()+"-"+Integer.toString(mesNas).toString()+"-"+Integer.toString(anioNas));
   
}
}

	
    
}
    

