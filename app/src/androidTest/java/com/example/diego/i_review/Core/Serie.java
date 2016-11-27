package com.example.diego.i_review.Core;

/**
 * Created by Diego on 23/11/2016.
 */

public class Serie {
    private int id;
    private String nombre;
    private int valoracion;

    public Serie ( String nombre){
        this.id = id;
        this.nombre = nombre;
        //this.valoracion = valoracion;
    }

    public String getNombre(){
        return nombre;
    }
    public int getId() { return id; }
    public int getValoracion() {return valoracion;}

    public String toString(){
        return this.getNombre(); //+ ". Valoracion.:" + this.getValoracion();
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public void setId (int id){
        this.id = id;
    }
    public void setValoracion(){
        this.valoracion = valoracion;
    }
}

