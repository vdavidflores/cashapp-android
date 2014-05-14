package com.kupay;

import android.util.Log;


	public class OperacionRow {
		 
		   public int icon;
		    public String title;
		    public String concepto;
		    public String idkey;
		    public OperacionRow(){
		        super();
		    }
		    
		    public OperacionRow (int icon, String title,String concepto, String idkey) {
		    	
		        super();
		        this.icon = icon;
		        this.title = title;
		        this.idkey = idkey;
		        this.concepto = concepto;
		        Log.v("list","1w");
		    }
		}

