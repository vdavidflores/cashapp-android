package com.kupay;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

public class MainActivity extends FragmentActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		 FragmentTabHost mTabHost = (FragmentTabHost) findViewById (android.R.id.tabhost);
		 mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		
	

		Bundle b = new Bundle();
		b.putString("key", "comprar");
		mTabHost.addTab(mTabHost.newTabSpec("comprar").setIndicator("comprar", getResources().getDrawable(R.layout.tran)),test.class, b);
		//mTabHost.getChildAt(0).setBackgroundResource(R.layout.bgcomp);
		//mTabHost.getChildAt(0).getLayoutParams().height = 80;
		//
		b = new Bundle();
		b.putString("key", "transferir");
		mTabHost.addTab(mTabHost.newTabSpec("transferir").setIndicator("transferir", getResources().getDrawable(R.layout.tran)), test.class, b);
		//mTabHost.getChildAt(0).setBackgroundResource(R.layout.bgcomp);
		//mTabHost.getChildAt(0).getLayoutParams().height = 80;
		b = new Bundle();
		b.putString("key", "cobrar");
		mTabHost.addTab(mTabHost.newTabSpec("cobrar").setIndicator("cobrar",getResources().getDrawable(R.layout.vent)),test.class, b);
		//mTabHost.getChildAt(0).setBackgroundResource(R.layout.bgcomp);
		//mTabHost.getChildAt(0).getLayoutParams().height = 80;

	}
	

}
