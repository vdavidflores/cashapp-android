package com.kupay;






import java.security.SecureRandom;

import org.json.JSONException;
import org.json.JSONObject;

import com.kupay.Post.OnResponseAsync;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;




public class Enlazar extends FragmentActivity {
    
	Button acept;
	Button cancel;


	EditText correo;
	EditText pin;
	EditText pin2;
	EditText puk;
	Post enlaz;
	String imei;
	String usr;
	RandomString random;
	Post enviarLlave;
	Post enviarNuevaConfirmacion;
	Button enviarBtn;
	private ProgressDialog progress;
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("inicio", "0.5");
        setContentView(R.layout.enlazar);
        Log.v("inicio", "1");
        //madres de seguridad random(no se que pedo)
        PRNGFixes.apply();
        
        random = new RandomString(64);
      enviarBtn = (Button) findViewById(R.id.codigoAcceso);
        // Tomar los datos de los campos
        acept = (Button) findViewById(R.id.aceptar_r);
   cancel = (Button) findViewById(R.id.iniciar); 
   Log.v("inicio", "2.0");  

   Log.v("inicio", "2.1");
   correo = (EditText) findViewById(R.id.correo_i);
   Log.v("inicio", "2.2");
   pin = (EditText) findViewById(R.id.pin_i);
   Log.v("inicio", "2.3");
   pin2 = (EditText) findViewById(R.id.pin_dos);
   Log.v("inicio", "2.4");
   puk = (EditText) findViewById(R.id.contrasenia);
   Log.v("inicio", "2.5");
   enlaz = new Post();
   enviarNuevaConfirmacion = new Post();
   Log.v("inicio", "2.6");
   enviarLlave = new Post();
	
