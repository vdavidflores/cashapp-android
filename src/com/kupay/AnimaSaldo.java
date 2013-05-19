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
    	 if (nuevoSaldo >= lastSaldo){
    		 while (i < nuevoSaldo) {
    			 try {
 					Thread.sleep(30);
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
					Thread.sleep(30);
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
    @Override
      protected void onProgressUpdate (Integer... valores) {
    	cc.setText("$"+valores[0].toString());
      }
    
    @Override
      protected void onPostExecute(Void response) {
    	
    }
	
}