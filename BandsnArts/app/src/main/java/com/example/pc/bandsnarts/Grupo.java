package com.example.pc.bandsnarts;

import java.util.ArrayList;

public class Grupo {
    private String imagen;
    private String nombre;
    private String estilo;
    private String descripcion;
    private String provincia;
    private String localidad;
    private String buscando;
    private ArrayList<Anuncio>anuncio=new ArrayList<Anuncio>();

    public String getBuscando() {
        return buscando;
    }

    public void setBuscando(String buscando) {
        this.buscando = buscando;
    }

    public Grupo(String imagen, String nombre, String estilo, String descripcion, String provincia, String localidad) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.estilo = estilo;
        this.descripcion = descripcion;
        this.provincia = provincia;
        this.localidad = localidad;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public ArrayList<Anuncio> getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(Anuncio anuncio) {
        this.anuncio.add(anuncio);
    }
}
