package com.kupay;


import java.text.BreakIterator;
import java.util.GregorianCalendar;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import com.markupartist.android.widget.PullToRefreshListView;
import com.markupartist.android.widget.PullToRefreshListView.OnRefreshListener;


public class Consulta extends Fragment {
	

	Boolean hayLista = false;
	 OperacionRow weather_data[];
	 PullToRefreshListView lv ;
	 Serch  serch;
  @Override
  public void onActivityCreated(Bundle savedInstanceState) { 
	  super.onActivityCreated(savedInstanceState);
	  

  }
  public void onDestroy() {
	  hayLista = false;
      
	  serch.cancel(true);
	  super.onDestroy();
      
  }
  
  @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View c = View.inflate(getActivity().getApplicationContext(), R.layout.consulta_listview,null);
		lv = (PullToRefreshListView) c.findViewById(R.id.listView1);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?>parent, View v, int position, long id) {			
				// TODO Auto-generated method stub
				
				TextView myView = new TextView(getActivity().getApplicationContext());
				  myView.setText(weather_data[(int) id].idkey.substring(0, 5));
				  myView.setGravity(Gravity.CENTER_HORIZONTAL);
				  
				  myView.setTextSize(40);
				//  myView.setGravity(Gravity.BOTTOM );
				  AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
				    alert.setTitle("Ticket de operación");
				    alert.setView(myView);
				    alert.setMessage("Concepto: "+weather_data[(int)id].concepto);
				  
				    alert.setPositiveButton("OK",
				            new DialogInterface.OnClickListener() {
				                @Override
				                public void onClick(DialogInterface dialog, int which) {
				                    // TODO Auto-generated method stub

				                    dialog.dismiss();
				                }
				            });

				    alert.show();		
			}
        	 
        });
		
		lv.setOnRefreshListener(new OnRefreshListener() {
		    @Override
		    public void onRefresh() {
		        // Do work to refresh the list here.
		    		serch = new Serch();
				 
		    		serch.execute();
				
		    }
		});
		
		return c;
  }
  @Override
	public void onResume() {
  	 
	  if (!hayLista){
		   serch = new Serch();
		   serch.execute();
	  }else{
		  WeatherAdapter adapter = new WeatherAdapter(getActivity(), R.layout.listview_item_row, weather_data);
	        lv.setAdapter(adapter);
	  }
       super.onResume();
    
  }
  


  
  private class Serch extends AsyncTask<Void, Integer, JSONObject>{
	  
	 
	
	  Post post; 	 
	  JSONArray jay;
	  String CONEXION_FALLIDA = "CONEXION_FALLIDA";
	  String NO_HAY_CONEXION = "NO_HAY_CONEXION";
		@Override
       protected void onPreExecute() {
		
			Log.v("movs", "1");
        }
       
        
		protected JSONObject doInBackground(Void... params) {
			JSONObject data = new JSONObject();
			try {
  			data.put("usr", MiUsuario());
  			data.put("dias", 1);
  			data.put("imei", MiImei());
  			//data.put("pin", 1234);
  			
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
	        	Double monto=0.0;
	        	
	        	String fecha = null;
	        	String polo = null;
	        	String concepto = null;
	        	String idkey = null;
	        	
	        	try {
	        	idkey= jay.getJSONObject(i).getString("IDKEY");
	        	tipo = jay.getJSONObject(i).getInt("TIPO");
	        	monto = jay.getJSONObject(i).getDouble("MONTO");
	        	fecha = jay.getJSONObject(i).getString("FECHA");
	        	polo = jay.getJSONObject(i).getString("POLO");
	        	concepto = jay.getJSONObject(i).getString("CONCEPTO");
	        	 
	        	} catch (JSONException e) {}
	        	    	    	    	
	        	switch (tipo) {
	
	        	case 1:
	        	weather_data[i] =  new OperacionRow(R.drawable.mdm, "Abono de "+polo+"$"+monto+"\n"+fecha+"\n"+concepto, concepto,idkey);
	        	break;
	
	        	case 2:
	        		if(polo.equals("-")){
	        			weather_data[i] =  new OperacionRow(R.drawable.compm, "Compra de "+polo+"$"+monto+"\n"+fecha+"\n"+concepto, concepto, idkey);
	        		}else{
	        			weather_data[i] =  new OperacionRow(R.drawable.compm, "Venta de "+polo+"$"+monto+"\n"+fecha+"\n"+concepto, concepto, idkey);
	        		}
	        	break;
	        	case 3:
	            	weather_data[i] =  new OperacionRow(R.drawable.tranm, "Transferencia de "+polo+"$"+monto+"\n"+fecha+"\n"+concepto, concepto, idkey);
	            break;
	            	
	        	case 5:
	            	weather_data[i] =  new OperacionRow(R.drawable.compm, "Compra de "+polo+"$"+monto+"\n"+fecha+"\n"+concepto, concepto, idkey);
	            break;
	        	case 6:
	            	weather_data[i] =  new OperacionRow(R.drawable.compm, "Deposito de "+polo+"$"+monto+"\n"+fecha+"\n"+concepto, concepto, idkey);
	            break;
	        	case 7:
	            	weather_data[i] =  new OperacionRow(R.drawable.compm, "Retiro de "+polo+"$"+monto+"\n"+fecha+"\n"+concepto, concepto, idkey);
	            break;
	        	case 8:
	            	weather_data[i] =  new OperacionRow(R.drawable.compm, "Comisión de "+polo+"$"+monto+"\n"+fecha+"\n"+concepto, concepto, idkey);
	            break;
	        	default:
	        	weather_data[i] =  new OperacionRow(R.drawable.mdm, "Movimiento desconocido "+polo+"$"+monto+"\n"+fecha+"\n"+concepto, concepto, idkey);
	        	break;
	        	}
	        }
       
	        Log.v("movs", "5");
	        WeatherAdapter adapter = new WeatherAdapter(getActivity(), R.layout.listview_item_row, weather_data);
	        lv.setAdapter(adapter);
	        hayLista = true;
       
        }else{
	        	lv.setAdapter(lv.getAdapter());
	        }
       lv.onRefreshComplete();
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
      private String MiImei(){
      	String imei = null;
          BDD dbh = new BDD(getActivity(),"kupay",null,1);
          SQLiteDatabase db= dbh.getReadableDatabase();
          Cursor reg = db.query("kupay",new String[]{"imei"},null,null,null,null,null,"1");
          if(reg.moveToFirst()){
              imei=reg.getString(0);
             
          
          }
  	 return imei;
      }
  }
  
} 

