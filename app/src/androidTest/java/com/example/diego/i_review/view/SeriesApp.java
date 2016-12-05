package com.example.diego.i_review.view;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.diego.i_review.Core.Serie;
import com.example.diego.i_review.Core.SqlIO;
import com.example.diego.i_review.Core.Temporada;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diego on 27/11/2016.
 */

public class SeriesApp extends Application {
    private SqlIO db;
    private List<Serie> series;
    private List<Temporada> temporadas;

    @Override
    public void onCreate(){
        this.series = new ArrayList<>();
        this.temporadas = new ArrayList<>();
        this.db = new SqlIO(this);
    }


    public SQLiteDatabase getDB()
    {
        return this.db.getWritableDatabase();
    }

    public List<Serie> getListaSeries(){
        this.leerBDSerie();
        return this.series;
    }

    public List<Temporada> getListaTemporadas(final int idSerie){
        this.leerBDTemporada(idSerie);
        return this.temporadas;
    }

    private void leerBDSerie(){
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

    private void leerBDTemporada(final int idSerie){
        SQLiteDatabase db = this.db.getReadableDatabase();
        this.temporadas.clear();

        Cursor cursor = db.rawQuery("SELECT * FROM temporada WHERE idSerie='"+ idSerie +"'",null);

        if ( cursor.moveToFirst() ) {
            do {
                Temporada temporada = new Temporada(cursor.getInt( 0 ), cursor.getString( 1 ),cursor.getInt(2));
                this.temporadas.add( temporada );
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
            this.leerBDSerie();
        }finally {
            db.endTransaction();
        }
        return;
    }


    public void eliminarSerie(int id){
        this.leerBDSerie();
        SQLiteDatabase db = this.getDB();

        try {
            db.beginTransaction();
            db.execSQL("DELETE FROM serie WHERE id=?", new Integer[]{id});
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
        this.leerBDSerie();
        return;
    }

    public void modificarSerie(int id, String texto){
        this.leerBDSerie();
        SQLiteDatabase db = this.getDB();

        try{
            db.beginTransaction();
            db.execSQL("UPDATE Serie SET nombre ='" + texto + "' WHERE id=?", new Integer[]{id});
            db.setTransactionSuccessful();

        }finally {
            db.endTransaction();
        }
        this.leerBDSerie();
        return;
    }

    public void insertarTemporada(final int cant,final int idSerie){
        SQLiteDatabase db = this.getDB();
        String nombre = "Temporada";

        for (int i = 0;i<cant;i++){
            try{
                db.beginTransaction();
                db.execSQL("INSERT INTO temporada(nombre,idSerie) VALUES(?,?)",new String[]{nombre,Integer.toString( idSerie )});
                //db.execSQL("INSERT INTO temporada(nombre,idSerie) VALUES(Prueba,1)");
                db.setTransactionSuccessful();
            }finally {
                db.endTransaction();
            }
        }
        this.leerBDTemporada(idSerie);
    }

    public  void eliminarTemporadas(){
        SQLiteDatabase db = this.getDB();
        try{
            db.beginTransaction();
            db.execSQL("DELETE FROM temporada");
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }

    }

}
