package com.kupay;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends FragmentActivity {
	ProgressDialog	progress;
	FragmentTabHost mTabHost;
	Button actCC;
	Activity actividad;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		actividad = this;
		Log.v("app", "1");
		setContentView(R.layout.activity_main);
		  mTabHost = (FragmentTabHost) findViewById (android.R.id.tabhost);
		  actCC = (Button) findViewById(R.id.actCC); 
		 mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		 Eventos();
		 ActualizarCC cc = new ActualizarCC(actividad);
		 cc.execute();
		 tabs();
		
		 Log.v("app", "2");

	}
	
	
	
    public void Eventos(){
   //cambia el estado        
        
      actCC.setOnClickListener(new View.OnClickListener() {
          public void onClick(View view) { 
        	  Log.v("app", "actualizar cc..");
        	  ActualizarCC cc = new ActualizarCC(actividad);
        	 cc.execute();
        	 
          }} );
    }
	
	private void tabs(){
		Bundle b = new Bundle();
		b.putString("key", "comprar");
		mTabHost.addTab(mTabHost.newTabSpec("comprar").setIndicator("", getResources().getDrawable(R.layout.comp)),capturaQR.class, b);
		mTabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.layout.bgcomp); 
		mTabHost.getTabWidget().getChildAt(0).getLayoutParams().height = 100;
		mTabHost.getTabWidget().setStripEnabled(false);
		
		//mTabHost.getChildAt(0).getLayoutParams().height = 80;
		
		Log.v("app", "3");
		b = new Bundle();
		b.putString("key", "transferir");
		mTabHost.addTab(mTabHost.newTabSpec("transferir").setIndicator("", getResources().getDrawable(R.layout.tran)), transferencia.class, b);
		mTabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.layout.bgcomp); 
		mTabHost.getTabWidget().getChildAt(1).getLayoutParams().height = 100;
		mTabHost.getTabWidget().setStripEnabled(false);
		
		//mTabHost.getChildAt(1).getLayoutParams().height = 80;
		Log.v("app", "4");
		
		b = new Bundle();
		b.putString("key", "cobrar");
		mTabHost.addTab(mTabHost.newTabSpec("cobrar").setIndicator("",getResources().getDrawable(R.layout.vent)),venta.class, b);
		mTabHost.getTabWidget().getChildAt(2).setBackgroundResource(R.layout.bgcomp);
		mTabHost.getTabWidget().getChildAt(2).getLayoutParams().height = 100;
		mTabHost.getTabWidget().setStripEnabled(false);
		
		
		mTabHost.setCurrentTab(2);
		
		Log.v("app", "5");

	}
	
	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////

	

}
