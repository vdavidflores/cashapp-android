package com.kupay;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.zxing.Result;
import com.kupay.decoder.DecoderActivity;
import com.kupay.decoder.result.ResultHandler;
import com.kupay.decoder.result.ResultHandlerFactory;



import android.os.AsyncTask;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class capturaQR extends DecoderActivity{

	int pin;
	String qr;
	private AlertDialog dialog;

		   private static final String TAG = "app";
		    private View viewfinderView;

		    public void onCreate(Bundle icicle) {
		        super.onCreate(icicle);
		        Log.v(TAG, "onCreate()");
		    }
		    @Override
			public View onCreateView(LayoutInflater inflater, ViewGroup container,
					Bundle savedInstanceState) {
				View c = View.inflate(getActivity().getApplicationContext(), R.layout.capture,null);
				return c;
		    }

		    public void onDestroy() {
		        super.onDestroy();
		        Log.v(TAG, "onDestroy()");
		    }

		    @Override
			public void onResume() {
		    	 
		    	
		        super.onResume();
		         Log.v(TAG, "RESUME  2");
		        
		        
		      
		    }

////		    @Override
//			public void onPause() {
//		        super.onPause();
//		        Log.v(TAG, "onPause() CQR");
//		    }

		   /* @Override
		    public boolean onKeyDown(int keyCode, KeyEvent event) {
		        if (keyCode == KeyEvent.KEYCODE_BACK) {
		            if (inScanMode)
		                finish();
		            else
		                onResume();
		            return true;
		        }
		        return super.onKeyDown(keyCode, event);
		    }*/

		    @Override
		    public void handleDecode(Result rawResult, Bitmap barcode) {
		        drawResultPoints(barcode, rawResult);

		        ResultHandler resultHandler = ResultHandlerFactory.makeResultHandler(getActivity(), rawResult);
		        handleDecodeInternally(rawResult, resultHandler, barcode);
		    }

		    public void showScanner() {
		        viewfinderView.setVisibility(View.VISIBLE);
		    }

		    

		    // Put up our own UI for how to handle the decodBarcodeFormated contents.
		    private void handleDecodeInternally(Result rawResult, ResultHandler resultHandler, Bitmap barcode) {
		        onPause();
		        CharSequence qrDetectado = resultHandler.getDisplayContents();
		        Log.v("app", qrDetectado.toString());
				
	    			
	    			//JSONObject data = new JSONObject();
	    			
	    			qr = qrDetectado.toString();
	    			
	    			
				ChechQR checkQR = new ChechQR();
				checkQR.execute();
		    }
		    
//////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////CHECAR QR///////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////
		   
		    
		    
		    private class ChechQR extends AsyncTask<Void, Integer, JSONObject> {
		    	
		    	String ABONO_A_QUIEN_DETECTA = "ABONO_A_QUIEN_DETECTA";
		    	String CARGO_A_QUIEN_DETECTA = "CARGO_A_QUIEN_DETECTA";
		        
		    	JSONObject datos;
				private Post post;
				ProgressDialog progress;
				String OPERACION_DISPONIBLE = "OPERACION_DISPONIBLE";
				String OPERACION_NO_DISPONIBLE = "OPERACION_NO_DISPONIBLE";

				@Override
		         protected void onPreExecute() {
					 progress = ProgressDialog.show(getActivity(), "Busqueda de QR", "Busqueda en proceso...");
					
		          }
		         
		          
				protected JSONObject doInBackground(Void... params) {
					JSONObject data = new JSONObject();
					try { 
						data.put("qr", qr);
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		    		post = new Post(4,data);
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
		        	
		        	AlertDialog.Builder builder_ = new AlertDialog.Builder(getActivity());	
		    		builder_.setIcon(R.drawable.ku72);
		    		
		        	progress.dismiss();
		        	try {
					 String resultado = response.getString("RESULTADO");
		      		if (OPERACION_DISPONIBLE.toString().equals(resultado) ){

		  				datos = response.getJSONObject("DATOS");

		  				//switch (datos.getString("TIPO")) 
		  				
						if (CARGO_A_QUIEN_DETECTA.toString().equals(datos.getString("TIPO").toString())){
							builder_.setTitle("Cargo");
							builder_.setMessage("Monto a pagar: $"+datos.getString("MONTO")+" \n"+
							"Concepto: "+datos.getString("CONCEPTO"));
						}else if (ABONO_A_QUIEN_DETECTA.toString().equals(datos.getString("TIPO").toString())){
							builder_.setTitle("Abono");
							builder_.setMessage("Monto a ingresar: "+datos.getString("MONTO")+" \n"+
							"Concepto: "+datos.getString("CONCEPTO"));
						}
						builder_.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								
								pin();
								
								
							}
						});
						builder_.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								qr = "";
							}
						});
						
		  		    	dialog = builder_.create();
		  		    	dialog.show();
		  				
			    		
		  				Log.v("app","Datos: "+ datos.toString());
		      		}else if(OPERACION_NO_DISPONIBLE.toString().equals(resultado)){
		      			
		      			builder_.setTitle("Operacion no disponible");
						builder_.setMessage("El codigo detectado no esta disponible, probablemente ya fue usado o se encuentra desactivado.");
						
						builder_.setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
			                public void onClick(DialogInterface dialog, int id) {
			                     dialog.dismiss();
			                     qr = "";
			                }
			            });
						dialog = builder_.create();
		  		    	dialog.show();
		      		}else{
		      			int duracion=Toast.LENGTH_LONG;
		                  Toast mensaje=Toast.makeText(getActivity(), "Operacion no encontrada", duracion);
		                  mensaje.show();
		      		}
		        	} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		      	
		        	onResume();
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
		                		 ejecutarQR ejqr = new ejecutarQR();
									ejqr.execute();
		                	 }
		               	// Toast.makeText(getActivity(),pin,Toast.LENGTH_LONG).show(); 
		                	
		    			}
		            });
		    		builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dialog, int id) {
		                	dialog.dismiss();
		                }
		            });

		    		dialog = builder.create();
		        	dialog.show();
		        
		            	
		        	
		        }	
		    }
		    
		    
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		    private class ejecutarQR extends AsyncTask<Void, Integer, JSONObject>{
		    	ProgressDialog progress;
		    	JSONObject datos;
		    	TextView cc;
		    	// RESULTADOS
		    	final String TRANSACCION_EXITOSA = "TRANSACCION_EXITOSA";
		    	final String TRANSACCION_FALLIDA = "TRANSACCION_FALLIDA";
		    	//final String CAUSA_FALLA = "CAUSA_FALLA";
		    	
		    	//CAUSAS DE FALLA
		    	final String FONDOS_INUFICIENTES  = "FONDOS_INUFICIENTES";
		    	final String USUARIO_INVALIDO = "USUARIO_INVALIDO";

				@Override
		         protected void onPreExecute() {
					progress = ProgressDialog.show(getActivity(), "Transaccion en proceso", "procesando transacción...");
					
		          }
		         
		          
				protected JSONObject doInBackground(Void... params) {
					JSONObject data = new JSONObject();
					try {
		    			data.put("usr", MiUsuario());
		    			data.put("imei", "123456789012345");
		    			data.put("pin", pin);
		    			data.put("qr", qr);
		    			
		    			} catch (JSONException e) {
		    				// TODO Auto-generated catch block
		    				e.printStackTrace();
		    			}
					Post post = new Post(6,data);
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
		  				//cc = (TextView) getActivity().findViewById(R.id.cantidad);
		  				//cc.setText("$"+Integer.toString(datos.getInt("SALDO_POST_TRASACCION")));
		  				
		  				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		  				builder.setTitle("Transacción exitosa");
		  				
		  				TextView publicidad = new TextView(getActivity());
		  				publicidad.setText("Publicidad KUPAY \n"+
		  				"Espacio reservado para publicidad dirigida");
		  				publicidad.setTextSize(14);
		  				publicidad.setGravity(Gravity.CENTER);
		  				
		  				builder.setView(publicidad);
		  				
		  				builder.setPositiveButton("Aceptar", new OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
				  				AnimaSaldo actCC = new AnimaSaldo(getActivity());
				  				try {
									actCC.execute(datos.getInt("SALDO_POST_TRASACCION"));
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
				  				
							}
						});
		  				
		  				AlertDialog dilaogoAceptar = builder.create();
		  				dilaogoAceptar.show();
		  				

		  				
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
		    	  

				
		    }
		    
		    
		}
