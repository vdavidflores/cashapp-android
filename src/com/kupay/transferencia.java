package com.kupay;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class transferencia extends Fragment{

	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.transferencia, container, false);
    }
}
	
	
	
	/*@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.v("tran","1");
		super.onCreate(savedInstanceState);
		Log.v("tran","2");
		Log.v("tran","3");
	
	}

	
	public View setCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.transferencia,null);
		return v;
		}*/

			
	


	

