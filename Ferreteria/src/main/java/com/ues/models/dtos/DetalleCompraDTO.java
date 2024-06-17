
package com.ues.models.dtos;

import java.util.Date;

public class DetalleCompraDTO {
    private String empleado;
    private String proveedor;
    private String producto;
    private Date fechaCompra;
    private int cantidad;
    private int existencia;
    private float precio;
    private float total;

    public DetalleCompraDTO() {
    }

    public DetalleCompraDTO(String empleado, String proveedor, String producto, Date fechaCompra, int cantidad, int existencia, float precio, float total) {
        this.empleado = empleado;
        this.proveedor = proveedor;
        this.producto = producto;
        this.fechaCompra = fechaCompra;
        this.cantidad = cantidad;
        this.existencia = existencia;
        this.precio = precio;
        this.total = total;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

   

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
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
