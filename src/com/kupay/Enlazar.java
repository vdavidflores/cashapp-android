package com.kupay;






import android.app.Activity;
import android.content.Intent;

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
	EditText puk;
	

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
        
        // Tomar los datos de los campos
acept = (Button) findViewById(R.id.aceptar_r);
   cancel = (Button) findViewById(R.id.iniciar); 
   Log.v("inicio", "2");  
   
   correo = (EditText) findViewById(R.id.correo_i);
	pin = (EditText) findViewById(R.id.pin_i);
	puk = (EditText) findViewById(R.id.puk_i);
	
	eventos();
        
	
    }
    
 private void eventos(){
		
		
		// Boton de solicitar Taxis	
			
			
	 acept.setOnClickListener(new View.OnClickListener() {
				
		       	
				@Override
				public void onClick(View view) {
					if (!correo.getText().toString().equals("")&&
	    					!pin.getText().toString().equals("")&&
	    					!puk.getText().toString().equals(""))
					{		
					
					
					
					
					
					
					// TODO Auto-generated method stub
					Intent in = new Intent (getApplicationContext(), MainActivity.class);
					startActivityForResult(in, 2);
					
					try {
						this.finalize();
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Log.v("TC","4ma");
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
