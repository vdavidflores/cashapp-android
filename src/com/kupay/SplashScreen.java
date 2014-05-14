package com.kupay;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
 
public class SplashScreen extends Activity {
 
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2500;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slash_kupay);
        
        if(MiUsuario().equals("nill")){
 
        new Handler().postDelayed(new Runnable() {
 
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
 
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(getApplicationContext(), Pregunta.class);
                startActivity(i);
 
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
        }else{
        	 Intent i = new Intent(getApplicationContext(), MainConteiner.class);
             startActivity(i);
             this.finish();
        }
    }
    
    private String MiUsuario(){
    	String usr = null;
        BDD dbh = new BDD(getApplicationContext(),"kupay",null,1);
        SQLiteDatabase db= dbh.getReadableDatabase();
        Cursor reg = db.query("kupay",new String[]{"usr"},null,null,null,null,null,"1");
        if(reg.moveToFirst()){
            usr=reg.getString(0);
           
        
        }
        dbh.close();
	 return usr;
    }
 
}
