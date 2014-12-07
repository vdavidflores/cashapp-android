package com.kupay;


import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;




import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
	
	

	
	ListView sidemenu;
	SlidingMenu menu;
	TextView usuario;
	TextView MontoSaldo;
	OperacionRow menurow[];
	
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
		
		

		usuario = (AutoResizeTextView) menu.findViewById(R.id.mail_navicon);
		MontoSaldo = (TextView) menu.findViewById(R.id.montoSaldo);
		 cc = new ActualizarCC(this);
		 cc.execute();
		
		
		usuario.setText(MiUsuario());
		
		sidemenu = (ListView) menu.findViewById(R.id.sideMenuList);
		menurow = new OperacionRow[6];
		menurow[0]= new OperacionRow(R.drawable.mdm, "Operaciones",null,null);
		menurow[1]= new OperacionRow(R.drawable.deposito, "Depósito",null,null);
		menurow[2]= new OperacionRow(R.drawable.retiro, "Retiro",null,null);
		menurow[3]= new OperacionRow(R.drawable.llavek, "Código de Acceso",null,null);
		menurow[4]= new OperacionRow(R.drawable.desenlazar, "Desenlazar equipo",null,null);
		menurow[5]= new OperacionRow(R.drawable.info, "Cashapp",null,null);
	
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
						.replace(R.id.fragment_container, new Deposito(), "deposito")
						.commit();
				        
				//	}
					togle();

				}
				else  if(id== 2){
					//if (fm.findFragmentByTag("deposito") == null) {
				       fm.beginTransaction()
						.replace(R.id.fragment_container, new Retiro(), "retiro")
						.commit();
				        
				//	}
					togle();

				}
				else  if(id== 3){
					//if (fm.findFragmentByTag("deposito") == null) {
				        fm.beginTransaction()
						.replace(R.id.fragment_container, new Llaveku(), "llveku")
						.commit();
				        
				//	}
					togle();

				}
				else  if(id== 4){
					//if (fm.findFragmentByTag("deposito") == null) {
				        fm.beginTransaction()
						.replace(R.id.fragment_container, new Desenlazar(), "desenlazar")
						.commit();
				        
				//	}
					togle();

				}
				else  if(id== 5){
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
	
	
}
