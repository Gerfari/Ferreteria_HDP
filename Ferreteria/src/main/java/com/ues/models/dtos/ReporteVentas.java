
package com.ues.models.dtos;

import java.sql.Date;


public class ReporteVentas {
  private String nombreCliente;  
  private String nombreEmpleado;  
  private String producto;  
  private int cantidad;
  private double precio;
  private double monto;
  private Date fecha;

    public ReporteVentas() {
    }

    public ReporteVentas(String nombreCliente, String nombreEmpleado, String producto, int cantidad, double precio, double monto, Date fecha) {
        this.nombreCliente = nombreCliente;
        this.nombreEmpleado = nombreEmpleado;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precio = precio;
        this.monto = monto;
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
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
  
  
  
}
