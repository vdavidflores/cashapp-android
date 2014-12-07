package com.kupay;





import org.json.JSONException;
import org.json.JSONObject;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.kupay.Post.OnResponseAsync;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

public class MainActivity extends Fragment {
	ProgressDialog	progress;
	TabHost mTabHost;
	Button navicon;
	Activity actividad;

	 //Post monitorCC;
	private boolean camaraCargada = false;
	
	private View mRoot;

	
	



	@Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
	      Bundle savedInstanceState) {
	    mRoot = inflater.inflate(R.layout.activity_main,null);
	   
		actividad = getActivity();
		Log.v("app", "1");
		  mTabHost = (TabHost) mRoot.findViewById (android.R.id.tabhost);
		  mTabHost.setup();
		  navicon = (Button)  mRoot.findViewById(R.id.navicon);
	


		  Eventos();
		 
		 tabs();
	    return mRoot;
	  }

	
	
    public void Eventos(){
   //cambia el estado        
      
      navicon.setOnClickListener(new OnClickListener() {
          public void onClick(View view) { 
        	  Activity act = getActivity();

        	  if(act instanceof MainConteiner) {
        	      ((MainConteiner) act).togle();
        	  }
         	 
          }} );
      
    }
	
    @Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);
		FragmentManager fm = getFragmentManager();
		fm.beginTransaction()
		.replace(R.id.tab_3, new Cobrar(), "cobrar")
		.replace(R.id.tab_1, new capturaQR(), "comprar")
		.replace(R.id.tab_2, new transferencia(), "transferir")
		.commit();
		mTabHost.setCurrentTab(1);
		
		
		
	}
    
    @Override
    public void onPause(){
    	//monitorCC.stopAsync();
    	super.onPause();
    }
    
    @Override
    public void onResume(){

			
		  super.onResume();
    }
  
    @Override
    public void onDestroy() {
    	
        Log.v("app", " MAIN ACTIVITIE onDestroy()");
        //monitorCC.stopAsync();
        
        //cc.cancel(true);
   super.onDestroy();
    }


    
	private void tabs(){
		Log.v("app", "5");
		Bundle b = new Bundle();
		b.putString("key", "comprar");
		mTabHost.addTab(mTabHost.newTabSpec("comprar").setIndicator("", getResources().getDrawable(R.layout.compratab)).setContent(R.id.tab_1));
		mTabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.layout.bgcomp); 
		mTabHost.getTabWidget().getChildAt(0).getLayoutParams().height = LayoutParams.MATCH_PARENT;
		mTabHost.getTabWidget().getChildAt(0).getLayoutParams().width = LayoutParams.MATCH_PARENT;
		mTabHost.getTabWidget().setStripEnabled(false);
		
		//mTabHost.getChildAt(0).getLayoutParams().height = 80;
		
		Log.v("app", "3");
		b = new Bundle();
		b.putString("key", "transferir");
		mTabHost.addTab(mTabHost.newTabSpec("transferir").setIndicator("", getResources().getDrawable(R.layout.transferitab)).setContent(R.id.tab_2));
		mTabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.layout.bgcomp); 
		mTabHost.getTabWidget().getChildAt(1).getLayoutParams().height = LayoutParams.MATCH_PARENT;
		mTabHost.getTabWidget().getChildAt(1).getLayoutParams().width = LayoutParams.MATCH_PARENT;
		mTabHost.getTabWidget().setStripEnabled(false);
		
		//mTabHost.getChildAt(1).getLayoutParams().height = 80;
		Log.v("app", "4");
		
		b = new Bundle();
		b.putString("key", "cobrar");
		mTabHost.addTab(mTabHost.newTabSpec("cobrar").setIndicator("",getResources().getDrawable(R.layout.cobrotab)).setContent(R.id.tab_3));
		mTabHost.getTabWidget().getChildAt(2).setBackgroundResource(R.layout.bgcomp);
		mTabHost.getTabWidget().getChildAt(2).getLayoutParams().height =  LayoutParams.MATCH_PARENT;
		mTabHost.getTabWidget().getChildAt(2).getLayoutParams().width = LayoutParams.MATCH_PARENT;
		mTabHost.getTabWidget().setStripEnabled(false);
		
		
	
		
		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				Log.v("app", tabId);
				
				
				FragmentManager fm = getFragmentManager();
				if (tabId.equals("cobrar")){
					if (fm.findFragmentByTag(tabId) == null) {
				
					fm.beginTransaction()
							.replace(R.id.tab_3, new Cobrar(), tabId)
							.commit();
					
					}
					capturaQR camaraPointer = (capturaQR) getFragmentManager().findFragmentById(R.id.tab_1);
					if(camaraCargada){
						camaraPointer.stopCamera(); 
					}
					
				}else if (tabId.equals("transferir")){
					if (fm.findFragmentByTag(tabId) == null) {
				
					fm.beginTransaction()
							.replace(R.id.tab_2, new transferencia(), tabId)
							.commit();
					
					}
					capturaQR camaraPointer = (capturaQR) getFragmentManager().findFragmentById(R.id.tab_1);
					if(camaraCargada){
						camaraPointer.stopCamera(); 
					}
					
				}else if(tabId.equals("comprar")){
					Log.v("app","CAMBIANDO a comprar");
					if (fm.findFragmentByTag(tabId) == null) {
						Log.v("app", "cargando captura qr fragment");
						Log.v("app", "el fragment ES nulo");
						fm.beginTransaction()
						.replace(R.id.tab_1, new capturaQR(), tabId)
						.commit();
					}
						capturaQR camaraPointer = (capturaQR) getFragmentManager().findFragmentById(R.id.tab_1);
						if(!camaraCargada){
							Log.v("app", "nunca antes se ha cargado la camara");
							Log.v("app", "ENSENDIENDO camara");
							camaraPointer.startPreview();
						} else{
							Log.v("app", "ya antes se ha cargado la camara");
							camaraPointer.restartCam();
						}
						camaraCargada = true;
				}
				
				
				
			}
		});
		
		
		Log.v("app", "5");

	}
	

	
	
	

	
	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////

	private String MiUsuario(){
    	String usr = null;
        BDD dbh = new BDD(getActivity(),"kupay",null,1);
        SQLiteDatabase db= dbh.getReadableDatabase();
        Cursor reg = db.query("kupay",new String[]{"usr"},null,null,null,null,null,"1");
        if(reg.moveToFirst()){
            usr=reg.getString(0);
           
        
        }
	 return usr;
    }
    
    private String MiImei(){
    	String imei = null;
        BDD dbh = new BDD(getActivity(),"kupay",null,1);
        SQLiteDatabase db= dbh.getReadableDatabase();
        Cursor reg = db.query("kupay",new String[]{"imei"},null,null,null,null,null,"1");
        if(reg.moveToFirst()){
            imei=reg.getString(0);
           
        
        }
	 return imei;
    }

}
