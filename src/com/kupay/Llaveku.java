package com.kupay;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Llaveku extends Fragment implements OnItemSelectedListener {
	Button navicon;
	
	 @Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
		 View view = View.inflate(getActivity().getApplicationContext(), R.layout.llaveku,null);
		 
		 Spinner spinner = (Spinner) view.findViewById(R.id.spinner1);
		 
		
		  navicon = (Button)  view.findViewById(R.id.navicon_dep);
		
		  navicon.setOnClickListener(new OnClickListener() {
	          public void onClick(View view) { 
	        	  Activity act = getActivity();

	        	  if(act instanceof MainConteiner) {
	        	      ((MainConteiner) act).togle();
	        	  }
	         	 
	          }} );


		  
		  
		 
		 return view; 
		 
	 }
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

	 
	 
	

	
	
	
	
	
	
	
	
}
