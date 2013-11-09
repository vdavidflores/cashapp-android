package com.kupay;







import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;




public class Registro_seguridad extends Activity {
    
	Button acept;
	Button cancel;
	int pin1;
	int pin2;
	EditText pin_uno;
	EditText pin_dos;
	EditText puk;
	
	@Override
	public void onBackPressed() {
	// Do nothing
	}
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
   
   
	pin_uno = (EditText) findViewById(R.id.pin_uno);
	pin_dos = (EditText) findViewById(R.id.pin_dos);
	puk = (EditText) findViewById(R.id.puk_rs);
	
	eventos();
        
	
    }
    
 private void eventos(){
		
		
		// Boton de solicitar Taxis	
			
			
	 acept.setOnClickListener(new View.OnClickListener() {
				
		       	
				@Override
				public void onClick(View view) {
					
					
					pin1 = pin_uno.getText().length();
					pin2 = pin_dos.getText().length();
					
					
					if (!pin_uno.getText().toString().equals("")&&
	    					!pin_dos.getText().toString().equals("")&&
	    					!puk.getText().toString().equals(""))
					{	
					
						
					 if (pin1<4){
						 Toast.makeText(getApplicationContext(), "Error en captura de pin, Recuerda que el pin tiene que ser de cuatro dígitos", Toast.LENGTH_LONG).show();
						
					}
					 else if (pin2<4){
						 Toast.makeText(getApplicationContext(), "Error en captura de pin, Recuerda que el pin tiene que ser de cuatro dígitos", Toast.LENGTH_LONG).show();
						
					}
					 else if (pin_uno.getText().toString().equals(pin_dos.getText().toString())){
						 
						
					
					 
						
					//pin_uno.getText().length();
					
					// TODO Auto-generated method stub
					Intent in = new Intent (getApplicationContext(), Enlazar.class);
					startActivityForResult(in, 2);
					
					try {
						this.finalize();
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Log.v("TC","4ma");
				}
					
					 else { 
							Toast.makeText(getApplicationContext(), "No coinciden las dos claves pin registradas, volver a intentarlo", Toast.LENGTH_LONG).show();
							}
					
					
					} 
					
					
					
					
					
					
					else {
						
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


/*
 * if (contrasena.getText().toString().equals(contraseña2.getText().toString())){
    					registrando a = new registrando();
            			a.execute();
 * 
 * 
 */



