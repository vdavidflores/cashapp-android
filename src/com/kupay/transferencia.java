package com.kupay;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;   
import android.widget.Toast;

public class transferencia extends Fragment{
	Button enviar;
	AutoCompleteTextView para;
	TextView cantidad;
	TextView cc;
	EditText concepto;
	int pin ;	JSONObject datos;
	Post post;
	protected AlertDialog dialog;
	private ProgressDialog progress;
	
	// RESULTADOS
	final String TRANSACCION_EXITOSA = "TRANSACCION_EXITOSA";
	final String TRANSACCION_FALLIDA = "TRANSACCION_FALLIDA";
	//final String CAUSA_FALLA = "CAUSA_FALLA";
	
	//CAUSAS DE FALLA
	final String FONDOS_INUFICIENTES  = "FONDOS_INUFICIENTES";
	final String USUARIO_INVALIDO = "USUARIO_INVALIDO";
	
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$", Pattern.CASE_INSENSITIVE);

	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		
		
        	View view = inflater.inflate(R.layout.transferencia, container, false);
             enviar = (Button) view.findViewById(R.id.enviar);
             para = (AutoCompleteTextView) view.findViewById(R.id.inputPara);
           
         
             
             ArrayList<String> emailAddressCollection = new ArrayList<String>();

             ContentResolver cr = getActivity().getContentResolver();

