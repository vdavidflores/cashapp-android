package com.kupay;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class KUReceiver extends BroadcastReceiver {
	private Context context;
	public KUReceiver() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		
		Log.v("app", "PUSH");
		
		// TODO: This method is called when the BroadcastReceiver is receiving
		// an Intent broadcast.
		/*this.context = context;
		 Bundle extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);
		Log.v("app",messageType);
		Log.v("app",GoogleCloudMessaging.
                MESSAGE_TYPE_MESSAGE);
	     sendNotification("Received: " + extras.toString());
            Log.v("app", "Received: " + extras.toString());*/
        
		//throw new UnsupportedOperationException("Not yet implemented");
	}
	
	 private void sendNotification(String msg) {
		 NotificationManager mNotificationManager = (NotificationManager)
	                this.context.getSystemService(this.context.NOTIFICATION_SERVICE);

	      //  PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
	           //     new Intent(this, DemoActivity.class), 0);

	        NotificationCompat.Builder mBuilder =
	                new NotificationCompat.Builder(this.context)
	        .setSmallIcon(R.drawable.home)
	        .setContentTitle("GCM Notification")
	        .setStyle(new NotificationCompat.BigTextStyle()
	        .bigText(msg))
	        .setContentText(msg);

	       // mBuilder.setContentIntent(contentIntent);
	        mNotificationManager.notify(1, mBuilder.build());
	    }
	
}
