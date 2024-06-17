/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ues.models.dtos;

import java.util.Date;

/**
 *
 * @author herna
 */
public class ComprasDTO {
    private int idCompra;
    private Date fechaCompra;
    private String duiEmpleado;
    private String empleado;
    private String proveedor;
    
    public ComprasDTO(){
        
    }

    public ComprasDTO(int idCompra) {
        this.idCompra = idCompra;
    }

    public ComprasDTO(int idCompra, Date fechaCompra, String duiEmpleado, String empleado, String proveedor) {
        this.idCompra = idCompra;
        this.fechaCompra = fechaCompra;
        this.duiEmpleado = duiEmpleado;
        this.empleado = empleado;
        this.proveedor = proveedor;
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

    public String getDuiEmpleado() {
        return duiEmpleado;
    }

    public void setDuiEmpleado(String duiEmpleado) {
        this.duiEmpleado = duiEmpleado;
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
    
}
