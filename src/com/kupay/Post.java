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

import android.util.Log;

/*
 * 
 */

public class Post {
	
	int accion;
	JSONObject obj;
	private  List<NameValuePair> pares;
	private JSONObject response;

	  public Post(int accion_ ,JSONObject obj_){
		  obj = obj_;
		  accion = accion_;
	  }
	  
	  
	  public JSONObject exec() throws ClientProtocolException, IOException, ParseException, JSONException{
		  Log.v("post", "1");
		 
			  
		HttpClient httpclient = new DefaultHttpClient();
		  Log.v("post", "2");
		     HttpPost httppost = new HttpPost("http://10.1.18.122/kuCloudApp/index.php");
		     pares = new ArrayList<NameValuePair>(2);
		     Log.v("post", "3");
		       pares.add(new BasicNameValuePair("ACCION", Integer.toString(accion)));
		       pares.add(new BasicNameValuePair("DATA", obj.toString()));
		       Log.v("post", "4");
		       
		       
		      
		    	//httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
				httppost.setEntity(new UrlEncodedFormEntity(pares));
				 Log.v("post", "5");
				 HttpResponse httpresponse = httpclient.execute(httppost);
			
		       response = new JSONObject(EntityUtils.toString(httpresponse.getEntity()));
		       Log.v("app", "YYYYYYY");
       return response;
	  }
	  
	
}
