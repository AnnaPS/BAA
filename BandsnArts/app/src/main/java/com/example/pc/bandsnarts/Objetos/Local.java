package com.example.pc.bandsnarts.Objetos;

public class Local {
    private String nombre;
    private String cp;
    private String direccion;
    private String localidad;
    private String provincia;

    public Local() {
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public Local(String nombre, String cp, String direccion, String localidad, String provincia) {
        this.nombre = nombre;
        this.cp = cp;
        this.direccion = direccion;
        this.localidad = localidad;
        this.provincia=provincia;
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
