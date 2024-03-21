
package com.ues.models;

import java.util.ArrayList;
import java.util.Date;


public class DetalleCompra {
    private int idDetalleCompra;
    private Date fechaDetalleCompra;
    private Compras compra;
    private Productos producto;
    private int cantidad;
    private float precio;
    private int existencia;
    private ArrayList<DetalleVenta> detallesVenta;

    public DetalleCompra() {
        this.detallesVenta = new ArrayList<>();
    }

    public DetalleCompra(int idDetalleCompra, Date fechaDetalleCompra, Compras compra, Productos producto, int cantidad, float precio, int existencia, ArrayList<DetalleVenta> detallesVenta) {
        this.idDetalleCompra = idDetalleCompra;
        this.fechaDetalleCompra = fechaDetalleCompra;
        this.compra = compra;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precio = precio;
        this.existencia = existencia;
        this.detallesVenta = detallesVenta;
    }

    public int getIdDetalleCompra() {
        return idDetalleCompra;
    }

    public void setIdDetalleCompra(int idDetalleCompra) {
        this.idDetalleCompra = idDetalleCompra;
    }

    public Date getFechaDetalleCompra() {
        return fechaDetalleCompra;
    }

    public void setFechaDetalleCompra(Date fechaDetalleCompra) {
        this.fechaDetalleCompra = fechaDetalleCompra;
    }

    public Compras getCompra() {
        return compra;
    }

    public void setCompra(Compras compra) {
        this.compra = compra;
    }

    public Productos getProducto() {
        return producto;
    }

    public void setProducto(Productos producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public ArrayList<DetalleVenta> getDetallesVenta() {
        return detallesVenta;
    }

    public void setDetallesVenta(ArrayList<DetalleVenta> detallesVenta) {
        this.detallesVenta = detallesVenta;
    }
    
    
}
