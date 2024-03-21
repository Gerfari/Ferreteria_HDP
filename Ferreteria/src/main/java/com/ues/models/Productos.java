package com.ues.models;

import java.util.ArrayList;

public class Productos {

    private int idProducto;
    private String nombreProducto;
    private String descripcion;
    private boolean estadoProducto;
    private Categorias categoria;
    private ArrayList<DetalleCompra> detallesCompra;

    public Productos() {
        this.detallesCompra = new ArrayList<>();
    }

    public Productos(int idProducto, String nombreProducto, String descripcion, Categorias categoria, ArrayList<DetalleCompra> detallesCompra) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.detallesCompra = detallesCompra;
    }

    public Productos(int idProducto, String nombreProducto, String descripcion, boolean estadoProducto, Categorias categoria, ArrayList<DetalleCompra> detallesCompra) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.descripcion = descripcion;
        this.estadoProducto = estadoProducto;
        this.categoria = categoria;
        this.detallesCompra = detallesCompra;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isEstadoProducto() {
        return estadoProducto;
    }

    public void setEstadoProducto(boolean estadoProducto) {
        this.estadoProducto = estadoProducto;
    }

    public Categorias getCategoria() {
        return categoria;
    }

    public void setCategoria(Categorias categoria) {
        this.categoria = categoria;
    }

    public ArrayList<DetalleCompra> getDetallesCompra() {
        return detallesCompra;
    }

    public void setDetallesCompra(ArrayList<DetalleCompra> detallesCompra) {
        this.detallesCompra = detallesCompra;
    }

}
