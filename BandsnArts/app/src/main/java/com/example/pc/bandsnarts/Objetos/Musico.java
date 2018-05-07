package com.example.pc.bandsnarts.Objetos;

import java.util.ArrayList;

public class Musico {

    private String uid;
    private String imagen;
    private String audio;
    private String nombre;
    private String sexo;
    private String estilo;
    private ArrayList<String> instrumento;
    private String descripcion;
    private String provincia;
    private String localidad;
    private ArrayList<Anuncio> anuncio = new ArrayList<Anuncio>();
    private String buscando="no";
    private ArrayList<String> redsocial=new ArrayList<String>();



    public Musico(String imagen, String sexo, String estilo, ArrayList<String> instrumento, String descripcion, String provincia, String localidad, String buscando) {
        this.imagen = imagen;
        this.sexo = sexo;
        this.estilo = estilo;
        this.instrumento = instrumento;
        this.descripcion = descripcion;
        this.provincia = provincia;
        this.localidad = localidad;
        this.buscando = buscando;
    }

    public String getBuscando() {
        return buscando;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setBuscando(String buscando) {
        this.buscando = buscando;
    }

    public Musico() {
    }

    public Musico(String uid, String imagen, String nombre, String sexo, String estilo, ArrayList<String> instrumento, String descripcion) {
        this.uid=uid;
        this.imagen = imagen;
        this.nombre = nombre;
        this.sexo = sexo;
        this.estilo = estilo;
        this.instrumento = instrumento;
        this.descripcion = descripcion;
    }


    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }


    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public ArrayList<String> getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(ArrayList<String> instrumento) {
        this.instrumento = instrumento;
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

    public void setRedsocial(String redsocial) {
        this.redsocial.add(redsocial);
    }

    public ArrayList<String> getRedsocial() {
        return redsocial;
    }
}
