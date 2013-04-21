package com.kupay;

import com.google.zxing.WriterException;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;


public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v("app","1");
		setContentView(R.layout.activity_main);
		Log.v("app","2");
		ImageView qr = (ImageView) findViewById(R.id.qrImg);
		Log.v("app","3");
		Bitmap qrBitmap = null;
		Log.v("app","4");
		QRCodeEncoder code  = new QRCodeEncoder("KUPAY", 320);
		Log.v("app","5");
		try {
			 qrBitmap = code.encodeAsBitmap();
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.v("app","6");
		
		qr.setImageBitmap(qrBitmap);
		Log.v("app","7");

	}

}
