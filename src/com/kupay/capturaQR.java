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
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
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
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class capturaQR extends DecoderActivity  {

	int pin;
	String qr;
	String datosExta = "";
	Boolean aDomicilio = false;
	private AlertDialog dialog;

		   private static final String TAG = "app";
		    private View viewfinderView;
		 
		    public void onCreate(Bundle icicle) {
		        super.onCreate(icicle);
		        Log.v(TAG, "onCreate()");
		    }
		    

		    public void onDestroy() {
		        super.onDestroy();
		        Log.v(TAG, "onDestroy()");
		    }

		    @Override
			public void onResume() {  
		       
		         super.onResume();
		    }

		    @Override
			public void onPause() {
		    	Log.v("app", "Se va a detener la camara por pausa");
		    	stopCamera();
		    	Log.v("app", "Se detubo la camara por pausa");
		    	 super.onPause();
		    }


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
		        stopCamera();
		        Log.v("app","algo se encontro");
		        CharSequence textoDetectado = resultHandler.getDisplayContents();
		        Log.v("app", textoDetectado.toString());
				
	    			
	    			//JSONObject data = new JSONObject();
		        if(textoDetectado.toString().contains("=")){
		         qr = textoDetectado.toString().substring(textoDetectado.toString().indexOf("=")+1);
		        }else{
	    		 qr = textoDetectado.toString();
		        }
	    			
				ChechQR checkQR = new ChechQR();
				checkQR.execute();
			//	onResume();
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
		      		if (resultado.equals("EXITO")){
		      			JSONObject datos = response.getJSONObject("DATOS");
		      			int codigodeestatus = Integer.parseInt(datos.getString("NUMERO_ESTATUS"));
		      			switch (codigodeestatus) {
						case 0:
							operacionActiva(datos);
							break;
						case 1:
							operacionInactiva();
							break;
						case 2:
							operacionInactiva();
							break;
						case 3:
							operacionActiva(datos);
							break;
						default:
							operacionInactiva();
							break;
						}
		      		}else{
		      			int duracion=Toast.LENGTH_LONG;
		                  Toast mensaje=Toast.makeText(getActivity(), "Operacion no encontrada", duracion);
		                  mensaje.show();
		                  restartCam();
		      		}
		        	} catch (JSONException e) {
						// TODO Auto-generated catch block
		        		restartCam();
						e.printStackTrace();
					}
		        }
		        private void operacionActiva(JSONObject datos) {
		        	
		        	AlertDialog.Builder builder_ = new AlertDialog.Builder(getActivity());
		        	final EditText emailOtel = new EditText(getActivity());
					final EditText direccion = new EditText(getActivity());
					
					try {
						if (CARGO_A_QUIEN_DETECTA.toString().equals(datos.getString("TIPO").toString())){
							builder_.setTitle("Cargo");
							builder_.setMessage("Monto a pagar: $"+datos.getString("MONTO")+" \n"+
							"Concepto: "+datos.getString("CONCEPTO"));
							aDomicilio = false;
						}else if (ABONO_A_QUIEN_DETECTA.toString().equals(datos.getString("TIPO").toString())){
							builder_.setTitle("Abono");
							builder_.setMessage("Monto a ingresar: "+datos.getString("MONTO")+" \n"+
							"Concepto: "+datos.getString("CONCEPTO"));
							aDomicilio = false;
						}else if (datos.getString("TIPO").equals("VENTA_A_DOMICILIO")){
							aDomicilio = true;
							builder_.setTitle("Venta a domicilio");
							builder_.setMessage("Monto a pagar: $"+datos.getString("MONTO")+" \n"+
							"Concepto: "+datos.getString("CONCEPTO")+"\n\nAviso:\nEs importante que proporciones un email de contacto o teléfono asi como tu dirección y datos suficientes para que el vendedor pueda hacerte llegar tu producto/servicio.");
							
							LinearLayout lila1= new LinearLayout(getActivity());
							lila1.setOrientation(LinearLayout.VERTICAL);
							lila1.setGravity(Gravity.CENTER);
							lila1.addView(emailOtel);
							lila1.addView(direccion);
				    		emailOtel.setHint("Teléfono o email de contacto");
				    		
				    		direccion.setHint("Dirección y datos para la entrega");
				    		direccion.setWidth(200);
				    		emailOtel.setWidth(200);
				    		builder_.setView(lila1);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					builder_.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int which) {
							if (!aDomicilio ){
								pin();
							}else{
								if (!emailOtel.getText().toString().equals("")&&!direccion.getText().toString().equals("")){
									pin();
									datosExta = "Contacto: "+emailOtel.getText().toString()+" Direccion:"+direccion.getText().toString();
								}else{
									restartCam();
									Toast.makeText(getActivity(), "Faltan datos de contacto y entrega", Toast.LENGTH_LONG).show();
								}
							}
						}
					});
					builder_.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int which) {
							dialog.dismiss();
							qr = "";
							restartCam();
						}
					});
					builder_.setOnKeyListener(new OnKeyListener() {

		                @Override
		                public boolean onKey(DialogInterface arg0, int keyCode,
		                        KeyEvent event) {
		                    // TODO Auto-generated method stub
		                    if (keyCode == KeyEvent.KEYCODE_BACK) {
		                        restartCam();
		                        dialog.dismiss();
		                    }
		                    return true;
		                }
		            });
					
	  		    	dialog = builder_.create();
	  		    	dialog.show();
	  			
				}
		        
		        
		        private void operacionInactiva(){
		        	AlertDialog.Builder builder_ = new AlertDialog.Builder(getActivity());
		      			
		      			builder_.setTitle("Operacion no disponible");
						builder_.setMessage("El codigo detectado no esta disponible, probablemente ya fue usado o se encuentra desactivado.");
						
						builder_.setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
			                public void onClick(DialogInterface dialog, int id) {
			                     dialog.dismiss();
			                     qr = "";
			                     restartCam();
			                }
			            });
						
						builder_.setOnKeyListener(new OnKeyListener() {

			                @Override
			                public boolean onKey(DialogInterface arg0, int keyCode,
			                        KeyEvent event) {
			                    // TODO Auto-generated method stub
			                    if (keyCode == KeyEvent.KEYCODE_BACK) {
			                        restartCam();
			                        dialog.dismiss();
			                    }
			                    return true;
			                }
			            });
						
						dialog = builder_.create();
		  		    	dialog.show();
		      		
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
		                	restartCam();
		                }
		            });
		 
		    		
		    	/*	
		    		builder.setOnKeyListener(new OnKeyListener() {

		                @Override
		                public boolean onKey(DialogInterface arg0, int keyCode,
		                        KeyEvent event) {
		                    // TODO Auto-generated method stub
		                    if (keyCode == KeyEvent.KEYCODE_BACK) {
		                        restartCam();
		                        dialog.dismiss();
		                    }
		                    return true;
		                }
		            });*/

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
					progress = ProgressDialog.show(getActivity(), "Transacción en proceso", "procesando transacción...");
					
		          }
		         
		          
				protected JSONObject doInBackground(Void... params) {
					JSONObject data = new JSONObject();
					try {
		    			data.put("usr", MiUsuario());
		    			data.put("imei", MiImei());
		    			data.put("pin", pin);
		    			data.put("qr", qr);
		    			data.put("extra", datosExta);
		    			
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
		      		if (resultado.equals("EXITO") ){
		      	       Log.v("app", "pst-2");
		  				Log.v("app","Datos: "+ datos.toString());
		  				//cc = (TextView) getActivity().findViewById(R.id.cantidad);
		  				//cc.setText("$"+Integer.toString(datos.getInt("SALDO_POST_TRASACCION")));
		  				
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
				  				AnimaSaldo actCC = new AnimaSaldo(getActivity());
				  				try {
									actCC.execute(datos.getDouble("SALDO_POST_TRASACCION"));
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
				  				restartCam();
				  				
							}
						});
		  				
		  				builder.setOnKeyListener(new OnKeyListener() {

			                @Override
			                public boolean onKey(DialogInterface arg0, int keyCode,
			                        KeyEvent event) {
			                    // TODO Auto-generated method stub
			                    if (keyCode == KeyEvent.KEYCODE_BACK) {
			                        restartCam();
			                        dialog.dismiss();
			                    }
			                    return true;
			                }
			            });
		  				
		  				AlertDialog dilaogoAceptar = builder.create();
		  				dilaogoAceptar.show();
		  				

		  				
		      		}else if(resultado.equals("FALLA")){
		      	       Log.v("app", "pst-3");
		      			
		      			Log.v("app", "pst-4");
		      			if (datos.has("CAUSA_FALLA")){
		      				transaccionFallida(datos);
		      			} else if (datos.has("CAUSA")) {
		      				transaccionFallida(datos);
						}  else {
							 Toast mensaje=Toast.makeText(getActivity(), "error desconosido", Toast.LENGTH_LONG);
			                 mensaje.show();
			                 restartCam();
						}
		      		}else{
		      			int duracion=Toast.LENGTH_SHORT;
		                  Toast mensaje=Toast.makeText(getActivity(), "error desconosido", duracion);
		                  mensaje.show();
		                  restartCam();
		      		}
		        	} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
		        
		    	private void transaccionFallida(JSONObject datos){
		    		
		    		String causaFalla = null;
					try {
						causaFalla = datos.getString("CAUSA_FALLA").toString();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    		AlertDialog.Builder builder_ = new AlertDialog.Builder(getActivity());	
		    		builder_.setIcon(R.drawable.ku72);
		    		builder_.setTitle("Transacción no realizada");
		    		
		    		if (USUARIO_INVALIDO.toString().equals(causaFalla)){
		    			builder_.setMessage("EL usuario ingresado es invalido");
		    		}else if(FONDOS_INUFICIENTES.toString().equals(causaFalla)){
		    			builder_.setMessage("No dipones de saldo suficiente para realizar esta transacción");
		    		}else{
		    		  if (datos.has("MENSAJE")) {
		    			  try {
							builder_.setMessage(datos.getString("MENSAJE"));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		    		  }else {
		    			builder_.setMessage("Error desconocido, intenta nuevamente mas tarde o contactanos en contacto@codigoku.com");
		    		  }
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
		        	        restartCam();
		        	    }
		        	});
		        	dialog.setOnKeyListener(new OnKeyListener() {

		                @Override
		                public boolean onKey(DialogInterface arg0, int keyCode,
		                        KeyEvent event) {
		                    // TODO Auto-generated method stub
		                    if (keyCode == KeyEvent.KEYCODE_BACK) {
		                        restartCam();
		                        dialog.dismiss();
		                    }
		                    return true;
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
