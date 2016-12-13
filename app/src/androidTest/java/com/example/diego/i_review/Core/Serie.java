package com.example.diego.i_review.Core;

/**
 * Created by Diego on 23/11/2016.
 */

public class Serie {
    private int id;
    private String nombre;

    public Serie (int id, String nombre ){
        this.id = id;
        this.nombre = nombre;
    }

    public String getNombre(){
        return nombre;
    }

    public int getId() { return id; }

    public String toString(){
        return this.getNombre();
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setId (int id){
        this.id = id;
    }

}

