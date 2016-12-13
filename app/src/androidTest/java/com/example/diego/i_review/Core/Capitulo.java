package com.example.diego.i_review.Core;

import android.provider.ContactsContract;

/**
 * Created by Diego on 05/12/2016.
 */

public class Capitulo {
    private int id;
    private String nombre;
    private int numCap;
    private String comentario;
    private int visto;
    private int idTemporada;

    public Capitulo(int id,String nombre,int numCap,String comentario,int visto,int idTemporada){
        this.id = id;
        this.nombre = nombre;
        this.numCap = numCap;
        this.comentario = comentario;
        this.visto = visto;
        this.idTemporada = idTemporada;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getNumCap(){
        return numCap;
    }

    public int getVisto(){
        return visto;
    }

    public String getComentario(){
        return comentario;
    }

    public void setComentario(String comentario){
        this.comentario = comentario;
    }

    public int getIdTemporada() {
        return idTemporada;
    }


    public String toString(){
        return this.getNombre() + this.getNumCap();
    }

}
