package com.kupay;

import android.content.Context;
import android.database.sqlite.*;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
/**
 *
 */
public class BDD extends SQLiteOpenHelper {
	
	
	
	public BDD(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		
	}


    public String crea="CREATE TABLE kupay (usr TEXT, imei TEXT)";

    
    
    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL(crea);
       db.execSQL("INSERT INTO kupay VALUES ('00000001','123456789012345')");
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS mitaxi");
        db.execSQL(crea);
        db.execSQL("INSERT INTO kupay VALUES ('00000001','123456789012345')");
    }
}