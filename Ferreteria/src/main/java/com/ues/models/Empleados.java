package com.ues.models;

import java.util.ArrayList;
import java.util.Date;


public class Empleados {
    private String duiEmpleado;
    private String nombreEmpleado;
    private String apellidoEmpleado;
    private Date fechaNacimiento;
    private String direccionEmpleado;
    private String telefonoEmpleado;
    private char genero;
    private boolean estadoEmpleado;
    private Roles rol; 
    private String correo;
    private String contraseña;
    private ArrayList<Compras> comprasRealizadas;
    private ArrayList<Ventas> ventasRealizadas;

    public Empleados() {
        this.comprasRealizadas = new ArrayList<>();
        this.ventasRealizadas = new ArrayList<>();
    }

    public Empleados(String duiEmpleado, String nombreEmpleado, String apellidoEmpleado, Date fechaNacimiento, String direccionEmpleado, String telefonoEmpleado, char genero, boolean estadoEmpleado, Roles rol, String correo, String contraseña, ArrayList<Compras> comprasRealizadas, ArrayList<Ventas> ventasRealizadas) {
        this.duiEmpleado = duiEmpleado;
        this.nombreEmpleado = nombreEmpleado;
        this.apellidoEmpleado = apellidoEmpleado;
        this.fechaNacimiento = fechaNacimiento;
        this.direccionEmpleado = direccionEmpleado;
        this.telefonoEmpleado = telefonoEmpleado;
        this.genero = genero;
        this.estadoEmpleado = estadoEmpleado;
        this.rol = rol;
        this.correo = correo;
        this.contraseña = contraseña;
        this.comprasRealizadas = comprasRealizadas;
        this.ventasRealizadas = ventasRealizadas;
    }

    public Empleados(String duiEmpleado, String nombreEmpleado, String apellidoEmpleado, Date fechaNacimiento, String direccionEmpleado, String telefonoEmpleado, char genero, boolean estadoEmpleado, Roles rol, String correo, String contraseña) {
        this.duiEmpleado = duiEmpleado;
        this.nombreEmpleado = nombreEmpleado;
        this.apellidoEmpleado = apellidoEmpleado;
        this.fechaNacimiento = fechaNacimiento;
        this.direccionEmpleado = direccionEmpleado;
        this.telefonoEmpleado = telefonoEmpleado;
        this.genero = genero;
        this.estadoEmpleado = estadoEmpleado;
        this.rol = rol;
        this.correo = correo;
        this.contraseña = contraseña;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }


    public boolean isEstadoEmpleado() {
        return estadoEmpleado;
    }

    public void setEstadoEmpleado(boolean estadoEmpleado) {
        this.estadoEmpleado = estadoEmpleado;
    }

    public String getDuiEmpleado() {
        return duiEmpleado;
    }

    public void setDuiEmpleado(String duiEmpleado) {
        this.duiEmpleado = duiEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getApellidoEmpleado() {
        return apellidoEmpleado;
    }

    public void setApellidoEmpleado(String apellidoEmpleado) {
        this.apellidoEmpleado = apellidoEmpleado;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDireccionEmpleado() {
        return direccionEmpleado;
    }

    public void setDireccionEmpleado(String direccionEmpleado) {
        this.direccionEmpleado = direccionEmpleado;
    }

    public String getTelefonoEmpleado() {
        return telefonoEmpleado;
    }

    public void setTelefonoEmpleado(String telefonoEmpleado) {
        this.telefonoEmpleado = telefonoEmpleado;
    }

    public char getGenero() {
        return genero;
    }

    public void setGenero(char genero) {
        this.genero = genero;
    }

    public Roles getRol() {
        return rol;
    }

    public void setRol(Roles rol) {
        this.rol = rol;
    }

    public ArrayList<Compras> getComprasRealizadas() {
        return comprasRealizadas;
    }

    public void setComprasRealizadas(ArrayList<Compras> comprasRealizadas) {
        this.comprasRealizadas = comprasRealizadas;
    }

    public ArrayList<Ventas> getVentasRealizadas() {
        return ventasRealizadas;
    }

    public void setVentasRealizadas(ArrayList<Ventas> ventasRealizadas) {
        this.ventasRealizadas = ventasRealizadas;
    }
    
    
}
