package com.kupay;

import com.google.zxing.WriterException;



import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;


public class MainActivity extends Activity{
	 private Camera mCamera;
	 private CameraPreview mPreview;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v("app","1");
		setContentView(R.layout.activity_main);
		Log.v("app","2");
		// Create an instance of Camera
        mCamera = getCameraInstance();
        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
		ImageView qr = (ImageView) findViewById(R.id.qrImg);
		FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
		preview.addView(mPreview);
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
	
	
	public static Camera getCameraInstance(){
	    Camera c = null;
	    try {
	        c = Camera.open(); // attempt to get a Camera instance
	    }
	    catch (Exception e){
	        // Camera is not available (in use or does not exist)
	    }
	    return c; // returns null if camera is unavailable
	}


}