	Log.v("inicio", "3"); 
	eventos();
	Log.v("inicio", "4");    
	
    }
    
    private void  errorEnlaze(String mensaje) {
    	AlertDialog.Builder builder_ = new AlertDialog.Builder(this);	
		builder_.setIcon(R.drawable.ku72);
		builder_.setTitle("Acceso denegado");
		builder_.setMessage(mensaje);
		Log.v("app", "noniendo botones...");
		builder_.setNeutralButton("Aceptar", null);
		builder_.setPositiveButton("Enviar correo de confirmación", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				JSONObject jso= new JSONObject();
				try {
					jso.put("usr", correo.getText().toString());
					enviarNuevaConfirmacion.setData(19, jso);
					enviarNuevaConfirmacion.execAsync(getApplicationContext());
					Toast.makeText(getApplicationContext(), "Correo enviado", Toast.LENGTH_SHORT).show();
					
				}catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		Log.v("app", "botones puestos");
		AlertDialog ad = builder_.create();
		ad.show();
	}
    
    private void  errorEnvio(String mensaje) {
    	AlertDialog.Builder builder_ = new AlertDialog.Builder(this);	
		builder_.setIcon(R.drawable.ku72);
		builder_.setTitle("Imposible enviar llave de acceso");
		builder_.setMessage(mensaje);
		
		builder_.setNeutralButton("Aceptar", null);
		Log.v("app", "botones puestos");
		AlertDialog ad = builder_.create();
		ad.show();
	}
    
    private void  preguntaEnvio() {
    	AlertDialog.Builder builder_ = new AlertDialog.Builder(this);	
		builder_.setIcon(R.drawable.ku72);
		builder_.setTitle("Enviar llave de acceso");
		builder_.setMessage("Deseas enviar una nueva llave de acceso a "+correo.getEditableText().toString());
		builder_.setNegativeButton("Cancelar", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		builder_.setPositiveButton("Aceptar", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				enviarBtn.setActivated(false);	
				JSONObject datallave = new JSONObject();
				progress = ProgressDialog.show(Enlazar.this, "Enviando", "Se esta enviando un nuevo código de acceso.");
				try {
					datallave.put("usr", correo.getText().toString());
					enviarLlave.setData(15, datallave);
					enviarLlave.execAsync(getApplicationContext());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		Log.v("app", "botones puestos");
		AlertDialog ad = builder_.create();
		ad.show();
	}
    
 private void eventos(){
	enviarLlave.setOnResponseAsync(new OnResponseAsync() {
		
		@Override
		public void onResponseAsync(JSONObject response) {
			enviarBtn.setActivated(true);	
			progress.dismiss();
			try {
				if(response.getString("RESULTADO").equals("EXITO")){
				
					Toast.makeText(getApplicationContext(), "código de acceso enviado!", Toast.LENGTH_LONG).show();
				}else{
					JSONObject datos = response.getJSONObject("DATOS");
					errorEnvio(datos.getString("MENSAJE"));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	});
		
		
	 enlaz.setOnResponseAsync(new OnResponseAsync() {
		
		@Override
		public void onResponseAsync(JSONObject response) {
			Log.v("app", "0");
			progress.dismiss();
				try {
					if(response.getString("RESULTADO").equals("EXITO")){
						nuevoUsurio(usr, getApplicationContext());
						Log.v("app", "1");
						nuevoImei(imei, getApplicationContext());
						Log.v("app", "2");
						Intent in = new Intent(getApplicationContext(), MainConteiner.class);
						Log.v("app", "3");
						startActivity(in);
						
						try {
							finishAffinity();
						} catch (Throwable e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else if(response.getString("RESULTADO").equals("FALLA")){
					Log.v("app", "error de enlaze");
						JSONObject data = response.getJSONObject("DATOS"); 
						errorEnlaze(data.getString("MENSAJE").toString());
					
					}else{
						Log.v("app", "Error en elnaze!");
					
						
						Toast.makeText(getApplicationContext(), "falla en enlaze", Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			
		}
	});
	 
	 
	 enviarBtn.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			
			if (!correo.getText().toString().equals(""))
			{	
				preguntaEnvio();
				
			}else{

				Toast.makeText(getApplicationContext(), "Ingresa un correo electronico", Toast.LENGTH_LONG).show();
			}
			
		}
	});
			
	 acept.setOnClickListener(new View.OnClickListener() {
				
		       	
				@Override
				public void onClick(View view) {
					
					if (!correo.getText().toString().equals("")&&
	    					!pin.getText().toString().equals("")&&
	    					!puk.getText().toString().equals("") &&
	    					pin2.getText().toString().equals(pin.getText().toString()))
					{		
						imei = random.nextString();
						usr =correo.getText().toString();
					
						JSONObject data = new JSONObject();
						try {
			    			
			    				data.put("usr",correo.getText().toString());
			    			
			    			data.put("imei",imei);
			    			data.put("pin", pin.getText().toString());
			    			data.put("pass",puk.getText().toString());
			    			data.put("rol1", "4");
			    			data.put("rol2", "5");
			    			
			    			} catch (JSONException e) {
			    				// TODO Auto-generated catch block
			    				e.printStackTrace();
			    			}
						
						enlaz.setData(14, data);
						enlaz.execAsync(getApplicationContext());
						Log.v("app", "lanzando progres");
						progress = ProgressDialog.show(Enlazar.this, "Validando datos", "En un momento estaremos listos.");
				}
			
				else{
					
				Toast.makeText(getApplicationContext(), "Campos incorectos, favor de llenar todos los campos", Toast.LENGTH_LONG).show();
					
					
				}

				}
					
			});
			
	 
	 
	 cancel.setOnClickListener(new View.OnClickListener() {
				
		       	
				@Override
				public void onClick(View view) {
					
						finish();

				}


			});

   
   }
 
 private void nuevoUsurio(String dat,Context cont){
     BDD dbh = new BDD(cont,"kupay",null,1);
     SQLiteDatabase db= dbh.getWritableDatabase();
     db.execSQL("UPDATE kupay SET usr='"+dat+"'");
   }
 private void nuevoImei(String dat,Context cont){
     BDD dbh = new BDD(cont,"kupay",null,1);
     SQLiteDatabase db= dbh.getWritableDatabase();
     db.execSQL("UPDATE kupay SET imei='"+dat+"'");
   }
   
}
