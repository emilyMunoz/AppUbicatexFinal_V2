package com.example.appubicatexfinal.model;

import com.mapbox.mapboxsdk.annotations.Marker;

import java.util.ArrayList;

public class Marcador {

    private int codigo ;
    private String nombre;
    //private int telefono;
    private double longitud;
    private double latitud;


    public Marcador() {


    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    //public int getTelefono() {
       // return telefono;
    //}

   // public void setTelefono(int telefono) {
       // this.telefono = telefono;
   // }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
