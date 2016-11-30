package com.example.diego.i_review.Core;

/**
 * Created by Diego on 30/11/2016.
 */

public class Temporada {
    private int id;
    private String nombre;
    private int idSerie;

    public Temporada(int id,String nombre,int idSerie){
        this.id = id;
        this.nombre = nombre;
        this.idSerie = idSerie;
    }

    public int getId(){
        return id;
    }

    public String getNombre(){
        return nombre;
    }

    public int getIdSerie(){
        return idSerie;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setNombre(String Nombre){
        this.nombre = nombre;
    }

    public void setIdSerie(int idSerie){
        this.idSerie = idSerie;
    }
}
