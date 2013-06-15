package com.kupay;

import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;



class AnimaSaldo extends AsyncTask<Integer, Integer, Void>{
	
	Activity actividad;
	TextView cc;
	int lastSaldo;
	int nuevoSaldo;
	long MAX_T = 60;
	long MIN_T = 20;
	long DIF_ESTANDAR = 200;
	
	
	AnimaSaldo(Activity act){
		this.actividad = act;
	}

	@Override
     protected void onPreExecute() {
		 cc = (TextView) actividad.findViewById(R.id.cantidad);
		String ccText = (String) cc.getText();
		//Log.v("app","el cc es: "+ccText);
		 Log.v("app","el cc es: "+ccText.substring(1));
		 try{
	     lastSaldo =Integer.parseInt(ccText.substring(1));
		 }catch (Exception e) {
			lastSaldo = 0;
		}
		 
		 
		 
	
	  
      }
     
      protected Void doInBackground(Integer... params) {
    	  nuevoSaldo = params[0];
    	  int i= lastSaldo;
    	 double DIF = Math.sqrt(Math.pow(lastSaldo-nuevoSaldo, 2));
    	  if (DIF > DIF_ESTANDAR){
    		  MAX_T = (long) ((DIF_ESTANDAR / DIF)*MAX_T); 
    		  MIN_T = (long) ((DIF_ESTANDAR/DIF)*MAX_T);
    		  
    	  }
    	  
    	 if (nuevoSaldo >= lastSaldo){
    		 while (i < nuevoSaldo) {
    			 try {
    				
    				 
 					Thread.sleep(map(i,lastSaldo,nuevoSaldo,MAX_T,MIN_T));
 				} catch (InterruptedException e) {
 					// TODO Auto-generated catch block
 					e.printStackTrace();
 				}
    			i ++;
				publishProgress(i);
			}
    	 }else{
    		 while (i > nuevoSaldo) {
    			 try {
					Thread.sleep(map(i, nuevoSaldo, lastSaldo, MIN_T,MAX_T ));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
     			i--;
 				publishProgress(i);
 			}
    	 }
    	  
    	  
		return null;
      }
      
      long map(long x, long in_min, long in_max, long out_min, long out_max)
      {
        long n = (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
        Log.v("app", "n: "+n+" in_min: "+in_min+" in_max: "+in_max+" out_min: "+out_min+" out_max: "+out_max);
        return n;
        
      }
      
    @Override
      protected void onProgressUpdate (Integer... valores) {
    	cc.setText("$"+valores[0].toString());
      }
    
    @Override
      protected void onPostExecute(Void response) {
    	
    }
	
}