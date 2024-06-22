/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ues.models.dtos;

import java.util.Date;

/**
 *
 * @author JAVIER MONGE
 */
public class DetalleProductoDTO {
    private int id_detalle_compra;
    private int id_producto;
    private String nombre_producto;
    private Date fecha_compra;
    private float precio;
    private int existencia;

    public DetalleProductoDTO() {
    }

    public DetalleProductoDTO(int id_producto, String nombre_producto, Date fecha_compra, float precio, int existencia) {
        this.id_producto = id_producto;
        this.nombre_producto = nombre_producto;
        this.fecha_compra = fecha_compra;
        this.precio = precio;
        this.existencia = existencia;
    }

    public int getId_detalle_compra() {
        return id_detalle_compra;
    }

    public void setId_detalle_compra(int id_detalle_compra) {
        this.id_detalle_compra = id_detalle_compra;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public Date getFecha_compra() {
        return fecha_compra;
    }

    public void setFecha_compra(Date fecha_compra) {
        this.fecha_compra = fecha_compra;
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

    @Override
    public String toString() {
        return "DetalleProductoDTO{" + "id_producto=" + id_producto + ", nombre_producto=" + nombre_producto + ", fecha_compra=" + fecha_compra + ", precio=" + precio + ", existencia=" + existencia + '}';
    }
    
    
}