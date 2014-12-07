package com.kupay;

import java.lang.reflect.Array;

import android.R.integer;
import android.util.Log;

public class hider {
	private String names[] = {"G","i","d","s"};
	private int tres;
	public int quintoMenosPrimero;
	private int clave3 = 8;
	
	public hider(int quintoEntreDos) {
		quintoMenosPrimero = (quintoEntreDos*(clave3/4))-6;
	}
	
	public int tercero(int  primero) {
	
		return primero*clave3*primero;
		
	}

	
	public int sexto(int primero) {
		return (quintoMenosPrimero+primero)*this.tercero(primero);
	}
	
	
	public String letra(int pos) {
		Log.v("app", "y-7");
		return names[pos];
	}
	
}
