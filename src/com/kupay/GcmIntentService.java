package com.kupay;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class GcmIntentService extends IntentService {
	public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    private String TAG = "app";
	public GcmIntentService() {
		  super("GcmIntentService");
	}

	 @Override
	    protected void onHandleIntent(Intent intent) {
			// TODO: This method is called when the BroadcastReceiver is receiving
			// an Intent broadcast.
		 
		 Bundle extras = intent.getExtras();
	        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
	        // The getMessageType() intent parameter must be the intent you received
	        // in your BroadcastReceiver.
	        String messageType = gcm.getMessageType(intent);

	        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
	            /*
	             * Filter messages based on message type. Since it is likely that GCM
	             * will be extended in the future with new message types, just ignore
	             * any message types you're not interested in, or that you don't
	             * recognize.
	             */
	            if (GoogleCloudMessaging.
	                    MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
	                sendNotification("Send error: " + extras.toString());
	            } else if (GoogleCloudMessaging.
	                    MESSAGE_TYPE_DELETED.equals(messageType)) {
	                sendNotification("Deleted messages on server: " +
	                        extras.toString());
	            // If it's a regular GCM message, do some work.
	            } else if (GoogleCloudMessaging.
	                    MESSAGE_TYPE_MESSAGE.equals(messageType)) {
	                // This loop represents the service doing some work.
	                for (int i=0; i<5; i++) {
	                    Log.v("app", "Working... " + (i+1)
	                            + "/5 @ " + SystemClock.elapsedRealtime());
	                    try {
	                        Thread.sleep(5000);
	                    } catch (InterruptedException e) {
	                    }
	                }
	                Log.v(TAG, "Completed work @ " + SystemClock.elapsedRealtime());
	                // Post notification of received message.
	                sendNotification("Received: " + extras.toString());
	                Log.v(TAG, "Received: " + extras.toString());
	            }
	        }
	        // Release the wake lock provided by the WakefulBroadcastReceiver.
	       // KUReceiver.completeWakefulIntent(intent);
	 }
	 

	 private void sendNotification(String msg) {
		 NotificationManager mNotificationManager = (NotificationManager)
	                getApplicationContext().getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);

	      //  PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
	           //     new Intent(this, DemoActivity.class), 0);

	        NotificationCompat.Builder mBuilder =
	                new NotificationCompat.Builder(getApplicationContext())
	        .setSmallIcon(R.drawable.home)
	        .setContentTitle("GCM Notification")
	        .setStyle(new NotificationCompat.BigTextStyle()
	        .bigText(msg))
	        .setContentText(msg);

	       // mBuilder.setContentIntent(contentIntent);
	        mNotificationManager.notify(1, mBuilder.build());
	    }
}
