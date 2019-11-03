package com.example.appubicatexfinal.model;

import com.example.appubicatexfinal.MainActivity;
import com.example.appubicatexfinal.R;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;

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

            return "\n Nombre: "+ nombre +
                    "\n Codigo: " + codigo +
                    "\n Longitud: "+ longitud +
                    "\n Latitud: "+ latitud;
        }

    }

