package com.kupay;


import java.text.BreakIterator;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class Consulta extends ListFragment {

  @Override
  public void onActivityCreated(Bundle savedInstanceState) { 
	  super.onActivityCreated(savedInstanceState);
	  Serch consultas = new Serch();
	  consultas.execute();

  }
  

  public void onListItemClick(ListView l, View v, int position, long id) {


  
  
  }
  
  private class Serch extends AsyncTask<Void, Integer, JSONObject>{
	  
	  OperacionRow weather_data[];
	  ProgressDialog progress;
	  Post post; 	 
	  JSONArray jay;
	  String CONEXION_FALLIDA = "CONEXION_FALLIDA";
	  String NO_HAY_CONEXION = "NO_HAY_CONEXION";
		@Override
       protected void onPreExecute() {
			progress = ProgressDialog.show(getActivity(), "Cargando", "Cargando lista...");
			Log.v("movs", "1");
        }
       
        
		protected JSONObject doInBackground(Void... params) {
			JSONObject data = new JSONObject();
			try {
  			
  				
  			
  			data.put("usr", MiUsuario());
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
      	String resultado = null;
      	
      	try {
			  resultado = response.getString("RESULTADO");
			 Log.v("movs", "3");
      	} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
      	
      	Log.v("app", resultado);
       if(!resultado.equals(CONEXION_FALLIDA.toString()) && !resultado.equals(NO_HAY_CONEXION.toString())){
	    	 
	       
			try {
				Log.v("movs", "3.5");
				jay = response.getJSONObject("DATOS").getJSONArray("OPERACIONES");
				Log.v("movs", "4");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				}
	         weather_data = new OperacionRow[jay.length()]; 
	        
	        for(int i=0; i < jay.length();i++){
	        
	        	int tipo=0;
	        	int monto=0;
	        	String fecha = null;
	        	String polo = null;
	        	String concepto = null;
	        	
	        	try {
	        		;
	        	tipo = jay.getJSONObject(i).getInt("TIPO");
	        	monto = jay.getJSONObject(i).getInt("MONTO");
	        	fecha = jay.getJSONObject(i).getString("FECHA");
	        	polo = jay.getJSONObject(i).getString("POLO");
	        	concepto = jay.getJSONObject(i).getString("CONCEPTO");
	        	 
	        	} catch (JSONException e) {}
	        	    	    	    	
	        	switch (tipo) {
	
	        	case 1:
	        	weather_data[i] =  new OperacionRow(R.drawable.mdm, "Abono de "+polo+"$"+monto+"\n"+fecha+"\n"+concepto);
	        	break;
	
	        	case 2:
	        	weather_data[i] =  new OperacionRow(R.drawable.compm, "Compra de "+polo+"$"+monto+"\n"+fecha+"\n"+concepto);
	        	break;
	        	case 3:
	            	weather_data[i] =  new OperacionRow(R.drawable.tranm, "Transferencia de "+polo+"$"+monto+"\n"+fecha+"\n"+concepto);
	            	break;
	            	
	        	case 5:
	            	weather_data[i] =  new OperacionRow(R.drawable.compm, "Compra de "+polo+"$"+monto+"\n"+fecha+"\n"+concepto);
	            	break;
	        	default:
	        	weather_data[i] =  new OperacionRow(R.drawable.mdm, "Movimiento desconocido "+polo+"$"+monto+"\n"+fecha+"\n"+concepto);
	        	break;
	        	}
	        }
       
	        Log.v("movs", "5");
	        WeatherAdapter adapter = new WeatherAdapter(getActivity(), R.layout.listview_item_row, weather_data);
	        setListAdapter(adapter);
       
        }else{
	        	setListAdapter(getListAdapter());
	        }
      
      }
      
      private String MiUsuario(){
      	String usr = null;
          BDD dbh = new BDD(getActivity(),"kupay",null,1);
          SQLiteDatabase db= dbh.getReadableDatabase();
          Cursor reg = db.query("kupay",new String[]{"usr"},null,null,null,null,null,"1");
          if(reg.moveToFirst()){
              usr=reg.getString(0);
             
          
          }
  	 return usr;
      }

		
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
} 

