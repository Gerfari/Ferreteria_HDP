/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ues.models.dtos;

/**
 *
 * @author herna
 */
public class VentasIntervaloFecha {
    private String nombreEmpleado;
    private int totalDeVentas;
    
    public VentasIntervaloFecha(){
        
    }
    public VentasIntervaloFecha(String nombreEmpleado,int totalDeVentas){
        this.nombreEmpleado=nombreEmpleado;
        this.totalDeVentas=totalDeVentas;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public int getTotalDeVentas() {
        return totalDeVentas;
    }

    public void setTotalDeVentas(int totalDeVentas) {
        this.totalDeVentas = totalDeVentas;
    }
    
    
    
}
