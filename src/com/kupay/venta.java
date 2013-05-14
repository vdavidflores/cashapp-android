package com.kupay;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class venta extends ListFragment {

  @Override
  public void onActivityCreated(Bundle savedInstanceState) { 
	  super.onActivityCreated(savedInstanceState);
    
    ventaw weather_data[]= new ventaw[] { 
    		new ventaw(R.drawable.compm, "Compra" ),
            new ventaw(R.drawable.mdm, "Depósito"),
            new ventaw(R.drawable.tranm, "Transacción"),
            new ventaw(R.drawable.tranm, "Transacción"),
            new ventaw(R.drawable.compm, "Compra")
    		};
    WeatherAdapter adapter = new WeatherAdapter(getActivity(), R.layout.listview_item_row, weather_data);
setListAdapter(adapter);
  }

  public void onListItemClick(ListView l, View v, int position, long id) {
    // Do something with the data

  }
} 

