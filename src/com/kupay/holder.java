package com.kupay;

import android.content.Context;
import android.util.Log;

public class holder {

	public String letra5y6 = "me";
	public int cuarto;
	public int segundo;
	Context Con_;
	public holder(Context con) {
		Con_ = con;
	}
	
	public void setSegundo(int tercero) {
		segundo = tercero - 24;
	}
	
	public String gettextoMenosL8(String l1, String l4, String l7) {
		return l1+Con_.getString(R.string.letra2y3)+l4+letra5y6+l7;
	}
	
	
	
}
