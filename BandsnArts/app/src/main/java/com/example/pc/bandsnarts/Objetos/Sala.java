package com.example.pc.bandsnarts;

public class Sala {
    private String nombre;
    private String cp;
    private String direccion;

    public Sala(String nombre, String cp, String direccion) {
        this.nombre = nombre;
        this.cp = cp;
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
