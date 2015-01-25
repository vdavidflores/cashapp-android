package com.kupay;


import java.io.IOException;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.kupay.Post.OnResponseAsync;




import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class MainConteiner extends FragmentActivity  {
	
	

    public static final String PROPERTY_REG_ID = "registration_id";
    public static final String EXTRA_MESSAGE = "message";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    String SENDER_ID = "554764786704";

	ListView sidemenu;
	SlidingMenu menu;
	TextView usuario;
	TextView MontoSaldo;
	OperacionRow menurow[];
    GoogleCloudMessaging gcm;
    Post regstrarGCMID;

	ActualizarCC cc;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_conteiner);
		if (findViewById(R.id.fragment_container) != null) {
			//
		FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        MainActivity myFragment = new MainActivity();
        myFragment.setArguments(getIntent().getExtras());
        ft.add(R.id.fragment_container, myFragment);
        ft.commit();
        //
		}
		
		regstrarGCMID = new Post();
		regstrarGCMID.setOnResponseAsync(new OnResponseAsync() {
			
			@Override
			public void onResponseAsync(JSONObject response) {
				// TODO Auto-generated method stub
				
			}
		});
		
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		menu.setShadowWidth(5);
		menu.setFadeDegree(0.0f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setShadowWidth(10);
		
		Display display = getWindowManager().getDefaultDisplay();
		menu.setBehindWidth((display.getWidth()/3)*2);
		
		
	//	menu.setMenu(R.layout.menu_frame);
		menu.setFadeDegree(0.35f);
		//menu.setBehindOffset(2);
		
		menu.setMenu(R.layout.menu_frame);
		 String regid;
		 
		 
		 ///ID Google Cloud MEsage (mensajeria en push)
		if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
            regid = getRegistrationId(getApplicationContext());
            Log.v("app", "REG-ID:"+regid);
            if (regid.isEmpty()) {
            	Log.v("app", "Esta vacio y se va actualizar el token para push");
               new registerInBackground().execute();
            }
        } else {
            Log.i("app", "No valid Google Play Services APK found.");
        }

		usuario = (AutoResizeTextView) menu.findViewById(R.id.mail_navicon);
		MontoSaldo = (TextView) menu.findViewById(R.id.montoSaldo);
		 cc = new ActualizarCC(this);
		 cc.execute();
		
		
		usuario.setText(MiUsuario());
		
		sidemenu = (ListView) menu.findViewById(R.id.sideMenuList);
		menurow = new OperacionRow[7];
		menurow[0]= new OperacionRow(R.drawable.mdm, "Operaciones",null,null);
		menurow[1]= new OperacionRow(R.drawable.historico, "Historial",null,null);
		menurow[2]= new OperacionRow(R.drawable.deposito, "Depositar",null,null);
		menurow[3]= new OperacionRow(R.drawable.retiro, "Retirar",null,null);
		menurow[4]= new OperacionRow(R.drawable.llavek, "Código de Acceso",null,null);
		menurow[5]= new OperacionRow(R.drawable.desenlazar, "Desenlazar equipo",null,null);
		menurow[6]= new OperacionRow(R.drawable.info, "Cashapp",null,null);
	
		WeatherAdapter adapter = new WeatherAdapter(this, R.layout.listview_item_row_menu,menurow);
		sidemenu.setAdapter(adapter);
		sidemenu.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?>parent, View v, int position, long id) {
				
				Log.v("app",String.valueOf(id));
				FragmentManager fm = getSupportFragmentManager();
				if (id == 0){
					
					 fm.beginTransaction()
						.replace(R.id.fragment_container, new MainActivity(), "deposito")
						.commit();
					
					togle();
				}else  if(id== 1){
					//if (fm.findFragmentByTag("deposito") == null) {
			        fm.beginTransaction()
					.replace(R.id.fragment_container, new Consulta(), "historico")
					.commit();
			        
			//	}
				togle();

			}
				else  if(id== 2){
					//if (fm.findFragmentByTag("deposito") == null) {
				        fm.beginTransaction()
						.replace(R.id.fragment_container, new Deposito(), "deposito")
						.commit();
				        
				//	}
					togle();

				}
				else  if(id== 3){
					//if (fm.findFragmentByTag("deposito") == null) {
				       fm.beginTransaction()
						.replace(R.id.fragment_container, new Retiro(), "retiro")
						.commit();
				        
				//	}
					togle();

				}
				else  if(id== 4){
					//if (fm.findFragmentByTag("deposito") == null) {
				        fm.beginTransaction()
						.replace(R.id.fragment_container, new Llaveku(), "llveku")
						.commit();
				        
				//	}
					togle();

				}
				else  if(id== 5){
					//if (fm.findFragmentByTag("deposito") == null) {
				        fm.beginTransaction()
						.replace(R.id.fragment_container, new Desenlazar(), "desenlazar")
						.commit();
				        
				//	}
					togle();

				}
				else  if(id== 6){
					//if (fm.findFragmentByTag("deposito") == null) {
				        fm.beginTransaction()
						.replace(R.id.fragment_container, new Info(), "info")
						.commit();
				        
				//	}
					togle();

				}
				
			}
		});
			
		
	}
	
    @Override
			public void onPause() {
		    	 super.onPause();
		    
		    }

	
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       super.onActivityResult(requestCode, resultCode, data);
    }
    
	public void togle() {
		menu.toggle();
		 ActualizarCC cc = new ActualizarCC(this);
    	 cc.execute();
    	 
    	
	}
	
	private String MiUsuario(){
    	String usr = null;
        BDD dbh = new BDD(this,"kupay",null,1);
        SQLiteDatabase db= dbh.getReadableDatabase();
        Cursor reg = db.query("kupay",new String[]{"usr"},null,null,null,null,null,"1");
        if(reg.moveToFirst()){
            usr=reg.getString(0);
           
        
        }
	 return usr;
    }
	
	private boolean checkPlayServices() {
	    int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
	    if (resultCode != ConnectionResult.SUCCESS) {
	        if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
	            GooglePlayServicesUtil.getErrorDialog(resultCode, this,PLAY_SERVICES_RESOLUTION_REQUEST).show();
	        } else {
	            Log.i("app", "This device is not supported.");
	            finish();
	        }
	        return false;
	    }
	    return true;
	}
	private String getRegistrationId(Context context) {
	    final SharedPreferences prefs = getGCMPreferences(context);
	    String registrationId = prefs.getString(PROPERTY_REG_ID, "");
	    if (registrationId.isEmpty()) {
	        Log.v("app", "Registration not found.");
	        return "";
	    }
	    // Check if app was updated; if so, it must clear the registration ID
	    // since the existing regID is not guaranteed to work with the new
	    // app version.
	    int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
	    int currentVersion = getAppVersion(context);
	    if (registeredVersion != currentVersion) {
	        Log.v("app", "App version changed.");
	        return "";
	    }
	    return registrationId;
	}
	private SharedPreferences getGCMPreferences(Context context) {
	    // This sample app persists the registration ID in shared preferences, but
	    // how you store the regID in your app is up to you.
	    return getSharedPreferences(MainConteiner.class.getSimpleName(),
	            Context.MODE_PRIVATE);
	}
	
	private static int getAppVersion(Context context) {
	    try {
	        PackageInfo packageInfo = context.getPackageManager()
	                .getPackageInfo(context.getPackageName(), 0);
	        return packageInfo.versionCode;
	    } catch (NameNotFoundException e) {
	        // should never happen
	        throw new RuntimeException("Could not get package name: " + e);
	    }
	}
	
	private class registerInBackground extends AsyncTask<Void, Integer, String>{
	   	 
	     

		@Override
         protected void onPreExecute() {
			
          }
         
          
		protected String doInBackground(Void... args0) {
			String regid = null;
			if (gcm == null) {
                gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
            }
            try {
				regid = gcm.register(SENDER_ID);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
        	  return regid;
	
          }

        @Override
          protected void onPostExecute(String response) {
        	JSONObject obj = new JSONObject();
        	try {
				obj.put("usr", MiUsuario());
				obj.put("imei", MiImei());
				obj.put("token", response);
				obj.put("so", "android");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	storeRegistrationId(getApplicationContext(),response);
        	regstrarGCMID.setData(28, obj);
        	regstrarGCMID.execAsync(getApplicationContext());
        	
        	
        }
  }
	private void storeRegistrationId(Context context, String regId) {
	    final SharedPreferences prefs = getGCMPreferences(context);
	    int appVersion = getAppVersion(context);
	    Log.v("app", "Saving regId on app version " + appVersion);
	    SharedPreferences.Editor editor = prefs.edit();
	    editor.putString(PROPERTY_REG_ID, regId);
	    editor.putInt(PROPERTY_APP_VERSION, appVersion);
	    editor.commit();
	}
	
      private String MiImei(){
      	String usr = null;
          BDD dbh = new BDD(this,"kupay",null,1);
          SQLiteDatabase db= dbh.getReadableDatabase();
          Cursor reg = db.query("kupay",new String[]{"imei"},null,null,null,null,null,"1");
          if(reg.moveToFirst()){
              usr=reg.getString(0);
             
          
          }
  	 return usr;
      }
	
}
