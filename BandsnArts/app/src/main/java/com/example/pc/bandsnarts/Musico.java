package com.example.pc.bandsnarts;

public class Musico {
    private String imagen;
    private String nombre;
    private String sexo;
    private String estilo;
    private String instrumento;
    private String descripcion;
    private String provincia;
    private String localidad;
    private Anuncio anuncio;
    private String buscando;

    public String getBuscando() {
        return buscando;
    }

    public void setBuscando(String buscando) {
        this.buscando = buscando;
    }

    public Musico(String imagen, String nombre, String sexo, String estilo, String instrumento, String descripcion, String provincia, String localidad, Anuncio anuncio) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.sexo = sexo;
        this.estilo = estilo;
        this.instrumento = instrumento;
        this.descripcion = descripcion;
        this.provincia = provincia;
        this.localidad = localidad;
        this.anuncio = anuncio;
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

    public String getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(String instrumento) {
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

    public Anuncio getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(Anuncio anuncio) {
        this.anuncio = anuncio;
    }
}
