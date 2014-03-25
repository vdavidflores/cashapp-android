package com.kupay;

import org.json.JSONException;
import org.json.JSONObject;

import com.kupay.Post.OnResponseAsync;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Cobrar extends Fragment{
	
	EditText monto;
	EditText concepto;
Button enviar;
Post pedirQR;
int pin;
private ProgressDialog progress;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		View view = inflater.inflate(R.layout.activity_cobrar, container, false);
		monto = (EditText) view.findViewById(R.id.monto);
		concepto = (EditText) view.findViewById(R.id.concepto);
		enviar  = (Button) view.findViewById(R.id.enviar);
		pedirQR = new Post();
		eventos();
		return view;
	}

private void eventos() {
	enviar.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
		
			 if (!monto.getText().toString().equals("")){
        		 monto.setBackgroundColor(Color.WHITE);
        		 if(!concepto.getText().toString().equals("")){
        			 concepto.setBackgroundColor(Color.WHITE);
        			 pin();
        		 }else {
						Toast.makeText(getActivity(), "Indica el concepto", Toast.LENGTH_LONG).show();
						concepto.requestFocus();
						concepto.setBackgroundColor(Color.YELLOW);
					}

         	 }else {
					Toast.makeText(getActivity(), "Indica el monto", Toast.LENGTH_LONG).show();
					concepto.setBackgroundColor(Color.WHITE);
					monto.requestFocus();
					monto.setBackgroundColor(Color.YELLOW);
				}
		}
	});
	
	pedirQR.setOnResponseAsync(new OnResponseAsync() {
		
		@Override
		public void onResponseAsync(JSONObject response) {
			// TODO Auto-generated method stub
			progress.dismiss();
			
			 try {
				String resultado = response.getString("RESULTADO");
				JSONObject datos = response.getJSONObject("DATOS");
				
				
				if (resultado.equals("EXITO")){

					Intent in = new Intent(getActivity(), OperacionQR.class);
					in.putExtra("OPERACION", datos.getString("OPERACION").toString());
					startActivity(in);
					
				}else{
					AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
					adb.setTitle("Error al crear el cargo");
					adb.setMessage("Lo sentimos.\nNo se ha creado tu cargo, verifica tu conexión e intenta nuevamente");
					adb.setNeutralButton("Acepar", null);
					AlertDialog ad = adb.create();
					ad.show();
				}
				
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	});
}


private void pin(){
	
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
        	 }
        else{ 
             pin = Integer.parseInt(Password.getEditableText().toString()) ;
        	 generar();
        	 }
        }
    });
	builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
        	monto.setText("");
        	concepto.setText("");
        	dialog.dismiss();
        }
    });
	AlertDialog dialog = builder.create();
	dialog.show();	
}	


private void  generar() {
	 
	 //Se arma el json
	 JSONObject data = new JSONObject();
		try {
			
				data.put("receptor","");
			
			data.put("usr", MiUsuario());
			data.put("monto", monto.getText().toString());
			data.put("concepto", concepto.getText().toString());
			data.put("imei", MiImei());
			data.put("tipo", "CARGO");
			data.put("pin", pin);
			
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 //se llama al servidor par pedir el qr
		pedirQR.setData(3, data);
		pedirQR.execAsync(getActivity());
	 //se muestra progreso
	 progress = ProgressDialog.show(getActivity(), "Generando cobro", "Espera un momento...");

}

private String MiUsuario(){
	String usr = null;
    BDD dbh = new BDD(getActivity(),"kupay",null,1);
    SQLiteDatabase db= dbh.getReadableDatabase();
    Cursor reg = db.query("kupay",new String[]{"usr"},null,null,null,null,null,"1");
    if(reg.moveToFirst()){
        usr=reg.getString(0);
       
    
    }
 return usr;
}
private String MiImei(){
	String usr = null;
    BDD dbh = new BDD(getActivity(),"kupay",null,1);
    SQLiteDatabase db= dbh.getReadableDatabase();
    Cursor reg = db.query("kupay",new String[]{"imei"},null,null,null,null,null,"1");
    if(reg.moveToFirst()){
        usr=reg.getString(0);
       
    
    }
 return usr;
}


}
