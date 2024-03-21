
package com.ues.models;

import java.util.ArrayList;


public class Categorias {
    private int idCategoria;
    private String categoria;
    private ArrayList<Productos> productos;

    public Categorias() {
        this.productos = new ArrayList<>();
    }

    public Categorias(int idCategoria, String categoria, ArrayList<Productos> productos) {
        this.idCategoria = idCategoria;
        this.categoria = categoria;
        this.productos = productos;
    }

    
    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public ArrayList<Productos> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Productos> productos) {
        this.productos = productos;
    }
    
    
}
