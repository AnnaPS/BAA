package com.example.pc.bandsnarts.Objetos;

import java.util.ArrayList;

public class Anuncio {
    private String titulo;
    private String descripcion;
    private String tipo;
    private String fecha;
    private String provincia, localidad, estilo, instrumento,sexo;




    public Anuncio() {
    }

    public Anuncio(String titulo, String descripcion, String tipo, String fecha, String provincia, String localidad, String estilo, String instrumento, String sexo) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.fecha = fecha;
        this.provincia = provincia;
        this.localidad = localidad;
        this.estilo = estilo;
        this.instrumento = instrumento;
        this.sexo = sexo;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Anuncio(String titulo, String descripcion, String fecha, String localidad, String provincia, String estilo, String instrumento, String sexo) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.localidad = localidad;
        this.provincia = provincia;

        this.estilo = estilo;
        this.instrumento = instrumento;
        this.sexo=sexo;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public String getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(String instrumento) {
        this.instrumento = instrumento;
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
