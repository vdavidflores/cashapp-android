package com.kupay;

import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;

import com.kupay.Post.OnResponseAsync;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
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

public class Desenlazar extends Fragment   {
	Button navicon, desenlazbtn;
	Post soket;
	int pin;
	
	 @Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
		 View view = View.inflate(getActivity().getApplicationContext(), R.layout.desenlazar,null);
		 soket = new Post();
		soket.setOnResponseAsync(new OnResponseAsync() {
			
			@Override
			public void onResponseAsync(JSONObject response) {
				// TODO Auto-generated method stub
				try {
					
					if(response.getString("RESULTADO").equals("EXITO")){
						
						nuevoUsurio("nill", getActivity());
						nuevoImei("nill", getActivity());
						 android.os.Process.killProcess(android.os.Process.myPid());
						    Editor editor = getActivity().getSharedPreferences("clear_cache", Context.MODE_PRIVATE).edit();
						    editor.clear();
						    editor.commit();
						    clearApplicationData();
						    
					}else{
						Toast.makeText(getActivity(), "Falla en desenlaze", Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		 
		 desenlazbtn = (Button) view.findViewById(R.id.desenlazebtn);
		 desenlazbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				PIN();
				
			}
		});
		
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
	         
	               
	            	
		            if (Password.getEditableText().length() == 0){
		            		 
		            		 dialog.dismiss();
		            }  else{ 
		            	
		            	  pin = Integer.parseInt(Password.getEditableText().toString()) ;
			            	JSONObject data = new JSONObject();
		            	try {
		            		
			            	data.put("usr", MiUsuario());
			            	data.put("imei", MiImei());
							data.put("pin", pin);
							
							soket.setData(17, data);
							soket.execAsync(getActivity().getApplicationContext());
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            }
	                 
	                 
	            	
	            }
	        });
			builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int id) {
	            pin = 0;
	            }
	        });

			AlertDialog dialog = builder.create();
	    	dialog.show();
	    
	        	
	    	
	    }
	
	 
	/* 
	
	 public static void trimCache(Context context) {
		    try {
		        File dir = context.getCacheDir();
		        if (dir != null && dir.isDirectory()) {
		            deleteDir(dir);

		        }
		    } catch (Exception e) {
		        // TODO: handle exception
		    }
		}
	
	
	 public static boolean deleteDir(File dir) {
		    if (dir != null && dir.isDirectory()) {
		        String[] children = dir.list();
		        for (int i = 0; i < children.length; i++) {
		            boolean success = deleteDir(new File(dir, children[i]));
		            if (!success) {
		                return false;
		            }
		        }
		    }

		    // <uses-permission
		    // android:name="android.permission.CLEAR_APP_CACHE"></uses-permission>
		    // The directory is now empty so delete it

		    return dir.delete();
		}*/
	
	 public void clearApplicationData() 
     {
       File cache = getActivity().getCacheDir();
       File appDir = new File(cache.getParent());
        if (appDir.exists()) {
        String[] children = appDir.list();
        for (String s : children) {
            if (!s.equals("lib")) {
                deleteDir(new File(appDir, s));
                Log.i("TAG", "**************** File /data/data/APP_PACKAGE/" + s + " DELETED *******************");
            }
        }
    }
}
 public static boolean deleteDir(File dir) {
    if (dir != null && dir.isDirectory()) {
        String[] children = dir.list();
        for (int i = 0; i < children.length; i++) {
            boolean success = deleteDir(new File(dir, children[i]));
            if (!success) {
                return false;
            }
        }
    }
    return dir.delete();
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
	    private void nuevoUsurio(String dat,Context cont){
	        BDD dbh = new BDD(cont,"kupay",null,1);
	        SQLiteDatabase db= dbh.getWritableDatabase();
	        db.execSQL("UPDATE kupay SET usr='"+dat+"'");
	      }
	    private void nuevoImei(String dat,Context cont){
	        BDD dbh = new BDD(cont,"kupay",null,1);
	        SQLiteDatabase db= dbh.getWritableDatabase();
	        db.execSQL("UPDATE kupay SET imei='"+dat+"'");
	      }
	
	
}
