package com.kupay;

import org.json.JSONException;
import org.json.JSONObject;

import com.kupay.Post.OnResponseAsync;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
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
import android.widget.ListPopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Deposito extends Fragment implements OnItemSelectedListener {
	Button navicon;
	Button aceptar;
	EditText cantidad;
	Spinner spinner;
	Post procesT;
	int pin;
	ProgressDialog progress;

	@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
		 View view = View.inflate(this.getActivity(), R.layout.deposito,null);
		 
		 //Spiner de forma de pago
		 String [] values = {"Tineda de conveniencia","Tarjeta bancaria",};
		 spinner = (Spinner) view.findViewById(R.id.spinner1);
		 ArrayAdapter<String> LTRadapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
		 LTRadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		 spinner.setAdapter(LTRadapter);
		    procesT = new Post();
		
		  navicon = (Button)  view.findViewById(R.id.navicon_dep);
		  aceptar = (Button)  view.findViewById(R.id.deposito_aceptar);
		  cantidad = (EditText) view.findViewById(R.id.cantidad_deposito);
		
		  navicon.setOnClickListener(new OnClickListener() {
	          public void onClick(View view) { 
	        	  Activity act = getActivity();

	        	  if(act instanceof MainConteiner) {
	        	      ((MainConteiner) act).togle();
	        	  }
	         	 
	          }} );
		  aceptar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if (!cantidad.getText().toString().equals("")){
					cantidad.setBackgroundColor(Color.WHITE);
					if (spinner.getSelectedItemPosition() == 1){
						listartarjetas();
					}else if (spinner.getSelectedItemPosition() == 0){
						
						// aqui va lo que se hace cuando se deposita por oxxo
						
					}
				}else{
					Toast.makeText(getActivity(), "Espesifica la catidad", Toast.LENGTH_LONG).show();
					cantidad.setBackgroundColor(Color.YELLOW);
				}
			}
		});

		  procesT.setOnResponseAsync(new OnResponseAsync() {
			
			@Override
			public void onResponseAsync(JSONObject response) {
				// TODO Auto-generated method stub
				progress.dismiss();
				try {
					if(response.getString("RESULTADO").equals("EXITO")){
						Toast.makeText(getActivity(), "Abono exitoso!", Toast.LENGTH_LONG).show();
					}else{
						Toast.makeText(getActivity(), "Abono fallido!", Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		 return view; 
		 
	 }

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		Log.v("app", "nada seleccionado");
	}

	public void listartarjetas() {
		AlertDialog.Builder builderSingle = new AlertDialog.Builder(
				this.getActivity());
		builderSingle.setTitle("Seecciona una tarjeta");
		final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
				this.getActivity(), android.R.layout.select_dialog_item);
		arrayAdapter.add("+ NUEVA TARJETA");
		
			//se buscan las tarjetas registradas y se enlistn
		 	BDD dbh = new BDD(getActivity().getApplicationContext(),"kupay",null,1);
	        SQLiteDatabase db= dbh.getWritableDatabase();
	        Cursor c = db.rawQuery("select * from kupayTarjetas",null);
	        //c.moveToFirst();
	        while (c.moveToNext()) {
	        	arrayAdapter.add(c.getString(1).toString());
			}
	        
	      //  Toast.makeText(getActivity(), "tarjeta: "+c.getString(0), Toast.LENGTH_LONG).show();
	
		
		builderSingle.setNeutralButton("cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		builderSingle.setAdapter(arrayAdapter,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Log.v("app", "dialogo " + Integer.toString(which));
						if (which == 0) {
							Intent intent = new Intent(getActivity(),
									NuevaTarjeta.class);
							startActivityForResult(intent, 450);
						}else{
							pinYtarjeta(which);
							Toast.makeText(getActivity(), "pagar con tarjeta id: "+(which-1), Toast.LENGTH_LONG).show();
						}
					}
				});
		builderSingle.show();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == 450 && resultCode == Activity.RESULT_OK){
			
			pinYtarjeta(data.getIntExtra("tarjeta_id",-1));
		//	procesarTarjeta(data.getIntExtra("indideTarjeta", 0));
		}
	}
	
	private void procesarTarjeta(int indiceTarjeta) {
		if (indiceTarjeta != -1){
			progress = ProgressDialog.show(getActivity(), "Transaccion en proceso", "procesando tarjeta...");
			BDD dbh = new BDD(getActivity().getApplicationContext(),"kupay",null,1);
	        SQLiteDatabase db= dbh.getWritableDatabase();
	        Log.v("app", "tarjeta id: "+Integer.toString(indiceTarjeta));
	        Cursor c = db.rawQuery("select * from kupayTarjetas where id=?",new String[]{Integer.toString(indiceTarjeta)});
	        c.moveToFirst();
	     //  c.moveToNext();
	        	
	        Log.v("app", "numero de tarjeta cryp: "+ c.getString(1));
		        
		      //  c.moveToFirst();
		        if(c.getCount()>=1){
		        	 Log.v("app", "numero de tarjeta cryp: "+ c.getString(2));
		        	JSONObject data = new JSONObject();
		        	
		        	try {
						data.put("numero_tarjeta_cryp", c.getString(2));
						data.put("nombre_titular_cryp", c.getString(3));
						data.put("exp_mes_cryp",c.getString(4));
						data.put("exp_anio_cryp", c.getString(5));
						data.put("cvv_cryp", c.getString(6));
						data.put("monto", cantidad.getText().toString());
						data.put("usr", MiUsuario());
						data.put("imei", MiImei());
						data.put("pin", pin);
						
						
						procesT.setData(16, data);
						procesT.execAsync(getActivity().getApplicationContext());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	
		        }
		        progress.dismiss();
		        	
			
		}
		
	}
	
	private void pinYtarjeta(final int Tarjetaid){
		
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
            	 	procesarTarjeta(Tarjetaid);
            	
            }
        });
		builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            
            }
        });

		AlertDialog dialog = builder.create();
    	dialog.show();
    
        	
    	
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
