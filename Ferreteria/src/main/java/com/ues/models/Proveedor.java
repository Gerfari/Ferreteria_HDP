package com.ues.models;

import java.util.ArrayList;

public class Proveedor {
    private int idProveedor;
    private String nombreProveedor;
    private String direccionProveedor;
    private String telefono;
    private boolean estadoProveedor;
    private ArrayList<Compras> comprasRealizadas;

    public Proveedor() {
        this.comprasRealizadas = new ArrayList<>();
    }

    public Proveedor(int idProveedor, String nombreProveedor, String direccionProveedor, String telefono, boolean estadoProveedor) {
        this.idProveedor = idProveedor;
        this.nombreProveedor = nombreProveedor;
        this.direccionProveedor = direccionProveedor;
        this.telefono = telefono;
        this.estadoProveedor = estadoProveedor;
        this.comprasRealizadas = new ArrayList<>();
    }

    public Proveedor(int idProveedor, String nombreProveedor, String direccionProveedor, String telefono, boolean estadoProveedor, ArrayList<Compras> comprasRealizadas) {
        this.idProveedor = idProveedor;
        this.nombreProveedor = nombreProveedor;
        this.direccionProveedor = direccionProveedor;
        this.telefono = telefono;
        this.estadoProveedor = estadoProveedor;
        this.comprasRealizadas = comprasRealizadas;
    }

    // Getters y setters

    public boolean isEstadoProveedor() {
        return estadoProveedor;
    }

    public void setEstadoProveedor(boolean estadoProveedor) {
        this.estadoProveedor = estadoProveedor;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getDireccionProveedor() {
        return direccionProveedor;
    }

    public void setDireccionProveedor(String direccionProveedor) {
        this.direccionProveedor = direccionProveedor;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public ArrayList<Compras> getComprasRealizadas() {
        return comprasRealizadas;
    }

    public void setComprasRealizadas(ArrayList<Compras> comprasRealizadas) {
        this.comprasRealizadas = comprasRealizadas;
    }
}
