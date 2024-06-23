/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ues.models.dao;

import com.ues.models.Cliente;
import com.ues.models.Conexion;
import com.ues.models.DetalleVenta;
import com.ues.models.Ventas;
import com.ues.models.dtos.VentasIntervaloFecha;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
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
    
    DecimalFormat formato = new DecimalFormat("0.00");
    public VentaDAO(){
        this.conexion=new Conexion();
    }
    private static String FECHAS_DE_VENTAS="SELECT fecha_venta FROM ventas ORDER BY fecha_venta ASC";
    private static String OBTENER_VENTAS_ENTRE_DOS_FECHAS="SELECT e.nombre_empleado,e.apellido_empleado, COUNT(DISTINCT v.id_venta) AS total_ventas,SUM(dv.cantidad*dv.precio_venta) as monto FROM Empleados e INNER JOIN Ventas v ON e.dui_empleado = v.dui_empleado INNER JOIN detalle_ventas dv on dv.id_venta=v.id_venta WHERE v.fecha_venta BETWEEN ? AND ? GROUP BY e.nombre_empleado,e.apellido_empleado order by monto desc";
    private static String SELECIONAR_CLIENTES="SELECT * FROM clientes where estado_cliente=true order by nombre_cliente";
    private static String INSERTAR_VENTA= "INSERT INTO Ventas(fecha_venta, dui_cliente, dui_empleado) VALUES (?,?,?)";
    private static String ULTIMA_VENTA= "SELECT id_venta FROM Ventas ORDER BY id_venta DESC LIMIT 1";
    private static String REGISTRAR_DETALLES= "INSERT INTO detalle_ventas(cantidad, precio_venta, id_venta, id_detalle_compra) VALUES(?, ?, ?, ?)";
    private static String ACTUALIZAR_EXISTENCIA="update detalle_compras  set existencia=? where id_detalle_compra=?";
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
    public ArrayList<Cliente>listaClientes() throws SQLException{
        ArrayList<Cliente> lsClientes = new ArrayList<>();
        try {
            this.conexion=new Conexion();
            this.accesoDB=conexion.getConexion();
            this.ps=accesoDB.prepareStatement(SELECIONAR_CLIENTES);
            this.rs=ps.executeQuery();
            while(rs.next()){
               Cliente objcliente = new Cliente();
               objcliente.setNombreCliente(rs.getString("nombre_cliente"));
               objcliente.setApellidoCliente(rs.getString("apellido_cliente"));
               objcliente.setDuiCliente(rs.getString("dui_cliente"));
                lsClientes.add(objcliente);
            }
            this.conexion.cerrarConexiones();
        } catch (SQLException ex) {
            Logger.getLogger(VentaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lsClientes;
    }
    public boolean insertVenta(Ventas venta){
        boolean resultado=false;
        this.conexion=new Conexion();
        this.accesoDB=conexion.getConexion();
        try {
            this.ps=accesoDB.prepareStatement(INSERTAR_VENTA);
            this.ps.setDate(1, new java.sql.Date(venta.getFechaVenta().getTime()));
                        this.ps.setString(2, venta.getCliente().getDuiCliente());

            this.ps.setString(3, venta.getEmpleado().getDuiEmpleado());
            
            int respuesta=this.ps.executeUpdate();
            if (respuesta>0) {
                resultado=true;
            }else{
                resultado=false;
            }
            this.conexion.cerrarConexiones();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return resultado;
    }
    public int ultimaVenta(){
        int codigoVenta=0;
        try {
            this.conexion=new Conexion();
            this.accesoDB=this.conexion.getConexion();
            this.ps=this.accesoDB.prepareStatement(ULTIMA_VENTA);
            this.rs=this.ps.executeQuery();
            
            while(this.rs.next()){
                codigoVenta=rs.getInt("id_venta");
            }
            this.conexion.cerrarConexiones();
        } catch (SQLException ex) {
            Logger.getLogger(CompraDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return codigoVenta;
    }
    public boolean registrarDetalle(DetalleVenta objDetalle,int idVenta){
        boolean resultado=false;
        try {
            this.conexion=new Conexion();
            this.accesoDB=this.conexion.getConexion();
            this.ps=this.accesoDB.prepareStatement(REGISTRAR_DETALLES);
            //FORMATEO A 2 DECIMALES EL PRECIO
            String precioFormateado=formato.format(objDetalle.getPrecioVenta());
            float precio=Float.parseFloat(precioFormateado);
            precio=precio+1;
            System.out.println("PRECIO SUMANDO 1: "+precio);
            precio=precio-1;
            System.out.println("PRECIO RESTANDO 1 OSEA EL ORIGINAL: "+precio);
            System.out.println("EL PRECIO FORMATEADO ES: "+precio);
            //TOMO LA FECHA DE AHORA Y LA ENVIO AUTOMATICAMENTE
            this.ps.setInt(1, objDetalle.getCantidad());
            this.ps.setFloat(2, precio);
            this.ps.setInt(3, idVenta);
            this.ps.setInt(4, objDetalle.getDetalleCompra().getIdDetalleCompra());
            int respuesta=this.ps.executeUpdate();
            
            if (respuesta>0) {
                resultado=true;
            }else{
                resultado=false;
            }
            this.conexion.cerrarConexiones();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CompraDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }
    public int actualizarExistencia(DetalleVenta objDetalle){
        
            int resultado=0;
            try {
            this.conexion=new Conexion();
            this.accesoDB=conexion.getConexion();
            this.ps=this.accesoDB.prepareStatement(ACTUALIZAR_EXISTENCIA);
            int nvExistencia=objDetalle.getDetalleCompra().getExistencia()-objDetalle.getCantidad();
            this.ps.setInt(1,nvExistencia);
            this.ps.setInt(2, objDetalle.getDetalleCompra().getIdDetalleCompra());
            resultado=this.ps.executeUpdate();
            this.ps.close();
            this.conexion.cerrarConexiones();
            
            
        } catch (SQLException ex) {
            Logger.getLogger(VentaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
            return resultado;
    }
    
}
