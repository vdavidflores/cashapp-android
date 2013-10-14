package com.kupay;






import android.app.Activity;

import android.os.Bundle;

import android.util.Log;

import android.widget.Button;
import android.widget.EditText;




public class Inicio_sesion extends Activity {
    
	Button acept;
	Button cancel;

	EditText correo;
	EditText pin;
	EditText puk;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("inicio", "0.5");
        setContentView(R.layout.inicio_sesion);
        Log.v("inicio", "1");
        
        // Tomar los datos de los campos
acept = (Button) findViewById(R.id.aceptar_r);
   cancel = (Button) findViewById(R.id.iniciar); 
   Log.v("inicio", "2");  
   
   correo = (EditText) findViewById(R.id.celular);
	pin = (EditText) findViewById(R.id.nombreReg);
	puk = (EditText) findViewById(R.id.mail);
	
	
        
	
    }
   
}
