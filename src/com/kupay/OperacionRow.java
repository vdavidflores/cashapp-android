package com.kupay;

import android.util.Log;


	public class OperacionRow {
		 
		   public int icon;
		    public String title;
		    public String concepto;
		    public OperacionRow(){
		        super();
		    }
		    
		    public OperacionRow (int icon, String title,String concepto) {
		    	
		        super();
		        this.icon = icon;
		        this.title = title;
		        this.concepto = concepto;
		        Log.v("list","1w");
		    }
		}

