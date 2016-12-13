package com.example.diego.i_review.view;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.diego.i_review.Core.Capitulo;
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
    private List<Capitulo> capitulos;

    @Override
    public void onCreate(){
        this.series = new ArrayList<>();
        this.temporadas = new ArrayList<>();
        this.capitulos = new ArrayList<>();
        this.db = new SqlIO(this);
    }


    public SQLiteDatabase getDB()
    {
        return this.db.getWritableDatabase();
    }

    //Listado de series leyendo la bd y devolviendo objetos de tipo serie.
    public List<Serie> getListaSeries(){
        this.leerBDSerie();
        return this.series;
    }

    //Listado de temporadas según la serie leyendo la bd y devolviendo objetos de tipo temporadas.
    public List<Temporada> getListaTemporadas(final int idSerie){
        this.leerBDTemporada(idSerie);
        return this.temporadas;
    }

    //Listado de capítulos según la temporada leyendo la bd y devolviendo objetos de tipo capítulos.
    public List<Capitulo> getListaCapitulos(final int idTemporada){
        this.leerBDCapitulos(idTemporada);
        return this.capitulos;
    }

    //Lectura de la bd para la tabla series mediante un cursor.
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

    //Lectura de la bd para la tabla temporadas mediante un cursor teniendo en cuenta el idSerie.
    private void leerBDTemporada(final int idSerie){
        SQLiteDatabase db = this.db.getReadableDatabase();
        this.temporadas.clear();

        Cursor cursor = db.rawQuery("SELECT * FROM temporada WHERE idSerie='"+ idSerie +"'",null);

        if ( cursor.moveToFirst() ) {
            do {
                Temporada temporada = new Temporada(cursor.getInt( 0 ), cursor.getString( 1 ),cursor.getInt( 2 ),cursor.getInt( 3 ));
                this.temporadas.add( temporada );
            } while( cursor.moveToNext() );

            cursor.close();
        }
        return;
    }

    //Lectura de la bd para la tabla capítulos mediante un cursor teniendo en cuenta el idTemporada.
    private void leerBDCapitulos(final int idTemporada){
        SQLiteDatabase db = this.db.getReadableDatabase();
        this.capitulos.clear();

        Cursor cursor = db.rawQuery("SELECT * FROM capitulo WHERE idTemporada='" + idTemporada + "'",null);

        if ( cursor.moveToFirst() ) {
            do {
                Capitulo capitulo = new Capitulo(cursor.getInt( 0 ), cursor.getString( 1 ),cursor.getInt(2),cursor.getString(3),cursor.getInt(4),cursor.getInt(5));
                this.capitulos.add( capitulo );
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

        for (int i = 1;i <= cant;i++){
            try{
                db.beginTransaction();
                db.execSQL("INSERT INTO temporada(nombre,numTemp,idSerie) VALUES(?,?,?)",new String[]{nombre,Integer.toString( i ),Integer.toString( idSerie )});
                db.setTransactionSuccessful();
            }finally {
                db.endTransaction();
            }
        }
        this.leerBDTemporada(idSerie);
    }

    public void eliminarTemporadas(final int idSerie){
        SQLiteDatabase db = this.getDB();
        try{
            db.beginTransaction();
            db.execSQL("DELETE FROM temporada WHERE idSerie=?",new Integer[]{idSerie});
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }

    public void insertarCapitulo(final int cant,final int idTemporada) {
        SQLiteDatabase db = this.getDB();
        String nombre = "Capitulo";

        for (int i = 1; i <= cant; i++) {
            try {
                db.beginTransaction();
                db.execSQL("INSERT INTO capitulo(nombre,numCap,idTemporada) VALUES(?,?,?)", new String[]{nombre,Integer.toString( i ), Integer.toString(idTemporada)});
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }
        this.leerBDCapitulos(idTemporada);
    }

    public  void eliminarCapitulos(final int idTemporada){
        SQLiteDatabase db = this.getDB();
        try{
            db.beginTransaction();
            db.execSQL("DELETE FROM capitulo WHERE idTemporada=?",new Integer[]{idTemporada});
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }

    public void añadirComentario(final int idCapitulo,String text){
        SQLiteDatabase db = this.getDB();
        try{
            db.beginTransaction();
            db.execSQL("UPDATE capitulo SET comentario ='" + text + "' WHERE id= '"+ idCapitulo+ "'");
            db.setTransactionSuccessful();
        }finally{
            db.endTransaction();
        }
        this.getCapituloById(idCapitulo);
    }

    //Método que cambia a 0 si el capitulo no se ha visto o a 1 en caso contrario.
    public void capituloVisto(final int idCapitulo,int change){
        SQLiteDatabase db = this.db.getReadableDatabase();
        try{
            db.beginTransaction();
            db.execSQL("UPDATE capitulo SET visto ='" + change + "' WHERE id= '"+ idCapitulo+ "'");
            db.setTransactionSuccessful();
        }finally{
            db.endTransaction();
        }
    }

    //Devuelve un objeto de tipo capitulo según un id.
    public Capitulo getCapituloById(final int idCapitulo){
        SQLiteDatabase db = this.db.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM capitulo WHERE id='" + idCapitulo + "'",null);
        cursor.moveToFirst();
        Capitulo capitulo = new Capitulo(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3),cursor.getInt(4), cursor.getInt(5));

        return capitulo;
    }

}
