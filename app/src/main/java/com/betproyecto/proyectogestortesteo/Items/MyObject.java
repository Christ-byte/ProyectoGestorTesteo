package com.betproyecto.proyectogestortesteo.Items;

import java.util.Date;

public class MyObject {
    private int id;
    private String tipo;
    private double monto;
    private Date fecha;
    private String descripción;
    private String imagen;
    private String categoría;
    private String estado;

    public MyObject() {
        // Constructor sin argumentos necesario para Firebase Realtime Database
    }
    public MyObject(int id, String tipo, double monto, Date fecha, String descripción, String imagen, String categoría, String estado) {
        this.id = id;
        this.tipo = tipo;
        this.monto = monto;
        this.fecha = fecha;
        this.descripción = descripción;
        this.imagen = imagen;
        this.categoría = categoría;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripción() {
        return descripción;
    }

    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getCategoría() {
        return categoría;
    }

    public void setCategoría(String categoría) {
        this.categoría = categoría;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
