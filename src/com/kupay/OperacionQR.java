package com.kupay;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.zxing.WriterException;
import com.kupay.Post.OnResponseAsync;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class OperacionQR extends Activity {

	ImageView qr;
	String operacion;
	Post monitor;
	TextView estatus;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v("app", "op1");
		setContentView(R.layout.activity_operacion_qr);
		qr = (ImageView) findViewById(R.id.QR);
		Log.v("app", "op2");
		estatus = (TextView) findViewById(R.id.estatus);
		Intent in = getIntent();
		Log.v("app", "op3");
		operacion = in.getExtras().getString("OPERACION").toString();
		Bitmap qrBitmap = null;
		Log.v("app","4");
		QRCodeEncoder code  = new QRCodeEncoder(operacion, 320);
		Log.v("app","5");
		try {
			 qrBitmap = code.encodeAsBitmap();
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.v("app","6");
		
		qr.setImageBitmap(qrBitmap);	
		
		monitor = new Post();
		eventos();
		JSONObject data = new JSONObject();
		try {
			data.put("qr", operacion);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		monitor.setData(4, data);
		monitor.execAsync(getApplicationContext());
		

	}
	
	@Override
	protected void onDestroy() {
	monitor.stopAsync();
	super.onDestroy();
	}
	
	private void eventos() {
		monitor.setOnResponseAsync(new OnResponseAsync() {
			
			@Override
			public void onResponseAsync(JSONObject response) {
				// TODO Auto-generated method stub
				try {
					if(response.getString("RESULTADO").equals("EXITO")){
					
							JSONObject datos = response.getJSONObject("DATOS");
						
							estatus.setText("Estatus: "+datos.getString("ESTATUS").toString());
							
					}
				
					JSONObject data = new JSONObject();
					data.put("qr", operacion);
					Log.v("app", "pidiendo "+operacion);
					monitor.setData(4, data);
					monitor.execAsync(getApplicationContext());
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
	}

}
