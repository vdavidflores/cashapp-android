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
	EditText email;
	EditText contrasena;
	EditText contraseña2;
	
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
   
   celular = (EditText) findViewById(R.id.celular);
	nombre = (EditText) findViewById(R.id.nombreReg);
	email = (EditText) findViewById(R.id.mail);
	Log.v("registro", "3");
	contrasena = (EditText) findViewById(R.id.pasword);
	contrasena.setTransformationMethod(PasswordTransformationMethod.getInstance());
	contraseña2 = (EditText) findViewById(R.id.pasword2);
	contraseña2.setTransformationMethod(PasswordTransformationMethod.getInstance());
   
	Log.v("registro", "4");
        
	/*  acept.setOnClickListener(new View.OnClickListener() {*/
    		
           	//das click en aceptar
    		/*@Override
    		public void onClick(View view) {
    		//Mandas jason al servidor	
    			if (!celular.getText().toString().equals("")&&
    					!nombre.getText().toString().equals("")&&
    					!email.getText().toString().equals("")&&
    					!contrasena.getText().toString().equals("")&&
    					!contraseña2.getText().toString().equals("")){
    				if (contrasena.getText().toString().equals(contraseña2.getText().toString())){*/
    		//			registrando a = new registrando();
            //			a.execute();
    				/*}else{
        				
        				Toast.makeText(getApplicationContext(), "Contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        			}
    				
    			}else{
    				
    				Toast.makeText(getApplicationContext(), "Faltan campos por llenar", Toast.LENGTH_SHORT).show();
    			}
    			
    			
    			
    			
    		
    		}

    	}); 
    
cancel.setOnClickListener(new View.OnClickListener() {
    		
           	
    		@Override
    		public void onClick(View view) {
    		
    			Intent in = new Intent (getApplicationContext(), MainActivity.class);
    			startActivity(in);
    			try {
    				this.finalize();
    			} catch (Throwable e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    		}

    	}); 
    
    
    
    
    
    
    
    
    
    }
   */ 
    
   /* private void registrar(String email,String contrasena, String celular,String nombre, String id){
		Context con = getApplicationContext();
		cambiaNombre(nombre, con);
		cambiaEmail(email, con);
		cambiaCel(celular, con);
		cambiaPass(contrasena,con);
		CambiaId(id, con);
	}
	
    private void cambiaNombre(String nombre,Context cont){
        BD dbh = new BD(cont,"taxihot",null,1);
        SQLiteDatabase db= dbh.getWritableDatabase();
        db.execSQL("UPDATE taxihot SET nombre='"+nombre+"'");
      }
   
    private void cambiaEmail(String email,Context cont){
        BD dbh = new BD(cont,"taxihot",null,1);
        SQLiteDatabase db= dbh.getWritableDatabase();
        db.execSQL("UPDATE taxihot SET email='"+email+"'");
      }
    private void cambiaCel(String cel,Context cont){
        BD dbh = new BD(cont,"taxihot",null,1);
        SQLiteDatabase db= dbh.getWritableDatabase();
        db.execSQL("UPDATE taxihot SET cel='"+cel+"'");
      }
    private void cambiaPass(String pass,Context cont){
        BD dbh = new BD(cont,"taxihot",null,1);
        SQLiteDatabase db= dbh.getWritableDatabase();
        db.execSQL("UPDATE taxihot SET pass='"+pass+"'");
      }
    private void CambiaId(String id,Context cont){
        BD dbh = new BD(cont,"taxihot",null,1);
        SQLiteDatabase db= dbh.getWritableDatabase();
        db.execSQL("UPDATE taxihot SET ident='"+id+"'");
      }
    */
    
    
    
    
    
    /*private class registrando extends AsyncTask<Void, Void, JSONObject>{
    	ProgressDialog	dialog;
    	@Override
        protected void onPreExecute() {
    		dialog 	 = ProgressDialog.show(Registro.this, "Registrando usuario", "Espere un momento...");
    	}

		@SuppressWarnings("null")
		@Override
		protected JSONObject doInBackground(Void... params) {
			Log.v("respuestas", "1");
					
			
			JSONObject datosq = new JSONObject();
			Log.v("respuestas", "2");
			try {
				datosq.put("nombre",nombre.getText().toString());
				datosq.put("usr",email.getText().toString());
				datosq.put("password",contrasena.getText().toString());
				datosq.put("telefono",celular.getText().toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.v("respuestas", "3");
			
			JSONObject respuestaq = new JSONObject();
			Log.v("respuestas", "4");
			Post reg = new Post("REGISTRO_USUARIO", datosq );
			try {
Log.v("respuestas", "5");
				respuestaq = reg.exec(getApplication().getApplicationContext());
				Log.v("respuestas", "6");
				Log.v("respuestas",respuestaq.toString());
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			// TODO Auto-generated method stub
			return respuestaq;
			
		}
		

        @Override
          protected void onPostExecute(JSONObject response) {
        	dialog.dismiss();
        	 try {
				String resultado = response.getString("RESULTADO");
				//cambias a clase iniciar
    			if (resultado.equals("EXITO")){
    				JSONObject datos = new JSONObject();
    				datos =	response.getJSONObject("DATOS");
				String nombrej = datos.getString("NOMBRE");
				String celularj = datos.getString("TELEFONO");
				String emailj = datos.getString("USUARIO");
				String idj = datos.getString("ID");
    				
    				registrar(emailj.toString(),"", celularj.toString(), nombrej.toString(), idj.toString());
    				Intent in = new Intent (getApplicationContext(), MainActivity.class);
    				in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    				in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        			startActivity(in);
        			try {
        				finish();
        			} catch (Throwable e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}
        			   				
    			}
    			else {
					Toast.makeText(getApplicationContext(), "No se pudo registrar el usuario", Toast.LENGTH_LONG).show();
				}
				
				
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }*/
    }
   
}
