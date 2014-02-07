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
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RetiroSpei extends Activity {

	
	Button aceptar, cancelar;
	EditText beneficiario, clabe;
	TextView monto;
	Post retiroSpei;
	ProgressDialog progress;
	int pin;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_retiro_spei);
		aceptar = (Button) findViewById(R.id.aceptar_spei);
		cancelar = (Button) findViewById(R.id.cancelar_spei);
		beneficiario = (EditText) findViewById(R.id.beneficiario_nombre);
		clabe = (EditText) findViewById(R.id.clabe);
		monto = (TextView) findViewById(R.id.monto_view);
		retiroSpei = new Post();
		
		Intent in = getIntent();
		monto.setText(in.getStringExtra("monto"));
		eventos();
		
	}

private void eventos() {
		aceptar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!beneficiario.getText().toString().equals("")){
					beneficiario.setBackgroundColor(Color.WHITE);
					if (!clabe.getText().toString().equals("") && (clabe.getText().length() == 18)){
						clabe.setBackgroundColor(Color.WHITE);
						
						pin();
						
					}else{
						Toast.makeText(getApplicationContext(), "Espesifica la CLABE (18 digitos)", Toast.LENGTH_LONG).show();
						clabe.setBackgroundColor(Color.YELLOW);
						clabe.requestFocus();
					}
					
					
				}else{
					Toast.makeText(getApplicationContext(), "Espesifica la Beneficiario", Toast.LENGTH_LONG).show();
					beneficiario.setBackgroundColor(Color.YELLOW);
					beneficiario.requestFocus();
				}
				
				
			}
		});
		
		cancelar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				
			}
		});
		
		retiroSpei.setOnResponseAsync(new OnResponseAsync() {
			
			@Override
			public void onResponseAsync(JSONObject response) {
				progress.dismiss();
				// TODO Auto-generated method stub
				try {
					if(response.getString("RESULTADO").toString().equals("EXITO")){
						JSONObject datos = response.getJSONObject("DATOS");
						mensajeExito(datos.getString("MENSAJE"));
						
					}else if(response.getString("RESULTADO").toString().equals("FALLA")){
						JSONObject datos = response.getJSONObject("DATOS");
						mensajeFalla(datos.getString("MENSAJE"));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
}

private void  mensajeExito(String msj) {
	AlertDialog.Builder adb = new AlertDialog.Builder(this);
	adb.setTitle("Retiro exitososo");
	//"El saldo ha sido retirado exitosamente de tu cuenta kupay y en brebe sera transferido a tu cuenta de banco (2 dias aprox)"
	adb.setMessage(msj);
	adb.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
		
        public void onClick(DialogInterface dialog, int id) {
        	finish();
        }
    });
	AlertDialog ad= adb.create();
	ad.show();
}
private void  mensajeFalla(String msj) {
	AlertDialog.Builder adb = new AlertDialog.Builder(this);
	adb.setTitle("Retiro fallido");
	//"El saldo ha sido retirado exitosamente de tu cuenta kupay y en brebe sera transferido a tu cuenta de banco (2 dias aprox)"
	adb.setMessage(msj);
	adb.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
		
        public void onClick(DialogInterface dialog, int id) {
        	dialog.dismiss();
        }
    });
	AlertDialog ad= adb.create();
	ad.show();
}


private void pin(){
	
	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	builder.setIcon(R.drawable.pin);
	builder.setTitle("Ingresa tu Pin");
	builder.setMessage("Inserta tu Pin");
	
	
	final EditText Password = new EditText(this);
	
	builder.setView(Password);
	Password.setGravity(Gravity.CENTER);
     Password.setHint("pin");
     Password.setWidth(200);
	 Password.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_PASSWORD);
	 Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
	 builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
		
        public void onClick(DialogInterface dialog, int id) {
        	if (Password.getText().length() == 0){
        		 
        		 dialog.dismiss();
        	 }
        else{ 
             pin = Integer.parseInt(Password.getEditableText().toString()) ;
             JSONObject dat = new JSONObject();
				try {
					dat.put("pin", pin);
					dat.put("imei", MiImei());
					dat.put("usr", MiUsuario());
					dat.put("monto", monto.getText().toString());
					dat.put("beneficiario", beneficiario.getText().toString());
					dat.put("clabe", clabe.getText().toString());
					 retiroSpei.setData(22, dat);
					 retiroSpei.execAsync(getApplicationContext());
					progress = ProgressDialog.show(RetiroSpei.this, "Retirando saldo", "Espera un momento");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        }
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
       BDD dbh = new BDD(getApplicationContext(),"kupay",null,1);
       SQLiteDatabase db= dbh.getReadableDatabase();
       Cursor reg = db.query("kupay",new String[]{"usr"},null,null,null,null,null,"1");
       if(reg.moveToFirst()){
           usr=reg.getString(0);
          
       
       }
	 return usr;
   }
   
   private String MiImei(){
   	String imei = null;
       BDD dbh = new BDD(getApplicationContext(),"kupay",null,1);
       SQLiteDatabase db= dbh.getReadableDatabase();
       Cursor reg = db.query("kupay",new String[]{"imei"},null,null,null,null,null,"1");
       if(reg.moveToFirst()){
           imei=reg.getString(0);
          
       
       }
	 return imei;
   }

}
