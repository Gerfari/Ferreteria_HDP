/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ues.models.dao;

import com.ues.models.Conexion;
import com.ues.models.dtos.prod_VendidosDTO;
import com.ues.models.dtos.proveedorDemandadoDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Emerson
 */
public class proveedorDemandadoDAO {
    
    private Conexion conexion;
    private Connection accesoDB;
    private PreparedStatement ps;
    private ResultSet rs;
    
    private static final String PROVEEDOR_DEMANDADO = "SELECT \n" +
"    pr.id_proveedor,\n" +
"    pr.nombre_proveedor,\n" +
"    prod.id_producto,\n" +
"    prod.nombre_producto,\n" +
"    SUM(dv.cantidad * dv.precio_venta) AS total_ventas\n" +
"FROM \n" +
"    ventas v\n" +
"    INNER JOIN detalle_ventas dv ON v.id_venta = dv.id_venta\n" +
"    INNER JOIN detalle_compras dc ON dv.id_detalle_compra = dc.id_detalle_compra\n" +
"    INNER JOIN compras c ON dc.id_compra = c.id_compra\n" +
"    INNER JOIN proveedor pr ON c.id_proveedor = pr.id_proveedor\n" +
"    INNER JOIN productos prod ON dc.id_producto = prod.id_producto\n" +
"GROUP BY \n" +
"    pr.id_proveedor, pr.nombre_proveedor, prod.id_producto, prod.nombre_producto\n" +
"ORDER BY \n" +
"    total_ventas DESC;";
    
    public proveedorDemandadoDAO() throws SQLException, ClassNotFoundException{
        this.conexion = new Conexion();
    }
 
    public ArrayList<proveedorDemandadoDTO> selectDisponiblesCategoria(){
        ArrayList<proveedorDemandadoDTO> cantidadList = new ArrayList<>();
        
        
        
        try {
            this.accesoDB=this.conexion.getConexion();
            this.ps=this.accesoDB.prepareStatement(PROVEEDOR_DEMANDADO);
            this.rs=this.ps.executeQuery();
            
            while(this.rs.next()){
                proveedorDemandadoDTO caDTO = new proveedorDemandadoDTO();
                caDTO.setId_proveedor(rs.getInt("id_proveedor"));
                caDTO.setNombre_proveedor(rs.getString("nombre_proveedor"));
                caDTO.setId_producto(rs.getInt("id_producto"));
                caDTO.setNombre_producto(rs.getString("nombre_producto"));
                caDTO.setTotal_ventas(rs.getInt("total_ventas"));
                cantidadList.add(caDTO);
            }
                    
                    this.conexion.cerrarConexiones();
                    
        } catch (SQLException ex) {
            Logger.getLogger(prod_VendidosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cantidadList;
    }    
}
