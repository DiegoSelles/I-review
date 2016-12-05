package com.example.diego.i_review.Core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Diego on 27/11/2016.
 */

public class SqlIO extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ListaSeries";
    private static final int DATABASE_VERSION = 6;

    public SqlIO(Context context)
    {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.beginTransaction();
        try{
            db.execSQL( "CREATE TABLE IF NOT EXISTS serie( "
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + "nombre string(255), "
                    + "valoracion int)");
            db.execSQL(  "CREATE TABLE IF NOT EXISTS temporada( "
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    + "nombre string(255), "
                    + "idSerie INTEGER,"
                    + "FOREIGN KEY(idSerie) REFERENCES serie(id))");

            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }

    public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion){
        Log.i(  "I-review.SqlIO",
                "Actualizando BBDD de version " + oldVersion + " a la " + newVersion );

        try {
            db.beginTransaction();
            db.execSQL( "DROP TABLE IF EXISTS serie");
            db.execSQL( "DROP TABLE IF EXISTS temporada");
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        this.onCreate( db );
    }
}
