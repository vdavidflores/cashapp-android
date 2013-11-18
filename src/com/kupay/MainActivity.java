package com.kupay;





import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentManager;
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
	Button actCC;
	Button navicon;
	Activity actividad;
	private View mRoot;
	
	/*
	 onDecoderNoVisible Decodercallbak;
	    
	    public interface onDecoderNoVisible {
	        public void onDecoderNoEsVisible();
	    }*/

	/*
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		actividad = this;
		Log.v("app", "1");

		setContentView(R.layout.activity_main);
		actividad = this;
		Log.v("app", "1");
		  mTabHost = (TabHost) findViewById (android.R.id.tabhost);
		  Log.v("app", "2");
		  actCC = (Button)  findViewById(R.id.actCC); 
		  Log.v("app", "2");
		  navicon = (Button)  findViewById(R.id.navicon);
		  Log.v("app", "3");
		 mTabHost.setup();
		 Log.v("app", "4");
		 Eventos();
		 ActualizarCC cc = new ActualizarCC(actividad);
		 cc.execute();
		 tabs();
		 
		 

	

	}*/
	

	@Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
	      Bundle savedInstanceState) {
	    mRoot = inflater.inflate(R.layout.activity_main,null);
	   
		actividad = getActivity();
		Log.v("app", "1");
		  mTabHost = (TabHost) mRoot.findViewById (android.R.id.tabhost);
		  mTabHost.setup();
		  actCC = (Button)  mRoot.findViewById(R.id.actCC); 
		  navicon = (Button)  mRoot.findViewById(R.id.navicon);
		

		 Eventos();
		 ActualizarCC cc = new ActualizarCC(actividad);
		 cc.execute();
		 tabs();
	    return mRoot;
	  }

	
	
    public void Eventos(){
   //cambia el estado        
        
      actCC.setOnClickListener(new OnClickListener() {
          public void onClick(View view) { 
        	  Log.v("app", "actualizar cc..");
        	  ActualizarCC cc = new ActualizarCC(actividad);
        	 cc.execute();
        	 
        	 
        	 int duracion=Toast.LENGTH_SHORT;
             Toast mensaje=Toast.makeText(getActivity(), "¡Saldo Actualizado!", duracion);
             mensaje.show();
             mensaje.setGravity(Gravity.TOP|Gravity.RIGHT, 0, 0);
        	 
        	 
          }} );
      
      
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

		mTabHost.setCurrentTab(1);

	}
    
	private void tabs(){
		Log.v("app", "5");
		
		//TabHost tabHost = mTabHost;
        
       /* // Tab for Photos
        TabSpec photospec = tabHost.newTabSpec("comprar");
        // setting Title and Icon for the Tab
        photospec.setIndicator("", getResources().getDrawable(R.layout.compratab));
     //   Intent photosIntent = new Intent(mRoot.getContext(), capturaQR.class);
      //  photospec.setContent(photosIntent);
        Log.v("app", "6");
        // Tab for Songs
        TabSpec songspec = tabHost.newTabSpec("transferir");        
        songspec.setIndicator("", getResources().getDrawable(R.layout.transferitab));
      //  Intent songsIntent = new Intent(mRoot.getContext(), transferencia.class);
      //  songspec.setContent(songsIntent);
         
        // Tab for Videos
        TabSpec videospec = tabHost.newTabSpec("consulta");
        videospec.setIndicator("consulta", getResources().getDrawable(R.layout.consultatab));
       // Intent videosIntent = new Intent(mRoot.getContext(), Consulta.class);
      //  videospec.setContent(videosIntent);
         
        // Adding all TabSpec to TabHost
        tabHost.addTab(photospec); // Adding photos tab
        tabHost.addTab(songspec); // Adding songs tab
        tabHost.addTab(videospec); // Adding videos tab
		*/
		
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
		b.putString("key", "consultar");
		mTabHost.addTab(mTabHost.newTabSpec("consultar").setIndicator("",getResources().getDrawable(R.layout.consultatab)).setContent(R.id.tab_3));
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
				if (tabId.equals("transferir")){
					if (fm.findFragmentByTag(tabId) == null) {
				
					fm.beginTransaction()
							.replace(R.id.tab_2, new transferencia(), tabId)
							.commit();
					//Decodercallbak.onDecoderNoEsVisible();
					}
					
				}else if(tabId.equals("comprar")){
					
					if (fm.findFragmentByTag(tabId) == null) {
						fm.beginTransaction()
						.replace(R.id.tab_1, new capturaQR(), tabId)
						.commit();
						
					}
				}else if(tabId.equals("consultar")) {
					if (fm.findFragmentByTag(tabId) == null) {
						fm.beginTransaction()
						.replace(R.id.tab_3, new Consulta(), tabId)
						.commit();
						//Decodercallbak.onDecoderNoEsVisible();
					}
				}
			}
		});
		
		
		Log.v("app", "5");

	}
	

	
	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////

	

}
