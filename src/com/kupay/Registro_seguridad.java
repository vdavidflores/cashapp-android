package com.kupay;






import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;




public class Registro_seguridad extends Activity {
    
	Button acept;
	Button cancel;

	EditText correo;
	EditText pin;
	EditText puk;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("inicio", "0.5");
        setContentView(R.layout.registro_seguridad);
        Log.v("inicio", "1");
        
        // Tomar los datos de los campos
acept = (Button) findViewById(R.id.aceptar_r);
   cancel = (Button) findViewById(R.id.iniciar); 
   Log.v("inicio", "2");  
   
   correo = (EditText) findViewById(R.id.celular);
	pin = (EditText) findViewById(R.id.nombreReg);
	puk = (EditText) findViewById(R.id.mail);
	
	eventos();
        
	
    }
    
 private void eventos(){
		
		
		// Boton de solicitar Taxis	
			
			
	 acept.setOnClickListener(new View.OnClickListener() {
				
		       	
				@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub
					Intent in = new Intent (getApplicationContext(), Inicio_sesion.class);
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
