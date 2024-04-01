
package com.ues.utils;


public class ClienteTotal {
    private String dui;
    private String nombre;
    private int cantidad;
    private float monto;

    public ClienteTotal() {
    }

    public ClienteTotal(String dui, String nombre, int cantidad, float monto) {
        this.dui = dui;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.monto = monto;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    @Override
    public String toString() {
        return "ClienteTotal{" + "dui=" + dui + ", nombre=" + nombre + ", cantidad=" + cantidad + ", monto=" + monto + '}';
    }
    
    
    
}
