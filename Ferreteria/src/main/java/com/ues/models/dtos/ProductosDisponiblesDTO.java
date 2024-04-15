package com.ues.models.dtos;

/**
 *
 * @author kevin
 */
public class ProductosDisponiblesDTO {
    String producto;
    int cantidadDisponible;

    public ProductosDisponiblesDTO() {
    }
    
    
    public ProductosDisponiblesDTO(String producto, int cantidadDisponible) {
        this.producto = producto;
        this.cantidadDisponible = cantidadDisponible;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }
    
    
}
