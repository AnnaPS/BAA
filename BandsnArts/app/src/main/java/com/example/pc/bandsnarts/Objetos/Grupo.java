package com.example.pc.bandsnarts.Objetos;

import java.util.ArrayList;

public class Grupo {

    private String uid;
    private String imagen;
    private String audio;
    private String nombre;
    private String estilo;
    private String descripcion;
    private String provincia="Sin especificar";
    private String localidad="Sin especificar";
    private String buscando="no";
    private String token;
    private ArrayList<String> keyChat=new ArrayList<>();

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public ArrayList<String> getKeyChat() {
        return keyChat;
    }

    public void setKeyChat(ArrayList<String> keyChat) {
        this.keyChat = keyChat;
    }

    private ArrayList<Anuncio>anuncio=new ArrayList<Anuncio>();
    private ArrayList<String> redsocial=new ArrayList<String>(){{
        add("youtube");
        add("facebook");
        add("instagram");
    }};

    public Grupo(){
    }


    public void setAnuncios(Anuncio anuncio) {
        this.anuncio.add(anuncio);
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }
    public void setAnuncio(ArrayList<Anuncio> anuncio) {
        this.anuncio = anuncio;
    }

    public void setRedsocial(ArrayList<String> redsocial) {
        this.redsocial = redsocial;
    }


    public ArrayList<String> getRedsocial() {
        return redsocial;
    }

    public String getBuscando() {
        return buscando;
    }

    public void setBuscando(String buscando) {
        this.buscando = buscando;
    }



    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public Grupo(String token,String uid, String imagen, String nombre, String estilo, String descripcion) {
        this.token=token;
        this.uid=uid;
        this.imagen = imagen;
        this.nombre = nombre;
        this.estilo = estilo;
        this.descripcion = descripcion;
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



}
