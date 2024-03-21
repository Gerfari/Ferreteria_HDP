
package com.ues.models;

import java.util.ArrayList;


public class Roles {
    private int idRol;
    private String rol;
    private ArrayList<Empleados> empleados;

    public Roles() {
        this.empleados = new ArrayList<>();
    }

    public Roles(int idRol, String rol, ArrayList<Empleados> empleados) {
        this.idRol = idRol;
        this.rol = rol;
        this.empleados = empleados;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public ArrayList<Empleados> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(ArrayList<Empleados> empleados) {
        this.empleados = empleados;
    }
    
    
}
