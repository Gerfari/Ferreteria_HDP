/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ues.models.dtos;

/**
 *
 * @author herna
 */
public class CantidadPorCategoriaDTO {
    private String nombreCategoria;
    private int cantidadCategoria;
    private int cantidadTotal;
    
    public CantidadPorCategoriaDTO(){
        
    }
    public CantidadPorCategoriaDTO(String nombreCategoria,int cantidadCategoria, int cantidadTotal){
        this.nombreCategoria=nombreCategoria;
        this.cantidadCategoria=cantidadCategoria;
        this.cantidadTotal=cantidadTotal;
    }
    public CantidadPorCategoriaDTO(String nombreCategoria, int cantidadCategoria){
        this.nombreCategoria=nombreCategoria;
        this.cantidadCategoria=cantidadCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public int getCantidadCategoria() {
        return cantidadCategoria;
    }

    public void setCantidadCategoria(int cantidadCategoria) {
        this.cantidadCategoria = cantidadCategoria;
    }

    public int getCantidadTotal() {
        return cantidadTotal;
    }

    public void setCantidadTotal(int cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }
    
    
}
