package com.kupay;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;

public class MainActivity extends FragmentActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v("app", "1");
		setContentView(R.layout.activity_main);
		 FragmentTabHost mTabHost = (FragmentTabHost) findViewById (android.R.id.tabhost);
		 mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		
		 Log.v("app", "2");

		Bundle b = new Bundle();
		b.putString("key", "comprar");
		mTabHost.addTab(mTabHost.newTabSpec("comprar").setIndicator("", getResources().getDrawable(R.layout.vent)),capturaQR.class, b);
		mTabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.layout.bgcomp); 
		//mTabHost.getChildAt(0).getLayoutParams().height = 80;
		
		Log.v("app", "3");
		b = new Bundle();
		b.putString("key", "transferir");
		mTabHost.addTab(mTabHost.newTabSpec("transferir").setIndicator("", getResources().getDrawable(R.layout.tran)), test.class, b);
		mTabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.layout.bgcomp); 
		//mTabHost.getChildAt(1).getLayoutParams().height = 80;
		Log.v("app", "4");
		
		b = new Bundle();
		b.putString("key", "cobrar");
		mTabHost.addTab(mTabHost.newTabSpec("cobrar").setIndicator("",getResources().getDrawable(R.layout.comp)),test.class, b);
		mTabHost.getTabWidget().getChildAt(2).setBackgroundResource(R.layout.bgcomp); 
		//mTabHost.getChildAt(2).getLayoutParams().height = 80;
		Log.v("app", "5");

	}
	

}
