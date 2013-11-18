package com.kupay;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Deposito extends Fragment {

	
	 @Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
		 View view = View.inflate(getActivity().getApplicationContext(), R.layout.deposito,null);
		 return view; 
		 
	 }
}
