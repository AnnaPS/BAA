package com.example.pc.bandsnarts.Objetos;

public class Mensaje {
    private String mensaje;
    private String fecha;
    private String imagenUsu;
    private String nombreUsu;

    public Mensaje() {
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getImagenUsu() {
        return imagenUsu;
    }

    public void setImagenUsu(String imagenUsu) {
        this.imagenUsu = imagenUsu;
    }

    public String getNombreUsu() {
        return nombreUsu;
    }

    public void setNombreUsu(String nombreUsu) {
        this.nombreUsu = nombreUsu;
    }
}
