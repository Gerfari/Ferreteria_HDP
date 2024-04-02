/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ues.models.dao;

import com.ues.models.Compras;
import com.ues.models.Conexion;
import com.ues.models.Proveedor;
import com.ues.models.dtos.ComprasDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author herna
 */
public class CompraDAO {

    private Conexion conexion;
    private Connection accesoDB;
    private PreparedStatement ps;
    private ResultSet rs;

    public CompraDAO() {
        this.conexion = new Conexion();
    }

    private static String SELECT_ALL_COMPRAS = "SELECT cp.id_compra,cp.fecha_compra, cp.dui_empleado, ep.nombre_empleado, ep.apellido_empleado, pr.nombre_proveedor FROM compras cp INNER JOIN empleados ep on cp.dui_empleado=ep.dui_empleado INNER JOIN proveedor pr on cp.id_proveedor=pr.id_proveedor";
    private static String OBTENER_PROVEEDORES="SELECT id_proveedor,nombre_proveedor from proveedor WHERE estado_proveedor=true";
    private static String INSERTAR_COMPRA="insert INTO compras(fecha_compra,dui_empleado,id_proveedor) VALUES(?,?,?);";
    
    public ArrayList<ComprasDTO> showCompras(){
        ArrayList<ComprasDTO> lsCompras = new ArrayList<>();
        try {
            this.conexion = new Conexion();
            this.accesoDB=conexion.getConexion();
            this.ps=accesoDB.prepareStatement(SELECT_ALL_COMPRAS);
            this.rs=ps.executeQuery();
            while(rs.next()){
                ComprasDTO dto = new ComprasDTO();
                dto.setIdCompra(rs.getInt("id_compra"));
                dto.setFechaCompra(rs.getDate("fecha_compra"));
                dto.setDuiEmpleado(rs.getString("dui_empleado"));
                dto.setEmpleado(rs.getString("nombre_empleado")+" "+rs.getString("apellido_empleado"));
                dto.setProveedor(rs.getString("nombre_proveedor"));
                lsCompras.add(dto);
            }
            this.conexion.cerrarConexiones();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lsCompras;
    }
    
    public ArrayList<Proveedor> comboProveedores(){
        ArrayList<Proveedor> lsProveedores=new ArrayList<>();
        try {
            this.conexion=new Conexion();
            this.accesoDB=conexion.getConexion();
            this.ps=accesoDB.prepareStatement(OBTENER_PROVEEDORES);
            this.rs=ps.executeQuery();
            while(rs.next()){
                Proveedor obj = new Proveedor();
                obj.setIdProveedor(rs.getInt("id_proveedor"));
                obj.setNombreProveedor(rs.getString("nombre_proveedor"));
                lsProveedores.add(obj);
            }
            this.conexion.cerrarConexiones();
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lsProveedores;
        
    }
    
    public boolean insertCompra(Compras compra){
        boolean resultado=false;
        this.conexion=new Conexion();
        this.accesoDB=conexion.getConexion();
        try {
            this.ps=accesoDB.prepareStatement(INSERTAR_COMPRA);
            this.ps.setDate(1, new java.sql.Date(compra.getFechaCompra().getTime()));
            this.ps.setString(2, compra.getEmpleado().getDuiEmpleado());
            this.ps.setInt(3, compra.getProveedor().getIdProveedor());
            
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
    
    
}