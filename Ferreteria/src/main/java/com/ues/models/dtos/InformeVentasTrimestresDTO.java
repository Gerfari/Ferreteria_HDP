package com.ues.models.dtos;

/**
 *
 * @author kevin
 */
public class InformeVentasTrimestresDTO {
    String mes;
    int ventas_por_mes;
    double total_ventas;

    public InformeVentasTrimestresDTO() {
    }

    public InformeVentasTrimestresDTO(String mes, int ventas_por_mes, double total_ventas) {
        this.mes = mes;
        this.ventas_por_mes = ventas_por_mes;
        this.total_ventas = total_ventas;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public int getVentas_por_mes() {
        return ventas_por_mes;
    }

    public void setVentas_por_mes(int ventas_por_mes) {
        this.ventas_por_mes = ventas_por_mes;
    }

    public double getTotal_ventas() {
        return total_ventas;
    }

    public void setTotal_ventas(double total_ventas) {
        this.total_ventas = total_ventas;
    }
    
    
}

    