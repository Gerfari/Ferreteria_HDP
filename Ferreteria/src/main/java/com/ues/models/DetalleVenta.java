
package com.ues.models;

import java.util.logging.Logger;


public class DetalleVenta {
    private int idDetalleVenta;
    private int cantidad;
    private float precioVenta;
    private Ventas venta;
    private DetalleCompra detalleCompra;

    public DetalleVenta() {
    }

    public DetalleVenta(int idDetalleVenta, int cantidad, float precioVenta, Ventas venta, DetalleCompra detalleCompra) {
        this.idDetalleVenta = idDetalleVenta;
        this.cantidad = cantidad;
        this.precioVenta = precioVenta;
        this.venta = venta;
        this.detalleCompra = detalleCompra;
    }

    public int getIdDetalleVenta() {
        return idDetalleVenta;
    }

    public void setIdDetalleVenta(int idDetalleVenta) {
        this.idDetalleVenta = idDetalleVenta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(float precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Ventas getVenta() {
        return venta;
    }

    public void setVenta(Ventas venta) {
        this.venta = venta;
    }

    public DetalleCompra getDetalleCompra() {
        return detalleCompra;
    }

    public void setDetalleCompra(DetalleCompra detalleCompra) {
        this.detalleCompra = detalleCompra;
    }
    
    
    
}
