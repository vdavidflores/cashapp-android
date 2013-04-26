package com.kupay;





import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		TabHost th = (TabHost) findViewById (R.id.tabhost);
		th.setup();
		
		TabSpec specs = th.newTabSpec("tag1");
		specs.setContent(R.id.comp);
		specs.setIndicator("Compra", getResources().getDrawable(R.layout.comp));
		th.addTab(specs);
		
		specs = th.newTabSpec("tag2");
		specs.setContent(R.id.tran);
		specs.setIndicator("Transferencia", getResources().getDrawable(R.layout.tran));
		th.addTab(specs);
		
		specs = th.newTabSpec("tag3");
		specs.setContent(R.id.vent);
		specs.setIndicator("Venta", getResources().getDrawable(R.layout.vent));
		th.addTab(specs);
		
	}
	
	
	



}
