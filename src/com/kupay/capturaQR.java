package com.kupay;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.kupay.decoder.DecoderActivity;
import com.kupay.decoder.result.ResultHandler;
import com.kupay.decoder.result.ResultHandlerFactory;



import android.os.Bundle;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class capturaQR extends DecoderActivity{

	 

		   private static final String TAG = capturaQR.class.getSimpleName();
		    private static final Set<ResultMetadataType> DISPLAYABLE_METADATA_TYPES = EnumSet.of(ResultMetadataType.ISSUE_NUMBER, ResultMetadataType.SUGGESTED_PRICE,
		            ResultMetadataType.ERROR_CORRECTION_LEVEL, ResultMetadataType.POSSIBLE_COUNTRY);

		    private TextView statusView = null;
		    private View resultView = null;
		    private boolean inScanMode = false;
			private View viewfinderView;

		    public void onCreate(Bundle icicle) {
		        super.onCreate(icicle);
		        setContentView(R.layout.capture);
		        Log.v(TAG, "onCreate()");

		        resultView = findViewById(R.id.result_view);
		        statusView = (TextView) findViewById(R.id.status_view);

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

		    @Override
		    public boolean onKeyDown(int keyCode, KeyEvent event) {
		        if (keyCode == KeyEvent.KEYCODE_BACK) {
		            if (inScanMode)
		                finish();
		            else
		                onResume();
		            return true;
		        }
		        return super.onKeyDown(keyCode, event);
		    }

		    @Override
		    public void handleDecode(Result rawResult, Bitmap barcode) {
		        drawResultPoints(barcode, rawResult);

		        ResultHandler resultHandler = ResultHandlerFactory.makeResultHandler(this, rawResult);
		        handleDecodeInternally(rawResult, resultHandler, barcode);
		    }

		    public void showScanner() {
		        inScanMode = true;
		        resultView.setVisibility(View.GONE);
		        statusView.setText(R.string.msg_default_status);
		        statusView.setVisibility(View.VISIBLE);
		        viewfinderView.setVisibility(View.VISIBLE);
		    }

		    protected void showResults() {
		        inScanMode = false;
		        statusView.setVisibility(View.GONE);
		        viewfinderView.setVisibility(View.GONE);
		        resultView.setVisibility(View.VISIBLE);
		    }

		    // Put up our own UI for how to handle the decodBarcodeFormated contents.
		    private void handleDecodeInternally(Result rawResult, ResultHandler resultHandler, Bitmap barcode) {
		        onPause();
		        showResults();

		        ImageView barcodeImageView = (ImageView) findViewById(R.id.barcode_image_view);
		        if (barcode == null) {
		            barcodeImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
		        } else {
		            barcodeImageView.setImageBitmap(barcode);
		        }

		        TextView formatTextView = (TextView) findViewById(R.id.format_text_view);
		        formatTextView.setText(rawResult.getBarcodeFormat().toString());

		        TextView typeTextView = (TextView) findViewById(R.id.type_text_view);
		        typeTextView.setText(resultHandler.getType().toString());


		        TextView timeTextView = (TextView) findViewById(R.id.time_text_view);
		        timeTextView.setText("djshfka");

		        TextView metaTextView = (TextView) findViewById(R.id.meta_text_view);
		        View metaTextViewLabel = findViewById(R.id.meta_text_view_label);
		        metaTextView.setVisibility(View.GONE);
		        metaTextViewLabel.setVisibility(View.GONE);
		        Map<ResultMetadataType, Object> metadata = rawResult.getResultMetadata();
		        if (metadata != null) {
		            StringBuilder metadataText = new StringBuilder(20);
		            for (Map.Entry<ResultMetadataType, Object> entry : metadata.entrySet()) {
		                if (DISPLAYABLE_METADATA_TYPES.contains(entry.getKey())) {
		                    metadataText.append(entry.getValue()).append('\n');
		                }
		            }
		            if (metadataText.length() > 0) {
		                metadataText.setLength(metadataText.length() - 1);
		                metaTextView.setText(metadataText);
		                metaTextView.setVisibility(View.VISIBLE);
		                metaTextViewLabel.setVisibility(View.VISIBLE);
		            }
		        }

		        TextView contentsTextView = (TextView) findViewById(R.id.contents_text_view);
		        CharSequence displayContents = resultHandler.getDisplayContents();
		        contentsTextView.setText(displayContents);
		        // Crudely scale betweeen 22 and 32 -- bigger font for shorter text
		        int scaledSize = Math.max(22, 32 - displayContents.length() / 4);
		        contentsTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, scaledSize);
		    }
		}
