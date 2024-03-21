
package com.ues.models;

import java.util.ArrayList;
import java.util.Date;


public class Compras {
    private int idCompra;
    private Date fechaCompra;
    private Empleados empleado;
    private Proveedor proveedor;
    private ArrayList<DetalleCompra> detallesCompra;

    public Compras() {
        this.detallesCompra = new ArrayList<>();
    }

    public Compras(int idCompra, Date fechaCompra, Empleados empleado, Proveedor proveedor, ArrayList<DetalleCompra> detallesCompra) {
        this.idCompra = idCompra;
        this.fechaCompra = fechaCompra;
        this.empleado = empleado;
        this.proveedor = proveedor;
        this.detallesCompra = detallesCompra;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public Empleados getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleados empleado) {
        this.empleado = empleado;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public ArrayList<DetalleCompra> getDetallesCompra() {
        return detallesCompra;
    }

    public void setDetallesCompra(ArrayList<DetalleCompra> detallesCompra) {
        this.detallesCompra = detallesCompra;
    }
    
    
}
