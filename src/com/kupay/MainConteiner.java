package com.kupay;


import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.widget.FrameLayout;

public class MainConteiner extends FragmentActivity  {
	
	SlidingMenu menu;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_conteiner);
		if (findViewById(R.id.fragment_container) != null) {
		FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        MainActivity myFragment = new MainActivity();
        myFragment.setArguments(getIntent().getExtras());
        ft.add(R.id.fragment_container, myFragment);
        ft.commit();
        
		}
		
		
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidth(5);
		menu.setFadeDegree(0.0f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		
		
		Display display = getWindowManager().getDefaultDisplay();
		menu.setBehindWidth((display.getWidth()/3)*2);
		
		menu.setMenu(R.layout.menu_frame);
		menu.setFadeDegree(0.35f);
		//menu.setBehindOffset(2);
		
			
		
	}
	
	public void togle() {
		menu.toggle();
	}
}
