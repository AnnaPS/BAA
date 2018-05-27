package com.example.pc.bandsnarts.Objetos;

public class Mensajes2 {

    private String mensaje;
    private String nombre;
    private String fotoPerfil;
    private String hora;
    private String UID;

    public Mensajes2() {

    }

    public Mensajes2(String mensaje, String nombre, String fotoPerfil,  String hora,String UID) {
        this.mensaje = mensaje;
        this.nombre = nombre;
        this.fotoPerfil = fotoPerfil;
        this.hora = hora;
        this.UID=UID;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
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

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
