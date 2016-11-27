package com.example.diego.i_review.view;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.diego.i_review.Core.Serie;
import com.example.diego.i_review.Core.SqlIO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diego on 27/11/2016.
 */

public class SeriesApp extends Application {
    private SqlIO db;
    private List<Serie> series;

    @Override
    public void onCreate(){
        this.series = new ArrayList<>();
        this.db = new SqlIO(this);
    }


    public SQLiteDatabase getDB()
    {
        return this.db.getWritableDatabase();
    }

    public List<Serie> getListaSeries(){
        this.leerBD();
        return this.series;
    }

    private void leerBD(){
        SQLiteDatabase db = this.db.getReadableDatabase();
        this.series.clear();

        Cursor cursor = db.rawQuery("SELECT * FROM serie",null);

        if ( cursor.moveToFirst() ) {
            do {
                Serie serie = new Serie(/*cursor.getInt( 0 ),*/ cursor.getString( 1 )/*, cursor.getInt( 2 ) */);
               // serie.setId(cursor.getInt(1));
               // serie.setValoracion(cursor.getInt(1));
                this.series.add( serie );
            } while( cursor.moveToNext() );

            cursor.close();
        }

        return;
    }

    public void insertarSerie(String nombre){
        SQLiteDatabase db = this.getDB();
        try{
            db.beginTransaction();
            db.execSQL("INSERT INTO serie(nombre) VALUES(?)",new String[]{nombre});
            Serie serie = new Serie( nombre );
            this.series.add( serie );
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
        return;
    }

    public void eliminarSerie(int position){
        this.leerBD();
        SQLiteDatabase db = this.getDB();

        try {
            db.beginTransaction();
            String sql = "DELETE FROM serie WHERE id =' " + position + "'";
            db.execSQL(sql);
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
        return;
    }
}
