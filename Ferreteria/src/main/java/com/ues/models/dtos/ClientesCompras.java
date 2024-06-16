
package com.ues.models.dtos;

import java.util.Date;


public class ClientesCompras {
    private String nombreCliente;
    private String nombreEmpleado;
    private String producto;
    private double precio;
    private Date fecha;

    public ClientesCompras() {
    }

    public ClientesCompras(String nombreCliente, String nombreEmpleado, String producto, double precio, Date fecha) {
        this.nombreCliente = nombreCliente;
        this.nombreEmpleado = nombreEmpleado;
        this.producto = producto;
        this.precio = precio;
        this.fecha = fecha;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    
}
