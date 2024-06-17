
package com.ues.models.dtos;

import java.util.Date;


public class VentaDTO {
    private int idVenta;
    private Date fechaVenta;
    private String empleado;
    private String cliente;

    public VentaDTO() {
    }

    public VentaDTO(int idVenta, Date fechaVenta, String duiEmpleado, String empleado, String cliente) {
        this.idVenta = idVenta;
        this.fechaVenta = fechaVenta;
        this.empleado = empleado;
        this.cliente = cliente;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
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
    
    
}
