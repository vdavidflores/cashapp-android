package com.kupay;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import com.kupay.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;


public class BarCode extends Activity {

	ImageView barcode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_bar_code);
		barcode = (ImageView) findViewById(R.id.fullscreen_content);
		
		Intent intent = getIntent();
		barcode.setTag(intent.getStringExtra("url"));
		DownloadImagesTask dlit = new DownloadImagesTask();
		dlit.execute(barcode);
	}
	private class DownloadImagesTask extends AsyncTask<ImageView, Void, Bitmap> {

		ImageView imageView = null;

		@Override
		protected Bitmap doInBackground(ImageView... imageViews) {
		    this.imageView = imageViews[0];
		    return download_Image((String)imageView.getTag());
		}

		@Override
		protected void onPostExecute(Bitmap result) {
		    imageView.setImageBitmap(result);
		    imageView.getLayoutParams().height = 400;
		}


		private Bitmap download_Image(String url) {
			Bitmap bm = null;
		    try {
		        URL aURL = new URL(url);
		        URLConnection conn = aURL.openConnection();
		        conn.connect();
		        InputStream is = conn.getInputStream();
		        BufferedInputStream bis = new BufferedInputStream(is);
		        bm = BitmapFactory.decodeStream(bis);
		        bis.close();
		        is.close();
		    } catch (IOException e) {
		        Log.e("Hub","Error getting the image from server : " + e.getMessage().toString());
		    } 
		    return bm;
		}
	}
	
}
