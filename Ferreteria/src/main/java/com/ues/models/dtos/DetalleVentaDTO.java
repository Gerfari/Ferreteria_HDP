
package com.ues.models.dtos;

import java.util.Date;


public class DetalleVentaDTO {
    private String empleado;
    private String cliente;
    private String producto;
    private Date fechaVenta;
    private int cantidad;
    private float precio;
    private float total;

    public DetalleVentaDTO() {
    }

    public DetalleVentaDTO(String empleado, String cliente, String producto, Date fechaVenta, int cantidad, float precio, float total) {
        this.empleado = empleado;
        this.cliente = cliente;
        this.producto = producto;
        this.fechaVenta = fechaVenta;
        this.cantidad = cantidad;
        this.precio = precio;
        this.total = total;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
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

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
    
    
}
