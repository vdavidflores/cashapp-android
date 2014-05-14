package com.kupay;

import org.json.JSONException;
import org.json.JSONObject;

import com.kupay.Post.OnResponseAsync;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Llaveku extends Fragment {
	Button navicon, getllav;
	EditText llavetext;
	Post getServllav;
	 int pin;
	 @Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
		 View view = View.inflate(getActivity().getApplicationContext(), R.layout.llaveku,null);
		 
		 
		 getServllav = new Post();
		getllav = (Button) view.findViewById(R.id.getllave);
		llavetext = (EditText) view.findViewById(R.id.llaveq);
		  navicon = (Button)  view.findViewById(R.id.navicon_dep);
		
		  navicon.setOnClickListener(new OnClickListener() {
	          public void onClick(View view) { 
	        	  Activity act = getActivity();

	        	  if(act instanceof MainConteiner) {
	        	      ((MainConteiner) act).togle();
	        	  }
	         	 
	          }} );
		  getllav.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			 PIN();
				
			}
		});

		  
		  getServllav.setOnResponseAsync(new OnResponseAsync() {
			
			@Override
			public void onResponseAsync(JSONObject response) {
				try {
					if(response.getString("RESULTADO").equals("EXITO")){
						Toast.makeText(getActivity(), "Nueva llave!", Toast.LENGTH_LONG).show();
						
						JSONObject datos = response.getJSONObject("DATOS");
						llavetext.setText(datos.getString("llave_ku").toString());
						
					}else{
						Toast.makeText(getActivity(), "Solicitud fallida!", Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		 
		 return view; 
		 
	 }

	 private void PIN(){
			
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setIcon(R.drawable.pin);
			builder.setTitle("Ingresa tu Pin");
			builder.setMessage("Inserta tu Pin");
			
			
			final EditText Password = new EditText(getActivity());
			
			builder.setView(Password);
			Password.setGravity(Gravity.CENTER);
		     Password.setHint("pin");
		     Password.setWidth(200);
			 Password.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_PASSWORD);
			 Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
			 builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
				
	            public void onClick(DialogInterface dialog, int id) {
	         
	                 pin = Integer.parseInt(Password.getEditableText().toString()) ;
	                 obtenerLLAVE();
	            }
	        });
			builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int id) {
	            	
	            }
	        });

			AlertDialog dialog = builder.create();
	    	dialog.show();
	    
	        	
	    	
	    }	
	 
	 private void obtenerLLAVE() {
		JSONObject data = new JSONObject();
		
		
		try {
			data.put("usr", MiUsuario());
			data.put("imei", MiImei());
			data.put("pin", pin);
			getServllav.setData(13,data);
			getServllav.execAsync(getActivity().getApplicationContext());
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	 private String MiUsuario(){
	    	String usr = null;
	        BDD dbh = new BDD(getActivity().getApplicationContext(),"kupay",null,1);
	        SQLiteDatabase db= dbh.getReadableDatabase();
	        Cursor reg = db.query("kupay",new String[]{"usr"},null,null,null,null,null,"1");
	        if(reg.moveToFirst()){
	            usr=reg.getString(0);
	           
	        
	        }
		 return usr;
	    }
	    
	    private String MiImei(){
	    	String imei = null;
	        BDD dbh = new BDD(getActivity().getApplicationContext(),"kupay",null,1);
	        SQLiteDatabase db= dbh.getReadableDatabase();
	        Cursor reg = db.query("kupay",new String[]{"imei"},null,null,null,null,null,"1");
	        if(reg.moveToFirst()){
	            imei=reg.getString(0);
	           
	        
	        }
		 return imei;
	    }
	
	
	
	
	
	
	
	
}
