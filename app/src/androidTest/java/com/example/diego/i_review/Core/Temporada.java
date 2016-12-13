package com.example.diego.i_review.Core;

/**
 * Created by Diego on 30/11/2016.
 */

public class Temporada {
    private int id;
    private String nombre;
    private int numTemp;
    private int idSerie;

    public Temporada(int id,String nombre,int numTemp,int idSerie){
        this.id = id;
        this.nombre = nombre;
        this.numTemp = numTemp;
        this.idSerie = idSerie;
    }

    public int getId(){
        return id;
    }

    public String getNombre(){
        return nombre;
    }

    public int getNumTemp(){
        return numTemp;
    }

    public int getIdSerie(){
        return idSerie;
    }

    public void setId(int id){
        this.id = id;
    }

    public String toString(){
        return this.getNombre() + " " + this.getNumTemp();
    }
}
