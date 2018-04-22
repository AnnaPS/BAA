package com.example.pc.bandsnarts.Objetos;

import java.util.ArrayList;

class Anuncio {
    private String titulo;
    private String descripcion;
    private String tipo;
    private String fecha;
    private ArrayList<String> mensaje=new ArrayList<String>();
    public ArrayList<String> getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje.add(mensaje);
    }
    public Anuncio() {
    }

    public Anuncio(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
