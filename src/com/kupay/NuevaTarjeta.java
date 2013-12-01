package com.kupay;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class NuevaTarjeta extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nueva_tarjeta);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nueva_tarjeta, menu);
		return true;
	}

}
