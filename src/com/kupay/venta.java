package com.kupay;


import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class venta extends ListFragment {

  @Override
  public void onActivityCreated(Bundle savedInstanceState) { 
	  super.onActivityCreated(savedInstanceState);
	  Serch consultas = new Serch();
	  consultas.execute();
	  
	//////////////////////////////////////////////////////////////////////////////////////////////////
		
	  
	  
	  
	  
	  
	  
	  
		/*JSONObject bloque = new JSONObject();
 		
 		try {
 		bloque.put("TIPO",1 );
 	
 		bloque.put("MONTO",21 );
 		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		JSONObject bloque2 = new JSONObject();
 		
 		try {
 		bloque2.put("TIPO",2 );
 	
 		bloque2.put("MONTO",22 );
 		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
JSONObject bloque3 = new JSONObject();
 		
 		try {
 		bloque3.put("TIPO",3 );
 	
 		bloque3.put("MONTO",23 );
 		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
JSONObject bloque4 = new JSONObject();
 		
 		try {
 		bloque4.put("TIPO",2 );
 	
 		bloque4.put("MONTO",24 );
 		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
JSONObject bloque5 = new JSONObject();
 		
 		try {
 		bloque5.put("TIPO",3 );
 	
 		bloque5.put("MONTO",25 );
 		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	  JSONArray jay = new JSONArray();
	  jay.put(bloque);
	  jay.put(bloque2);
	  jay.put(bloque3);
	  jay.put(bloque4);
	  jay.put(bloque5);*/
	//////////////////////////////////////////////////////////////////////////////////////////////////
    
   /* ventaw weather_data[] = new ventaw[jay.length()]; 
    
    for(int i=0; i < jay.length();i++){
    	
    	int tipo=0;
    	int monto=0;
    	
    	try {
    		
    	tipo = jay.getJSONObject(i).getInt("TIPO");
    	monto = jay.getJSONObject(i).getInt("MONTO");
    	 
    	} catch (JSONException e) {}
    	    	    	    	
    	switch (tipo) {

    	case 1:
    	weather_data[i] =  new ventaw(R.drawable.mdm, "Abono de $"+monto);
    	break;

    	case 2:
    	weather_data[i] =  new ventaw(R.drawable.compm, "Cargo de $"+monto);
    	break;
    	case 3:
        	weather_data[i] =  new ventaw(R.drawable.tranm, "Transferencia de $"+monto);
        	break;
    	default:
    	weather_data[i] =  new ventaw(R.drawable.mdm, "Movimiento Desconocido"+monto);
    	break;
    	}
    }*/
    
  
    		
   /* WeatherAdapter adapter = new WeatherAdapter(getActivity(), R.layout.listview_item_row, weather_data);
setListAdapter(adapter);*/
  }
  

  public void onListItemClick(ListView l, View v, int position, long id) {
    // Do something with the data

  
  
  }
  
  private class Serch extends AsyncTask<Void, Integer, JSONObject>{
	   	 
	  ProgressDialog progress;
	  Post post; 	 
	  JSONArray jay;

		@Override
       protected void onPreExecute() {
			progress = ProgressDialog.show(getActivity(), "Cargando", "Cargando lista...");
			Log.v("movs", "1");
        }
       
        
		protected JSONObject doInBackground(Void... params) {
			JSONObject data = new JSONObject();
			try {
  			
  				
  			
  			data.put("usr", "00000003");
  			data.put("dias", 1);
  			data.put("imei", "123456789012345");
  			data.put("pin", 1234);
  			
  			} catch (JSONException e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  			}
  		post = new Post(7,data);
      	  JSONObject response = null;
      	  try {
      		  response = post.exec(getActivity().getApplicationContext());
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
        	
        	Log.v("post",response.toString());

      	  return response;
      	  
	
        }
      @Override
        protected void onProgressUpdate (Integer... valores) {
    	  
        }
      
      @Override
        protected void onPostExecute(JSONObject response) {
      	progress.dismiss();
      	Log.v("movs", "2");
    
      	try {
			 String resultado = response.getString("RESULTADO");
			 Log.v("movs", "3");
      	} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
       
		try {
			Log.v("movs", "3.5");
			jay = response.getJSONObject("DATOS").getJSONArray("OPERACIONES");
			Log.v("movs", "4");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			}
        ventaw weather_data[] = new ventaw[jay.length()]; 
        
        for(int i=0; i < jay.length();i++){
        	
        	int tipo=0;
        	int monto=0;
        	String fecha = null;
        	
        	try {
        		;
        	tipo = jay.getJSONObject(i).getInt("TIPO");
        	monto = jay.getJSONObject(i).getInt("MONTO");
        	fecha = jay.getJSONObject(i).getString("FECHA");
        	 
        	} catch (JSONException e) {}
        	    	    	    	
        	switch (tipo) {

        	case 1:
        	weather_data[i] =  new ventaw(R.drawable.mdm, "Abono de $"+monto+"      "+fecha);
        	break;

        	case 2:
        	weather_data[i] =  new ventaw(R.drawable.compm, "Compra de $"+monto+"      "+fecha);
        	break;
        	case 3:
            	weather_data[i] =  new ventaw(R.drawable.tranm, "Transferencia de $"+monto+"      "+fecha);
            	break;
        	default:
        	weather_data[i] =  new ventaw(R.drawable.mdm, "Movimiento Desconocido"+monto+"      "+fecha);
        	break;
        	}
        }
        
      
        Log.v("movs", "5");
        WeatherAdapter adapter = new WeatherAdapter(getActivity(), R.layout.listview_item_row, weather_data);
    setListAdapter(adapter);
      	
      }

		
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
} 

