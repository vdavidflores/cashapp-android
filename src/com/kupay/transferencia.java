package com.kupay;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;   
import android.widget.Toast;

public class transferencia extends Fragment{
	Button enviar;
	TextView para;
	TextView cantidad;
	TextView cc;
	
	JSONObject datos;
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
	
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		
        	View view = inflater.inflate(R.layout.transferencia, container, false);
             enviar = (Button) view.findViewById(R.id.enviar);
             para = (TextView) view.findViewById(R.id.inputPara);
   		     cantidad = (TextView) view.findViewById(R.id.inputCantidad);
   		
   		   eventos();
        return view;
    }
	   

	

	private void eventos(){
		enviar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	
            	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        		builder.setIcon(R.drawable.ku72);
        		builder.setTitle("Transferencia");
        		builder.setMessage("Deseas transferir $"+cantidad.getText()+" a "+para.getText());
        		builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
        			
                    public void onClick(DialogInterface dialog, int id) {
                    transferir();
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
            	
            
            	
            	
            }});
            	
   }
	
	
	private void transferir(){
		TelephonyManager telephonyManager = (TelephonyManager) getActivity().getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
    	cc = (TextView) getActivity().findViewById(R.id.cantidad);
    	

    	
		Transaccon tarea = new Transaccon();
		tarea.execute();
	}
	
	private void transaccionFallida(String causaFalla){
		
		
		
		
		AlertDialog.Builder builder_ = new AlertDialog.Builder(getActivity());	
		builder_.setIcon(R.drawable.ku72);
		builder_.setTitle("Tranaccion no realizada");
		
		if (USUARIO_INVALIDO.toString().equals(causaFalla)){
			builder_.setMessage("EL usuario ingresado es invalido");
		}else if(FONDOS_INUFICIENTES.toString().equals(causaFalla)){
			builder_.setMessage("No dipones de saldo suficiente para realizar esta transacción");
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
			progress = ProgressDialog.show(getActivity(), "Transaccion en proceso", "procesando transacción...");
			
          }
         
          
		protected JSONObject doInBackground(Void... params) {
			JSONObject data = new JSONObject();
			try {
    			
    				data.put("receptor", para.getText());
    			
    			data.put("emisor", "00000001");
    			data.put("cantidad", cantidad.getText());
    			data.put("imei", "123456789012345");
    			data.put("pin", 1234);
    			
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
  				cc.setText("$"+Integer.toString(datos.getInt("SALDO_POST_TRASACCION")));
      		}else if(TRANSACCION_FALLIDA.toString().equals(resultado)){
      	       Log.v("app", "pst-3");
      			Log.v("app","Causa falla: "+datos.getString("CAUSA_FALLA").toString());
      			Log.v("app", "pst-4");
      			transaccionFallida(datos.getString("CAUSA_FALLA").toString());
      		}else{
      			int duracion=Toast.LENGTH_SHORT;
                  Toast mensaje=Toast.makeText(getActivity(), "error desconosido", duracion);
                  mensaje.show();
      		}
        	} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
      	
        	
        }

		
    }
	
}
		
		
       
	
	
	
	

	
	
	
	
	


	

