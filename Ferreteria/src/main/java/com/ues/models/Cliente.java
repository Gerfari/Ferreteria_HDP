
package com.ues.models;

import java.util.ArrayList;


public class Cliente {
    private String duiCliente;
    private String nombreCliente;
    private String apellidoCliente;
    private String direccionCliente;
    private String telefonoCliente;
    private boolean estadoCliente;
    private ArrayList<Ventas> ventasRealizadas;

    public Cliente() {
        this.ventasRealizadas = new ArrayList<>();
    }
    
    public Cliente(String duiCliente, String nombreCliente, String apellidoCliente, String direccionCliente, String telefonoCliente, boolean estadoCliente) {
        this.duiCliente = duiCliente;
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.direccionCliente = direccionCliente;
        this.telefonoCliente = telefonoCliente;
        this.estadoCliente = estadoCliente;
    }

    public Cliente(String duiCliente, String nombreCliente, String apellidoCliente, String direccionCliente, String telefonoCliente, ArrayList<Ventas> ventasRealizadas) {
        this.duiCliente = duiCliente;
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.direccionCliente = direccionCliente;
        this.telefonoCliente = telefonoCliente;
        this.ventasRealizadas = ventasRealizadas;
    }

    public boolean isEstadoCliente() {
        return estadoCliente;
    }

    public void setEstadoCliente(boolean estadoCliente) {
        this.estadoCliente = estadoCliente;
    }

    public String getDuiCliente() {
        return duiCliente;
    }

    public void setDuiCliente(String duiCliente) {
        this.duiCliente = duiCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public ArrayList<Ventas> getVentasRealizadas() {
        return ventasRealizadas;
    }

    public void setVentasRealizadas(ArrayList<Ventas> ventasRealizadas) {
        this.ventasRealizadas = ventasRealizadas;
    }

    @Override
    public String toString() {
        return "Cliente{" + "duiCliente=" + duiCliente + ", nombreCliente=" + nombreCliente + ", apellidoCliente=" + apellidoCliente + ", direccionCliente=" + direccionCliente + ", telefonoCliente=" + telefonoCliente + ", estadoCliente=" + estadoCliente + ", ventasRealizadas=" + ventasRealizadas + '}';
    }
    
    
    
}
