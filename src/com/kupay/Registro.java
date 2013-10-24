package com.kupay;


import java.util.jar.JarOutputStream;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class Registro extends Activity {
    
	Button acept;
	Button cancel;
	EditText celular;
	EditText nombre;
	EditText mail;
	EditText contrasena;
	EditText contraseña2;
	EditText apellido;
	EditText ndia;
	EditText nmes;
	EditText nano;
	
	@Override
	public void onBackPressed() {
	// Do nothing
	}
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
  
       
   celular = (EditText) findViewById(R.id.telefono_registro);
   nombre = (EditText) findViewById(R.id.nombre_registro);
   apellido = (EditText) findViewById(R.id.apellido_registro);
   mail = (EditText) findViewById(R.id.mail_registro);
   ndia = (EditText) findViewById(R.id.ndia_registro);
   nmes = (EditText) findViewById(R.id.nmes_registro);
   nano = (EditText) findViewById(R.id.nano_registro);
    
   eventos();
   }
    
private void eventos(){
		
		
		// Boton de solicitar Taxis	
			
			
	 acept.setOnClickListener(new View.OnClickListener() {
				
		       	
				@Override
				public void onClick(View view) {
					
					Log.v("registro", "2.5");
					
					if (!celular.getText().toString().equals("")&&
	    					!nombre.getText().toString().equals("")&&
	    					!apellido.getText().toString().equals("")&&
	    					!mail.getText().toString().equals("")&&
	    					!celular.getText().toString().equals("")&&
	    					!ndia.getText().toString().equals("")&&
	    					!nmes.getText().toString().equals("")&&
	    					!nano.getText().toString().equals(""))
					{	
							Log.v("registro", "3");
						
							if (Integer.parseInt(nano.getText().toString()) < (int)1900 ){
								Log.v("registro", "4");	
								Toast.makeText(getApplicationContext(), "Año de nacimiento incorrecto", Toast.LENGTH_LONG).show();
										
							}
							else if (Integer.parseInt(ndia.getText().toString()) > (int)31){
								Log.v("registro", "6");
								Toast.makeText(getApplicationContext(), "Día incorrecto", Toast.LENGTH_LONG).show();							
							}
							else if (Integer.parseInt(nmes.getText().toString()) > (int)12){
								Log.v("registro", "5");
								Toast.makeText(getApplicationContext(), "Mes incorrecto", Toast.LENGTH_LONG).show();			
									
							}
							else {
							// TODO Auto-generated method stub
							Intent in = new Intent (getApplicationContext(), Registro_seguridad.class);
							startActivityForResult(in, 2);
					
							try {
								this.finalize();
							} catch (Throwable e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					
							Toast.makeText(getApplicationContext(), "Datos Registrados", Toast.LENGTH_LONG).show();
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
					// TODO Auto-generated method stub
					Intent in = new Intent (getApplicationContext(), Pregunta.class);
					startActivityForResult(in, 2);
					
					try {
						this.finalize();
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Log.v("TC","4ma");
				}


			});

   
   }
    
}
    

