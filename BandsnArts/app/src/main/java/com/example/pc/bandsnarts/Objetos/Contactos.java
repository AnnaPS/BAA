package com.example.pc.bandsnarts.Objetos;

public class Contactos {

    private String nombre,fotoPerfil;

    public Contactos() {

    }

    public Contactos(String nombre, String fotoPerfil) {
        this.nombre = nombre;
        this.fotoPerfil = fotoPerfil;


    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

}
