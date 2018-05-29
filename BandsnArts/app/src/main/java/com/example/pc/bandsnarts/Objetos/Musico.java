package com.example.pc.bandsnarts.Objetos;

import java.util.ArrayList;

public class Musico {

    private String uid;
    private String imagen;
    private String audio;
    private String nombre;
    private String sexo;
    private String estilo;
    private ArrayList<String> instrumento=new ArrayList<>();
    private String descripcion;
    private String provincia="Sin especificar";
    private String localidad="Sin especificar";
    private ArrayList<Anuncio> anuncio = new ArrayList<Anuncio>();
    private String buscando="no";
    private ArrayList <String> token=new ArrayList<String>();
    private ArrayList<String> redsocial=new ArrayList<String>(){{
        add("youtube");
        add("facebook");
        add("instagram");
    }};
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

    public ArrayList<String> getToken() {
        return token;
    }

    public void setToken(ArrayList<String> token) {
        this.token = token;
    }

    private ArrayList <String> keyChat=new ArrayList<>();

    public ArrayList<String> getKeyChat() {
        return keyChat;
    }

    public void setKeyChat(ArrayList<String> keyChat) {
        this.keyChat = keyChat;
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

    public void setAnuncios(Anuncio anuncio) {
        this.anuncio.add(anuncio);
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
}
