package com.kupay;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class venta extends ListFragment {

  @Override
  public void onActivityCreated(Bundle savedInstanceState) { 
	  super.onActivityCreated(savedInstanceState);
	//////////////////////////////////////
	  
		JSONObject bloque = new JSONObject();
 		
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
 		bloque4.put("TIPO",3 );
 	
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
	  jay.put(bloque5);
	///////////////////////////777
    
    ventaw weather_data[] = new ventaw[jay.length()]; 
    
    for(int i=0; i < jay.length();i++){
    	
    	int tipo=0;
    	int monto=0;
    	
    	try {
    		
    	tipo = jay.getJSONObject(i).getInt("TIPO");
    	monto = jay.getJSONObject(i).getInt("MONTO");
    	 
    	} catch (JSONException e) {}
    	
    	weather_data[i] = 	new ventaw(R.drawable.compm, "Compra  a  de $"+monto );
    }
    
    /*
    weather_data[0]	= new ventaw(R.drawable.compm, "Compra  a  de $" );
    weather_data[1]	= new ventaw(R.drawable.mdm, "Depósito  de  $");
    weather_data[2]	= new ventaw(R.drawable.venm, "Venta  a  de $");
    weather_data[3]	= new ventaw(R.drawable.tranm, "Transferencia  a  de $");
    weather_data[4]	= new ventaw(R.drawable.compm, "Compra  a  de $");*/
    		
    WeatherAdapter adapter = new WeatherAdapter(getActivity(), R.layout.listview_item_row, weather_data);
setListAdapter(adapter);
  }
  

  public void onListItemClick(ListView l, View v, int position, long id) {
    // Do something with the data

  }
} 

