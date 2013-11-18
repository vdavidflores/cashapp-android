package com.kupay;


import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;




import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MainConteiner extends FragmentActivity  {
	
	

	
	ListView sidemenu;
	SlidingMenu menu;
	OperacionRow menurow[];
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
		
		
	//	menu.setMenu(R.layout.menu_frame);
		menu.setFadeDegree(0.35f);
		//menu.setBehindOffset(2);
		
		menu.setMenu(R.layout.menu_frame);
		

		
	
		sidemenu = (ListView) menu.findViewById(R.id.sideMenuList);
		menurow = new OperacionRow[7];
		menurow[0]= new OperacionRow(R.drawable.mdm, "INICIO",null,null);
		menurow[1]= new OperacionRow(R.drawable.mdm, "Deposito",null,null);
		menurow[2]= new OperacionRow(R.drawable.mdm, "Retiro",null,null);
		menurow[3]= new OperacionRow(R.drawable.mdm, "Llave-ku",null,null);
		menurow[4]= new OperacionRow(R.drawable.mdm, "Perfil",null,null);
		menurow[5]= new OperacionRow(R.drawable.mdm, "Soporte",null,null);
		menurow[6]= new OperacionRow(R.drawable.mdm, "Desenlazar equipo",null,null);
		
	
		WeatherAdapter adapter = new WeatherAdapter(this, R.layout.listview_item_row,menurow);
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
						.replace(R.id.fragment_container, new Deposito(), "deposito")
						.commit();
				        
				//	}
					togle();

				}
				/*switch ((int) id) {
				
				case 0:
					if (fm.findFragmentByTag("mainactivity") == null) {
				        fm.beginTransaction()
						.replace(R.id.fragment1, new MainActivity(), "mainactivity")
						.commit();
				        
						
					}togle();
					break;
				case 1:
										break;

				default:
					break;
				}
				togle();
				*/
			}
		});
			
		
	}
	
	public void togle() {
		menu.toggle();
	}
}
