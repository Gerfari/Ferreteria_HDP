
package com.ues.models.dtos;


public class ProductoCatExis {
    private String nombre;
    private String descripcion;
    private String categoria;
    private int existencia;

    public ProductoCatExis() {
    }

    public ProductoCatExis(String nombre, String descripcion, String categoria, int existencia) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.existencia = existencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    @Override
    public String toString() {
        return "ProductoCatExis{" + "nombre=" + nombre + ", descripcion=" + descripcion + ", categoria=" + categoria + ", existencia=" + existencia + '}';
    }
    
    
}
