/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ues.models.dao;

import com.ues.models.Conexion;
import com.ues.models.dtos.prod_VendidosDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Emerson
 */
public class prod_VendidosDAO {


 private Conexion conexion;
    private Connection accesoDB;
    private PreparedStatement ps;
    private ResultSet rs;
    
    private static final String PRODUCTOS_MAS_VENDIDOS = "SELECT p.nombre_producto, SUM(dv.cantidad) AS total_vendido FROM Productos p INNER JOIN Detalle_Compras dc ON p.id_producto = dc.id_producto INNER JOIN Detalle_Ventas dv ON dc.id_detalle_compra = dv.id_detalle_compra GROUP BY p.nombre_producto ORDER BY total_vendido DESC";
    
    public prod_VendidosDAO() throws SQLException, ClassNotFoundException{
        this.conexion = new Conexion();
    }
 
    public ArrayList<prod_VendidosDTO> selectDisponiblesCategoria(){
        ArrayList<prod_VendidosDTO> cantidadList = new ArrayList<>();
        
        
        
        try {
            this.accesoDB=this.conexion.getConexion();
            this.ps=this.accesoDB.prepareStatement(PRODUCTOS_MAS_VENDIDOS);
            this.rs=this.ps.executeQuery();
            
            while(this.rs.next()){
                prod_VendidosDTO canDTO = new prod_VendidosDTO();
                canDTO.setNombreProducto(rs.getString("nombre_producto"));
                canDTO.setTotalVendido(rs.getInt("total_vendido"));
                cantidadList.add(canDTO);
            }
                    
                    this.conexion.cerrarConexiones();
                    
        } catch (SQLException ex) {
            Logger.getLogger(prod_VendidosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cantidadList;
    }    
}