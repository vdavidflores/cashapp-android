package com.kupay;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

/*
 * 
 */

public class Post {
	 HttpResponse httpresponse;
	int accion;
	JSONObject obj;
	private  List<NameValuePair> pares;
	private JSONObject response ;
	private Context conext;
	private execAsync ea;

	  public Post(int accion_ ,JSONObject obj_){
		  obj = obj_;
		  accion = accion_;
	  }
	  public Post(){
		  
	  }
	  public void setData(int accion_ ,JSONObject obj_){
		  obj = obj_;
		  accion = accion_;
	  }
	  
	  public JSONObject exec(Context c ) throws ClientProtocolException, ParseException, JSONException{
		  Log.v("post", "1");
		  String respuesta= "";
		 if (HayConexion(c)){
			 Log.v("post", "2");
			  
			HttpClient httpclient = sslClient(new DefaultHttpClient());
			
		  	Log.v("post", "2");
		    HttpPost httppost = new HttpPost("https://cashapp.mx/kuCloudApp/index.php");
		  //	 HttpPost httppost = new HttpPost("http://10.1.17.237/kuCloudApp/index.php"); 
		  	pares = new ArrayList<NameValuePair>(2);
		     Log.v("post", "3");
		       pares.add(new BasicNameValuePair("ACCION", Integer.toString(accion)));
		       pares.add(new BasicNameValuePair("DATA", obj.toString()));
		       Log.v("post", "4");
		       
		       
		      
		    	httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
				
				try {
					httppost.setEntity(new UrlEncodedFormEntity(pares));
					 Log.v("post", "5");
				
					httpresponse = httpclient.execute(httppost);
					respuesta = EntityUtils.toString(httpresponse.getEntity());
					Log.v("app", "respuesta: " +respuesta.toString());
				 if ( httpresponse != null){
					
						response = new JSONObject(respuesta);
						
				 }
				 
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Log.v("post", "5.4: "+e.toString());
					return  new JSONObject().put("RESULTADO", "CONEXION_FALLIDA");
					//Log.v("post", "5.5");
					//e.printStackTrace();
				} 
				
				if (response != null)
				return response;
		 }else{
				//responfail.put("RESULTADO", "CONEXION_FALLIDA");
			 return  new JSONObject().put("RESULTADO", "NO_HAY_CONEXION");
		 }
		return new JSONObject().put("RESULTADO", "CONEXION_FALLIDA");
	  }
	  
	  
	  public boolean HayConexion(Context c){//Detectar si hay conexion de datos (Internet)
	        ConnectivityManager cm = (ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);
	        NetworkInfo ni = cm.getActiveNetworkInfo();
	        
	        if(ni != null && ni.isConnected()) return true;
	        else return false;
	    }
	  
	  private HttpClient sslClient(HttpClient client) {
		    try {
		        X509TrustManager tm = new X509TrustManager() { 
		            public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
		            }

		            public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
		            }

		            public X509Certificate[] getAcceptedIssuers() {
		                return null;
		            }
		        };
		        SSLContext ctx = SSLContext.getInstance("TLS");
		        ctx.init(null, new TrustManager[]{tm}, null);
		        SSLSocketFactory ssf = new MySoketSSLFactory(ctx);
		        ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		        ClientConnectionManager ccm = client.getConnectionManager();
		        SchemeRegistry sr = ccm.getSchemeRegistry();
		        sr.register(new Scheme("https", ssf, 443));
		        return new DefaultHttpClient(ccm, client.getParams());
		    } catch (Exception ex) {
		        return null;
		    }
		}
	  
	  OnResponseAsync mListener;
	  public interface OnResponseAsync{
		  public void onResponseAsync(JSONObject response);
		  }
	  
	  
	  public void setOnResponseAsync(OnResponseAsync responseAsync) {
		  mListener=responseAsync;
		  }
	  
	  
	  public void execAsync(Context appContext) {
		  conext = appContext;
		 ea = new execAsync();
		ea.execute();
	}
	  public void stopAsync() {
		ea.cancel(true);
	}
	  
	  
	  private class execAsync extends AsyncTask<Void, Integer, JSONObject>{
		   	 
		     

			@Override
	         protected void onPreExecute() {
				
	          }
	         
	          
			protected JSONObject doInBackground(Void... args0) {
	        		  try {
						response = exec(conext);
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
	
	          	
	          	Log.v("app",response.toString());
	  
	        	  return response;
		
	          }
	        
	        @Override
	          protected void onPostExecute(JSONObject response) {
	        	
	        	mListener.onResponseAsync(response);
	        	
	        }
	  }
}
