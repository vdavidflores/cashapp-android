package com.kupay;

import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;



class AnimaSaldo extends AsyncTask<Double, Double, Void>{
	
	Activity actividad;
	TextView cc;
	Double lastSaldo;
	Double nuevoSaldo;
	Double MAX_T = 60.0;
	Double MIN_T = 20.0;
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
	     lastSaldo =Double.parseDouble(ccText.substring(1));
		 }catch (Exception e) {
			lastSaldo = 0.0;
		}
		 
		 
		 
	
	  
      }
     
      protected Void doInBackground(Double... params) {
    	  nuevoSaldo = params[0];
    	  Double i= lastSaldo;
    	 double DIF = Math.sqrt(Math.pow(lastSaldo-nuevoSaldo, 2));
    	  if (DIF > DIF_ESTANDAR){
    		  MAX_T = (Double) ((DIF_ESTANDAR / DIF)*MAX_T); 
    		  MIN_T = (Double) ((DIF_ESTANDAR/DIF)*MAX_T);
    		  
    	  }
    	  
    	 if (nuevoSaldo >= lastSaldo){
    		 while (i < nuevoSaldo) {
    			 try {
    				
    				 
 					Thread.sleep(Double.doubleToRawLongBits( map(i,lastSaldo,nuevoSaldo,MAX_T,MIN_T)));
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
					Thread.sleep(Double.doubleToRawLongBits( map(i, nuevoSaldo, lastSaldo, MIN_T,MAX_T )));
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
      
      Double map(Double x, Double in_min, Double in_max, Double out_min, Double out_max)
      {
    	  double n = (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
        Log.v("app", "n: "+n+" in_min: "+in_min+" in_max: "+in_max+" out_min: "+out_min+" out_max: "+out_max);
        return n;
        
      }
      
    @Override
      protected void onProgressUpdate (Double... valores) {
    	Log.v("app", "actualizando");
    	cc.setText("$"+Double.toString(valores[0]));
      }
    
    @Override
      protected void onPostExecute(Void response) {
    	
    }

	
}