package com.example.pc.bandsnarts.Objetos;

import java.util.ArrayList;

public class KeyChat {
    private String nombre;
    private ArrayList <Mensajes2> historcoMensajes=new ArrayList<>();

    public KeyChat() {
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
