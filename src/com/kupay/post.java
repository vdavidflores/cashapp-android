package com.kupay;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

/*
 * 
 */

public class post {
	
	int accion;
	JSONObject obj;
	private ArrayList<BasicNameValuePair> pares;
	private JSONObject response;

	  public post(int accion_ ,JSONObject obj_){
		  obj = obj_;
		  accion = accion_;
	  }
	  
	  
	  public JSONObject exec(){
		  HttpClient httpclient = new DefaultHttpClient();
			 
		     HttpPost httppost = new HttpPost("http://localhost/kuCloudApp/index.php");
		     pares = new ArrayList<BasicNameValuePair>();
		     // , "DATA", obj.toString())
		       pares.add(new BasicNameValuePair((String)"ACCION", (String)Integer.toString(accion)));
		       pares.add(new BasicNameValuePair((String)"DATA", obj.toString()));
		       
		       try {
				httppost.setEntity(new UrlEncodedFormEntity(pares));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		       try {
				response = (JSONObject) httpclient.execute(httppost);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
       return response;
	  }
	
}
