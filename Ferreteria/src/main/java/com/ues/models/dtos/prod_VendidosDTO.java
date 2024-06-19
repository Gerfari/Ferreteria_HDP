/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ues.models.dtos;

/**
 *
 * @author Emerson
 */
public class prod_VendidosDTO {

    
    private String nombreProducto;
private int totalVendido;

public prod_VendidosDTO() {
    }

    public prod_VendidosDTO(String nombreProducto, int totalVendido) {
        this.nombreProducto = nombreProducto;
        this.totalVendido = totalVendido;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getTotalVendido() {
        return totalVendido;
    }

    public void setTotalVendido(int totalVendido) {
        this.totalVendido = totalVendido;
    }
   

 

    
    
}