             Cursor emailCur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, null, null, null);

             while (emailCur.moveToNext())
             {
                 String email = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                         emailAddressCollection.add(email);
             }
             emailCur.close();

             String[] emailAddresses = new String[emailAddressCollection.size()];
             emailAddressCollection.toArray(emailAddresses);

             ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                          R.layout.simple_dropdown_item_1line_custom, emailAddresses);
             
             
          
             para.setAdapter(adapter);


   		     cantidad = (TextView) view.findViewById(R.id.inputCantidad);
   		  concepto = (EditText) view.findViewById(R.id.concept);
   
   		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
   		     
   		   eventos();
        return view;
    }
	   

	

	private void eventos(){
		enviar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	
            	 String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
            	 
            	 if (para.getText().toString().matches(EMAIL_REGEX)){
            		 if(!cantidad.getText().toString().equals("")){
            			 
            			 AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                 		builder.setIcon(R.drawable.ku72);
                 		builder.setTitle("Transferencia");
                 		builder.setMessage("Deseas transferir $"+cantidad.getText()+" a;\n"+para.getText());
                 		builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                 			
                             public void onClick(DialogInterface dialog, int id) {
                             	pin();
             				}
                         });
                 		builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                             public void onClick(DialogInterface dialog, int id) {
                             	enviar.setText("");
                             	para.setText("");
                             }
                         });

                 		dialog = builder.create();
                     	dialog.show();
            			 
            			 
            		 }else {
						Toast.makeText(getActivity(), "Cantad vacia", Toast.LENGTH_LONG).show();
						cantidad.requestFocus();
					}
            		 
            		 
            		 
            	 }else {
					Toast.makeText(getActivity(), "email inválido", Toast.LENGTH_LONG).show();
					para.requestFocus();
				}
            	
            	
            	
            	
            
            	
            	
            }});
            	
   }
	
	
	private void transferir(){
		TelephonyManager telephonyManager = (TelephonyManager) getActivity().getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
    	cc = (TextView) getActivity().findViewById(R.id.cantidad);
    	

    	
		Transaccon tarea = new Transaccon();
		tarea.execute();
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
            	 transferir();
            	 }
            }
        });
		builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	cantidad.setText("");
            	para.setText("");
            }
        });

		dialog = builder.create();
    	dialog.show();
    
        	
    	
    }	




	
	
	
	
	
	
	
	
	
	private void transaccionFallida(String causaFalla, String mensaje){
		
		
		
		
		AlertDialog.Builder builder_ = new AlertDialog.Builder(getActivity());	
		builder_.setIcon(R.drawable.ku72);
		builder_.setTitle("Transacción no realizada");
		
		if (USUARIO_INVALIDO.toString().equals(causaFalla)){
			builder_.setMessage("El pin es incorrecto, intenta nuevamente");
		}else if(FONDOS_INUFICIENTES.toString().equals(causaFalla)){
			builder_.setMessage("No dipones de saldo suficiente para realizar esta transacción");
		}else if(causaFalla.equals("FALLA_MENSAJE")){
			builder_.setMessage(mensaje);
		}else if (causaFalla.equals("FALLA")){
			builder_.setMessage(mensaje);
		}else{
			builder_.setMessage("Error desoconosido, intenta nuevamente mas tarde o contacta a tu acesor Ku-pay" +
					" al 01800-222-359-9661");
		}
		
    	builder_.setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
    	dialog = builder_.create();
    	dialog.show();
    	dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
    	    @Override
    	    public void onDismiss(DialogInterface dialog) {
    	        dialog.dismiss();
    	    }
    	});
	}
	
	
	
	
    private class Transaccon extends AsyncTask<Void, Integer, JSONObject>{
   	 
     

		@Override
         protected void onPreExecute() {
			progress = ProgressDialog.show(getActivity(), "Transacción en proceso", "procesando transacción...");
			
          }
         
          
		protected JSONObject doInBackground(Void... params) {
			JSONObject data = new JSONObject();
			try {
    			
    				data.put("receptor", para.getText());
    			
    			data.put("emisor", MiUsuario());
    			data.put("cantidad", cantidad.getText());
    			data.put("concepto", concepto.getText().toString());
    			data.put("imei", MiImei());
    			data.put("pin", pin);
    			
    			} catch (JSONException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    		post = new Post(1,data);
        	  JSONObject response = null;
        	  try {
        		  response = post.exec(getActivity().getApplicationContext());
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				Log.v("app", "ask1");
				e.printStackTrace();
			} catch (ParseException e) {
				Log.v("app", "ask2");
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				Log.v("app", "ask4");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
          	
          	Log.v("post",response.toString());
  
        	  return response;
	
          }
        @Override
          protected void onProgressUpdate (Integer... valores) {
        
          }
        
        @Override
          protected void onPostExecute(JSONObject response) {
        	progress.dismiss();
       Log.v("app", "pst-1");
      
        	try {
			 String resultado = response.getString("RESULTADO");
			 datos = response.getJSONObject("DATOS");
      		if (TRANSACCION_EXITOSA.toString().equals(resultado) ){
      	       Log.v("app", "pst-2");
  				Log.v("app","Datos: "+ datos.toString());
  				
  				mensajeExito();
  				//cc.setText("$"+Integer.toString(datos.getInt("SALDO_POST_TRASACCION")));
      		}else if(TRANSACCION_FALLIDA.toString().equals(resultado)){
      	       Log.v("app", "pst-3");
      			Log.v("app","Causa falla: "+datos.getString("CAUSA_FALLA").toString());
      			Log.v("app", "pst-4");
      			
      			if (datos.getString("CAUSA_FALLA").equals("FALLA_MENSAJE")){
      				transaccionFallida(datos.getString("CAUSA_FALLA").toString(),datos.getString("MENSAJE"));
      			}else{
      			transaccionFallida(datos.getString("CAUSA_FALLA").toString(),null);
      			}
      		}else if (resultado.equals("FALLA")){
      			transaccionFallida("FALLA",datos.getString("MENSAJE"));
      		}
        	} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
      	
        	
        }
        
        private void mensajeExito() {
        	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("Transacción exitosa");
				
				TextView publicidad = new TextView(getActivity());
				publicidad.setText("Publicidad\n"+
				"Espacio reservado para publicidad dirigida");
				publicidad.setTextSize(14);
				publicidad.setGravity(Gravity.CENTER);
				
				builder.setView(publicidad);
				
				builder.setPositiveButton("Aceptar", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
	  				dialog.dismiss();
	  			
				}
			});
				
				builder.setOnKeyListener(new OnKeyListener() {

                @Override
                public boolean onKey(DialogInterface arg0, int keyCode,
                        KeyEvent event) {
                    // TODO Auto-generated method stub
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                      
                        dialog.dismiss();
                    }
                    return true;
                }
            });
				
				AlertDialog dilaogoAceptar = builder.create();
				dilaogoAceptar.show();
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
	
}
		
		
       
	
	
	
	

	
	
	
	
	


	

