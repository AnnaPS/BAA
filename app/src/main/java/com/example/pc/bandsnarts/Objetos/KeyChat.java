package com.example.pc.bandsnarts.Objetos;

import java.util.ArrayList;

public class KeyChat {
    private String nombre;
    private String img;
    private String key;
    private ArrayList<Mensajes2> historcoMensajes = new ArrayList<>();
    private String tipo = "tipo-tipo";
    private String notificaciones = "false-false";



    public KeyChat() {
    }

    public KeyChat(String tipo, String img, String nombre, String key) {
        this.nombre = nombre;
        this.key = key;
        this.img = img;
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public String getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(String notificaciones) {
        this.notificaciones = notificaciones;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Mensajes2> getHistorcoMensajes() {
        return historcoMensajes;
    }

    public void setHistorcoMensajes(ArrayList<Mensajes2> historcoMensajes) {
        this.historcoMensajes = historcoMensajes;
    }
}
