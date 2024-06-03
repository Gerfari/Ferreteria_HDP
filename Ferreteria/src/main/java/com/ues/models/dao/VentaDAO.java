/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ues.models.dao;

import com.ues.models.Conexion;
import com.ues.models.dtos.VentasIntervaloFecha;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author herna
 */
public class VentaDAO {
    private Conexion conexion;
    private Connection accesoDB;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public VentaDAO(){
        this.conexion=new Conexion();
    }
    private static String FECHAS_DE_VENTAS="SELECT fecha_venta FROM ventas ORDER BY fecha_venta ASC";
    private static String OBTENER_VENTAS_ENTRE_DOS_FECHAS="SELECT e.nombre_empleado,e.apellido_empleado, COUNT(DISTINCT v.id_venta) AS total_ventas,SUM(dv.cantidad*dv.precio_venta) as monto FROM Empleados e INNER JOIN Ventas v ON e.dui_empleado = v.dui_empleado INNER JOIN detalle_ventas dv on dv.id_venta=v.id_venta WHERE v.fecha_venta BETWEEN ? AND ? GROUP BY e.nombre_empleado,e.apellido_empleado";
    
    
    //PARA LLENAR EL COMBOBOX
    public ArrayList<Date> fechasDeVentas(){
        ArrayList<Date> lsFechas = new ArrayList<>();
        try {
            this.conexion=new Conexion();
            this.accesoDB=conexion.getConexion();
            this.ps=accesoDB.prepareStatement(FECHAS_DE_VENTAS);
            this.rs=ps.executeQuery();
            while(rs.next()){
                Date fecha = rs.getDate("fecha_venta");
                lsFechas.add(fecha);
            }
            
            
            this.conexion.cerrarConexiones();
        } catch (SQLException ex) {
            Logger.getLogger(VentaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lsFechas;
    }
    
    //PARA MOSTRAR LAS VENTAS SELECCIONADAS DEL COMBOBOX
    public ArrayList<VentasIntervaloFecha> ventasEntreFechas(String fechaInicio,String fechaFin){
        ArrayList<VentasIntervaloFecha> lsVentas = new ArrayList<>();
        try {
            //CONVIERTO LAS FECHAS DE STRING A DATE
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDateInicio = LocalDate.parse(fechaInicio,formato);
            LocalDate localDateFin = LocalDate.parse(fechaFin,formato);
            
//            Date fechaIni = java.sql.Date.valueOf(localDateInicio);
//            Date fechaF = java.sql.Date.valueOf(localDateFin);
            this.accesoDB=conexion.getConexion();
            
            this.ps=accesoDB.prepareStatement(OBTENER_VENTAS_ENTRE_DOS_FECHAS);
            this.ps.setDate(1, java.sql.Date.valueOf(localDateInicio));
            this.ps.setDate(2, java.sql.Date.valueOf(localDateFin));
            this.rs=ps.executeQuery();
            
            while(this.rs.next()){
                VentasIntervaloFecha dtoVentas = new VentasIntervaloFecha();
                dtoVentas.setNombreEmpleado(rs.getString("nombre_empleado")+" "+rs.getString("apellido_empleado"));
                dtoVentas.setTotalDeVentas(rs.getInt("total_ventas"));
                dtoVentas.setMontoTotal(rs.getDouble("monto"));
                lsVentas.add(dtoVentas);
            }
            
            this.conexion.cerrarConexiones();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lsVentas;
    }
    
    
}
