package com.kupay;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class OperacionQR extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_operacion_qr);
		
		/*
		 * 
+		Log.v("app","3");
+		Bitmap qrBitmap = null;
+		Log.v("app","4");
+		QRCodeEncoder code  = new QRCodeEncoder("KUPAY", 320);
+		Log.v("app","5");
+		try {
+			 qrBitmap = code.encodeAsBitmap();
+		} catch (WriterException e) {
+			// TODO Auto-generated catch block
+			e.printStackTrace();
+		}
+		Log.v("app","6");
+		
+		qr.setImageBitmap(qrBitmap);
		 * 
		 * */
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.operacion_qr, menu);
		return true;
	}

}
