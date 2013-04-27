package com.kupay;





import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
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
		specs.setIndicator("", getResources().getDrawable(R.layout.comp));
		th.addTab(specs);
		th.getTabWidget().getChildAt(0).setBackgroundResource(R.layout.bgcomp);
		th.getTabWidget().getChildAt(0).getLayoutParams().height = 80;
		
		specs = th.newTabSpec("tag2");
		specs.setContent(R.id.tran);
		specs.setIndicator("", getResources().getDrawable(R.layout.tran));
		th.addTab(specs);
		th.getTabWidget().getChildAt(1).setBackgroundResource(R.layout.bgcomp);
		th.getTabWidget().getChildAt(1).getLayoutParams().height = 80;
		
		specs = th.newTabSpec("tag3");
		specs.setContent(R.id.vent);
		specs.setIndicator("", getResources().getDrawable(R.layout.vent));
		th.addTab(specs);
		th.getTabWidget().getChildAt(2).setBackgroundResource(R.layout.bgcomp);
		th.getTabWidget().getChildAt(2).getLayoutParams().height = 80;
	}
	

}
