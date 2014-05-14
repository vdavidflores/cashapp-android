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
       db.execSQL("INSERT INTO kupay VALUES ('nill','nill')");
       db.execSQL("CREATE TABLE kupayTarjetas (id INTEGER PRIMARY KEY AUTOINCREMENT,nombre TEXT,numero_tarjeta_cryp TEXT, nombre_titular_cryp TEXT,  exp_mes_cryp TEXT,  exp_anio_cryp TEXT,  cvv_cryp TEXT)");
     //  db.execSQL("INSERT INTO kupayTarjetas VALUES ('la buena','8547f3458575','yopo','12','2017','123')");
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS kupay");
        db.execSQL("DROP TABLE IF EXISTS kupayTarjetas");
        db.execSQL("CREATE TABLE kupayTarjetas (id INTEGER PRIMARY KEY AUTOINCREMENT,nombre TEXT,numero_tarjeta_cryp TEXT, nombre_titular_cryp TEXT,  exp_mes_cryp TEXT,  exp_anio_cryp TEXT,  cvv_cryp TEXT)");
        db.execSQL(crea);
        //db.execSQL("INSERT INTO kupayTarjetas VALUES ('la buena','8547f3458575','yopo','12','2017','123')");
        db.execSQL("INSERT INTO kupay VALUES ('nill','nill')");
    }
}