
package com.ues.models;

import java.util.ArrayList;
import java.util.Date;


public class Ventas {
    private int idVenta;
    private Date fechaVenta;
    private Cliente cliente;
    private Empleados empleado;
    private ArrayList<DetalleVenta> detallesVenta;

    public Ventas() {
        this.detallesVenta = new ArrayList<>();
    }

    public Ventas(int idVenta, Date fechaVenta, Cliente cliente, Empleados empleado, ArrayList<DetalleVenta> detallesVenta) {
        this.idVenta = idVenta;
        this.fechaVenta = fechaVenta;
        this.cliente = cliente;
        this.empleado = empleado;
        this.detallesVenta = detallesVenta;
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Empleados getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleados empleado) {
        this.empleado = empleado;
    }

    public ArrayList<DetalleVenta> getDetallesVenta() {
        return detallesVenta;
    }

    public void setDetallesVenta(ArrayList<DetalleVenta> detallesVenta) {
        this.detallesVenta = detallesVenta;
    }
    
    
}
