/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ues.models.dtos;

/**
 *
 * @author Emerson
 */
public class proveedorDemandadoDTO {
    private int id_proveedor; 
    private String nombre_proveedor; 
    private int id_producto; 
    private String nombre_producto; 
    private int total_ventas; 

    public proveedorDemandadoDTO() {
    }

    public proveedorDemandadoDTO(int id_proveedor, String nombre_proveedor, int id_producto, String nombre_producto, int total_ventas) {
        this.id_proveedor = id_proveedor;
        this.nombre_proveedor = nombre_proveedor;
        this.id_producto = id_producto;
        this.nombre_producto = nombre_producto;
        this.total_ventas = total_ventas;
    }
    
    

  

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public String getNombre_proveedor() {
        return nombre_proveedor;
    }

    public void setNombre_proveedor(String nombre_proveedor) {
        this.nombre_proveedor = nombre_proveedor;
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

    public int getTotal_ventas() {
        return total_ventas;
    }

    public void setTotal_ventas(int total_ventas) {
        this.total_ventas = total_ventas;
    }
   
    
    
    
}
