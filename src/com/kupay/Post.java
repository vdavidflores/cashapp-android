package com.kupay;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/*
 * 
 */

public class Post {
	 HttpResponse httpresponse;
	int accion;
	JSONObject obj;
	private  List<NameValuePair> pares;
	private JSONObject response;

	  public Post(int accion_ ,JSONObject obj_){
		  obj = obj_;
		  accion = accion_;
	  }
	  

	  
	  
	  public JSONObject exec(Context c ) throws ClientProtocolException, ParseException, JSONException{
		  Log.v("post", "1");
		  String respuesta= "";
		 if (HayConexion(c)){
			 Log.v("post", "2");
			  
			HttpClient httpclient = new DefaultHttpClient();
		  	Log.v("post", "2");
		     HttpPost httppost = new HttpPost("http://10.1.17.152/taxiHotServer/index.php");
		     pares = new ArrayList<NameValuePair>(2);
		     Log.v("post", "3");
		       pares.add(new BasicNameValuePair("FUNCION", Integer.toString(accion)));
		       pares.add(new BasicNameValuePair("DATA", obj.toString()));
		       Log.v("post", "4");
		       
		       
		      
		    	httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
				
				try {
					httppost.setEntity(new UrlEncodedFormEntity(pares));
					 Log.v("post", "5");
					
					httpresponse = httpclient.execute(httppost);
				 respuesta = EntityUtils.toString(httpresponse.getEntity());
				 Log.v("post", "6");
				 if ( httpresponse != null){
					
						response = new JSONObject(respuesta);
						
				 }
				 
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Log.v("post", "5.4: "+respuesta);
					return  new JSONObject().put("RESULTADO", "CONEXION_FALLIDA");
					//Log.v("post", "5.5");
					//e.printStackTrace();
				} 
				return response;
		 }else{
				//responfail.put("RESULTADO", "CONEXION_FALLIDA");
			 return  new JSONObject().put("RESULTADO", "NO_HAY_CONEXION");
		 }
	  }
	  
	  
	  public boolean HayConexion(Context c){//Detectar si hay conexion de datos (Internet)
	        ConnectivityManager cm = (ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);
	        NetworkInfo ni = cm.getActiveNetworkInfo();
	        
	        if(ni != null && ni.isConnected()) return true;
	        else return false;
	    }
	
}
