package com.ues.models.dtos;

import java.util.Date;

/**
 *
 * @author kevin
 */
public class InformeComprasProductosDTO {
    
    String producto;
    String categoria;
    String proveedor;
    Date fecha_compra;
    int cantidad_compra;
    double precio_producto;
    double total_compra;
    int existencia;

    public InformeComprasProductosDTO() {
    }

    public InformeComprasProductosDTO(String producto, String categoria, String proveedor, Date fecha_compra, int cantidad_compra, double precio_producto, double total_compra, int existencia) {
        this.producto = producto;
        this.categoria = categoria;
        this.proveedor = proveedor;
        this.fecha_compra = fecha_compra;
        this.cantidad_compra = cantidad_compra;
        this.precio_producto = precio_producto;
        this.total_compra = total_compra;
        this.existencia = existencia;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public Date getFecha_compra() {
        return fecha_compra;
    }

    public void setFecha_compra(Date fecha_compra) {
        this.fecha_compra = fecha_compra;
    }

    public int getCantidad_compra() {
        return cantidad_compra;
    }

    public void setCantidad_compra(int cantidad_compra) {
        this.cantidad_compra = cantidad_compra;
    }

    public double getPrecio_producto() {
        return precio_producto;
    }

    public void setPrecio_producto(double precio_producto) {
        this.precio_producto = precio_producto;
    }

    public double getTotal_compra() {
        return total_compra;
    }

    public void setTotal_compra(double total_compra) {
        this.total_compra = total_compra;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }
}
