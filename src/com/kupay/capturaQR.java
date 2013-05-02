package com.kupay;

import com.google.zxing.Result;
import com.kupay.decoder.DecoderActivity;
import com.kupay.decoder.result.ResultHandler;
import com.kupay.decoder.result.ResultHandlerFactory;



import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;


public class capturaQR extends DecoderActivity{

	 

		   private static final String TAG = capturaQR.class.getSimpleName();
		

		    private boolean inScanMode = false;
			private View viewfinderView;

		    public void onCreate(Bundle icicle) {
		        super.onCreate(icicle);
		        setContentView(R.layout.capture);
		        Log.v(TAG, "onCreate()");



		        inScanMode = false;
		    }

		    protected void onDestroy() {
		        super.onDestroy();
		        Log.v(TAG, "onDestroy()");
		    }

		    @Override
		    protected void onResume() {
		        super.onResume();
		        Log.v(TAG, "onResume()gitfg");
		    }

		    @Override
		    protected void onPause() {
		        super.onPause();
		        Log.v(TAG, "onPause()");
		    }

		   /* @Override
		    public boolean onKeyDown(int keyCode, KeyEvent event) {
		        if (keyCode == KeyEvent.KEYCODE_BACK) {
		            if (inScanMode)
		                finish();
		            else
		                onResume();
		            return true;
		        }
		        return super.onKeyDown(keyCode, event);
		    }*/

		    @Override
		    public void handleDecode(Result rawResult, Bitmap barcode) {
		        drawResultPoints(barcode, rawResult);

		        ResultHandler resultHandler = ResultHandlerFactory.makeResultHandler(this, rawResult);
		        handleDecodeInternally(rawResult, resultHandler, barcode);
		    }

		    public void showScanner() {
		        inScanMode = true;
		        viewfinderView.setVisibility(View.VISIBLE);
		    }

		    

		    // Put up our own UI for how to handle the decodBarcodeFormated contents.
		    private void handleDecodeInternally(Result rawResult, ResultHandler resultHandler, Bitmap barcode) {
		        onPause();
		        AlertDialog.Builder builder_ = new AlertDialog.Builder(this);	
		        builder_.setTitle("HEY!");
		        
		        CharSequence displayContents = resultHandler.getDisplayContents();
	    		builder_.setMessage("SE DETECTO: "+displayContents);
	        	builder_.setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int id) {
	                	onResume();
	                }
	            });
	        	AlertDialog dialog = builder_.create();
	        	dialog.show();
		        

		    }
		}
