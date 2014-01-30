package com.kupay;






import java.security.SecureRandom;

import org.json.JSONException;
import org.json.JSONObject;

import com.kupay.Post.OnResponseAsync;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;




public class Enlazar extends Activity {
    
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
	
	@Override
	public void onBackPressed() {
	// Do nothing
		
	}
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("inicio", "0.5");
        setContentView(R.layout.enlazar);
        Log.v("inicio", "1");
        //madres de seguridad random(no se que pedo)
        PRNGFixes.apply();
        
        random = new RandomString(64);
      
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
		/*builder_.setPositiveButton("Enviar llave-ku", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				enviarBtn.callOnClick();
			}
		});*/
		Log.v("app", "botones puestos");
		AlertDialog ad = builder_.create();
		ad.show();
	}
    
 private void eventos(){
	enviarLlave.setOnResponseAsync(new OnResponseAsync() {
		
		@Override
		public void onResponseAsync(JSONObject response) {
		//	enviarBtn.setActivated(true);	
			try {
				if(response.getString("RESULTADO").equals("EXITO")){
					Toast.makeText(getApplicationContext(), "llave ku enviada!", Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	});
		
		// Boton de solicitar Taxis	
	 enlaz.setOnResponseAsync(new OnResponseAsync() {
		
		@Override
		public void onResponseAsync(JSONObject response) {
			Log.v("app", "0");
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
							finish();
						} catch (Throwable e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else if(response.getString("RESULTADO").equals("FALLA")){
					JSONObject data = response.getJSONObject("DATOS"); 
						errorEnlaze(data.getString("MENSAJE"));
					
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
	 
	 
	/* enviarBtn.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			
			if (!correo.getText().toString().equals(""))
			{	enviarBtn.setActivated(false);	
				JSONObject datallave = new JSONObject();
				
				try {
					datallave.put("usr", correo.getText().toString());
					enviarLlave.setData(15, datallave);
					enviarLlave.execAsync(getApplicationContext());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}else{

				Toast.makeText(getApplicationContext(), "Ingresa un correo electronico", Toast.LENGTH_LONG).show();
			}
			
		}
	});*/
			
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
					Toast.makeText(getApplicationContext(), "Registrado...", Toast.LENGTH_LONG).show();
				
					
					

				}
			
				else{
					
				Toast.makeText(getApplicationContext(), "Campos incorectos, favor de llenar todos los campos", Toast.LENGTH_LONG).show();
					
					
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
