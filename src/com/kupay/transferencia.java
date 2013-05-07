package com.kupay;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
	String resultado = null;
	JSONObject datos;
	protected AlertDialog dialog;

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
    	JSONObject data = new JSONObject();
    	
    	
    	try {
			data.put("receptor", para.getText());
			data.put("emisor", "00000001");
			data.put("cantidad ", cantidad.getText());
			data.put("imei", telephonyManager.getDeviceId());
			data.put("pin", 1234);
			
		Post post = new Post(1,data);
    	JSONObject response = post.exec();
    	/*Log.v("app","Respuesta de post: "+response.toString());
    	resultado = response.getString("RESULTADO");
    	Log.v("app", "Resultado en rapuesta: "+resultado);
    		if (resultado == "TRANSACCION_EXITOSA"){
        		
				datos = response.getJSONObject("DATOS");
				Log.v("app","Datos: "+ datos.toString());
				cc.setText("$"+Integer.toString(datos.getInt("SALDO")));
    		}else if(resultado == "TRANSACCION_FALLIDA"){
    			transaccionFallida();
    		}else{
    			int duracion=Toast.LENGTH_SHORT;
                Toast mensaje=Toast.makeText(getActivity(), "error desconosido", duracion);
                mensaje.show();
    		}
    	*/
    	} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void transaccionFallida(){
		AlertDialog.Builder builder_ = new AlertDialog.Builder(getActivity());	
		builder_.setIcon(R.drawable.ku);
		builder_.setTitle("Error en transaccion");
		builder_.setMessage("Lo sentimos tu trasaccion ha fallado");
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
	
}
		
		
       
	
	
	
	

	
	
	
	
	


	

