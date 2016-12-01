package com.example.diego.i_review.view;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
                Serie serie = new Serie(cursor.getInt( 0 ), cursor.getString( 1 ));
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
            db.setTransactionSuccessful();
            this.leerBD();
        }finally {
            db.endTransaction();
        }
        return;
    }

    /*public int getIdByNombre(String nombre){
        SQLiteDatabase db = this.getDB();
        int id = 0;
        try{
            Cursor cursor = db.rawQuery("SELECT id FROM serie WHERE nombre =?",new String[]{nombre});
            id = cursor.getInt(0);
        }catch(Exception e){
            Log.i("Error",e.toString());
        }
        return id;
    }*/

    public void eliminarSerie(int id){
        this.leerBD();
        SQLiteDatabase db = this.getDB();

        try {
            db.beginTransaction();
            db.execSQL("DELETE FROM serie WHERE id=?", new Integer[]{id});
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
        this.leerBD();
        return;
    }

    public void modificarSerie(int id, String texto){
        this.leerBD();
        SQLiteDatabase db = this.getDB();

        try{
            db.beginTransaction();
            db.execSQL("UPDATE Serie SET nombre ='" + texto + "' WHERE id=?", new Integer[]{id});
            db.setTransactionSuccessful();

        }finally {
            db.endTransaction();
        }
        this.leerBD();
        return;
    }

}
