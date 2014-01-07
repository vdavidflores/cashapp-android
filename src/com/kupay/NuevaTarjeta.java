package com.kupay;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;



import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class NuevaTarjeta extends Activity implements OnItemSelectedListener {

	Spinner spinner;
	Spinner paisSpiner;
	private ProgressDialog progress;
	EditText nombreTitular, numeroTarjeta, expMes, expAnio, cvv, direccionTitular, codigoPostal, estado;

	Button acepar, cancelar;
	
	 int pin;
	 String paises[];
	 String  marcas[] = {"Visa","MasterCard",};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nueva_tarjeta);
		
		paises = getResources().getStringArray(R.array.countries_array);
		 spinner = (Spinner) findViewById(R.id.spinner_marca);
		 paisSpiner = (Spinner) findViewById(R.id.spinner_pais);
		 nombreTitular = (EditText) findViewById(R.id.titular_nombre);
		 numeroTarjeta = (EditText) findViewById(R.id.tarjeta_numero);
		 expMes = (EditText) findViewById(R.id.mes);
		 expAnio = (EditText) findViewById(R.id.anio);
		 cvv = (EditText) findViewById(R.id.cvv);
		 direccionTitular = (EditText) findViewById(R.id.direccion);
		 codigoPostal = (EditText) findViewById(R.id.codiogo_postal);
		 estado = (EditText) findViewById(R.id.estado);
		
		 ArrayAdapter<String> LTRadapter = new ArrayAdapter<String>(this.getApplicationContext(), android.R.layout.simple_spinner_item, marcas);
		    LTRadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		    spinner.setAdapter(LTRadapter);
		    
		    ArrayAdapter<String> mpa = new ArrayAdapter<String>(this.getApplicationContext(), android.R.layout.simple_spinner_item, paises);
		    LTRadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		    paisSpiner.setAdapter(mpa);
		    cancelar = (Button) findViewById(R.id.cancelar_ntarjeta);
		 
		     cancelar.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
				setResult(Activity.RESULT_CANCELED);
				finish();
				
					
				}
			});
		     acepar = (Button) findViewById(R.id.aceptar_ntarjeta);
			 
		     acepar.setOnClickListener(new OnClickListener() {
				

				@Override
				public void onClick(View v) {
				
					pin();
					
				}
			});
		    
		
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
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
            if (Password.getEditableText().length() == 0){
            		 
            		 dialog.dismiss();
            	 }
            else{ 
                 pin = Integer.parseInt(Password.getEditableText().toString()) ;
            	Encriptamiento encript = new Encriptamiento();
            	encript.execute();
            	 }
            }
        });
		builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	dialog.dismiss();
            }
        });

		AlertDialog	dialog = builder.create();
    	dialog.show();
    
        	
    	
    }	
	

	private class  Encriptamiento extends AsyncTask<Void, Void, JSONObject> {
		@Override
        protected void onPreExecute() {
			progress = ProgressDialog.show(NuevaTarjeta.this, "Registrando tarjeta", "encriptando datos....");
			
         }

		@Override
		protected JSONObject doInBackground(Void... params) {
			
			JSONObject data = new JSONObject();
			try {
    			
				data.put("numero_tarjeta", numeroTarjeta.getText().toString());
				data.put("nombre_titular", nombreTitular.getText().toString());
				data.put("exp_mes", expMes.getText().toString());
				data.put("exp_anio", expAnio.getText().toString());
				data.put("cvv", cvv.getText().toString());
				data.put("direccion_titular", direccionTitular.getText().toString());
				data.put("codigo_postal", codigoPostal.getText().toString());
				data.put("estado", estado.getText().toString());
				data.put("marca", marcas[spinner.getSelectedItemPosition()]);
				data.put("pais", paises[paisSpiner.getSelectedItemPosition()]);
				
				
				data.put("usr", MiUsuario());
    			data.put("imei", MiIMEI());
    			data.put("pin", pin);
				
    			
    			} catch (JSONException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
			Post post = new Post(11,data);
        	  JSONObject response = null;
        	  try {
        		  response = post.exec(getApplicationContext());
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
          protected void onPostExecute(JSONObject response) {
			  progress.dismiss();
			  try {
				  String resultado = response.get("RESULTADO").toString();
				if (resultado.equals("EXITO")){
					  //ALMACENA
					JSONObject datos = response.getJSONObject("DATOS");
					 BDD dbh = new BDD(getApplicationContext(),"kupay",null,1);
				        SQLiteDatabase db= dbh.getWritableDatabase();
				        db.execSQL("INSERT INTO kupayTarjetas VALUES ('"+datos.getString("numero_tarjeta_cryp")+"'," +
				        		"'"+datos.getString("nombre_titular_cryp")+"'," +
				        				"'"+datos.getString("exp_mes_cryp")+"'," +
				        						"'"+datos.getString("exp_anio_cryp")+"," +
				        								"'"+datos.getString("cvv_cryp")+"'," +
				        										"'"+datos.getString("direccion_titular_cryp")+"'," +
				        												"'"+datos.getString("codigo_postal_cryp")+"'," +
				        														"'"+datos.getString("marca_cryp")+"'," +
				        																"'"+datos.getString("pais_cryp")+"')");
				        String query = "SELECT ROWID from kupayTarjetas order by ROWID DESC limit 1";
				        Cursor c = db.rawQuery(query,null);
				        Intent data = new Intent();
				        if (c != null && c.moveToFirst()) 
				         data.putExtra("tarjeta_id",Long.toString(c.getLong(0)));
			
						setResult(Activity.RESULT_OK,data);
						finish();
					
				  } else{ 
						
						AlertDialog.Builder builder_ = new AlertDialog.Builder(getApplicationContext());	
						builder_.setIcon(R.drawable.ku72);
						builder_.setTitle("No se registro la tarjeta");
						if (resultado.equals("FALLA")){
								builder_.setMessage(response.getJSONObject("DATOS").getString("MENSAJE").toString());
						} else
						if (resultado.equals("CONEXION_FALLIDA")){
							//Alert dialog conexion falida
							builder_.setMessage("Algun error en tu conexión, intenta nuevamente");
						} else 
						if (resultado.equals("NO_HAY_CONEXION")){
							//No hay conexion
							builder_.setMessage("Algun error en tu conexión, intenta nuevamente");
						}
						builder_.setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
				            public void onClick(DialogInterface dialog, int id) {
				            }
				        });
				    	AlertDialog dialog = builder_.create();
				    	dialog.show();
				 }
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  
		  }
		
	}
	
	 private String MiUsuario(){
     	String usr = null;
         BDD dbh = new BDD(getApplicationContext(),"kupay",null,1);
         SQLiteDatabase db= dbh.getReadableDatabase();
         Cursor reg = db.query("kupay",new String[]{"usr"},null,null,null,null,null,"1");
         if(reg.moveToFirst()){
             usr=reg.getString(0);
            
         
         }
         dbh.close();
 	 return usr;
     }
	 
	 private String MiIMEI(){
	     	String usr = null;
	         BDD dbh = new BDD(getApplicationContext(),"kupay",null,1);
	         SQLiteDatabase db= dbh.getReadableDatabase();
	         Cursor reg = db.query("kupay",new String[]{"imei"},null,null,null,null,null,"1");
	         if(reg.moveToFirst()){
	             usr=reg.getString(0);
	            
	         
	         }
	         dbh.close();
	 	 return usr;
	     }

}
