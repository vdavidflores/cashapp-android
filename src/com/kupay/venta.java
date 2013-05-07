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
    
    String[] values = new String[] { "El Principito", "Playera Zara", "libro Lolita",
        "juguete rojo", "tenis adidas", "Celular Verde"};
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
        android.R.layout.simple_list_item_1, values);
setListAdapter(adapter);
  }

  public void onListItemClick(ListView l, View v, int position, long id) {
    // Do something with the data

  }
} 

