package com.kupay;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

class ActualizarCC extends AsyncTask<Void, Integer, JSONObject>{
  	 
	final String ACTUALIZACION_CC_EXITOSA = "ACTUALIZACION_CC_EXITOSA";
	final String ACTUALIZACION_CC_FALLIDA = "ACTUALIZACION_CC_FALLIDA";
	final String CONEXION_FALLIDA = "CONEXION_FALLIDA";
	Activity context;
	
	

	
	public ActualizarCC(Activity actividad) {
		this.context = actividad;
	}

	@Override
     protected void onPreExecute() {
		//progress = ProgressDialog.show(getApplicationContext(), "Conectando a servidor", "Conectando...");
		
      }
     
      protected JSONObject doInBackground(Void... params) {
		
    	  JSONObject data = new JSONObject();
    	  JSONObject response = new JSONObject();
			try {

  			data.put("emisor", "00000001");
  			data.put("imei", "123456789012345");
  			data.put("pin", 1234);
  			
  			} catch (JSONException e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  			}
			Post post = new Post(5,data);
    	  
    	
    		  try {
				response = post.exec(context);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			//Log.v("app", "main resp:"+ response.toString());
      	

    	  return response;

      }
    @Override
      protected void onProgressUpdate (Integer... valores) {
    
      }
    
    @Override
      protected void onPostExecute(JSONObject response) {
    	try {
    	//	progress.dismiss();
			 String resultado = response.getString("RESULTADO");
			Log.v("app","RESult: "+ resultado.toString());
			JSONObject datos = response.getJSONObject("DATOS");
     		if (ACTUALIZACION_CC_EXITOSA.toString().equals(resultado) ){
     			
     			
 				Log.v("app","Datos: "+ datos.toString());
 				TextView cc = (TextView) context.findViewById(R.id.cantidad);
 				cc.setText("$"+Integer.toString(datos.getInt("SALDO")));
 				//AnimaSaldo actcc = new AnimaSaldo(context);
 				//actcc.equals(datos.getInt("SALDO"));
 				
     		}else if(ACTUALIZACION_CC_FALLIDA.toString().equals(resultado)){
     			int duracion=Toast.LENGTH_SHORT;
                Toast mensaje=Toast.makeText(context, "error en actualización", duracion);
                mensaje.show();
     		}else if(CONEXION_FALLIDA.toString().equals(resultado)){
     			int duracion=Toast.LENGTH_SHORT;
                Toast mensaje=Toast.makeText(context, "error en conexion", duracion);
                mensaje.show();
     		}else{
     			int duracion=Toast.LENGTH_SHORT;
                 Toast mensaje=Toast.makeText(context, "error desconosido", duracion);
                 mensaje.show();
     		}
       	} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
	
}
