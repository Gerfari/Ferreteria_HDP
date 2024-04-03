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

/**
 *
 * @author Emerson
 */
public class prod_VendidosDAO {
    private Conexion conexion;
    private Connection accesoDB;
    private PreparedStatement ps;
    private ResultSet rs;

    public prod_VendidosDAO() {
        this.conexion = new Conexion(); 
    }

    public List<prod_VendidosDTO> obtenerProductosMasVendidos() {
        
        List<prod_VendidosDTO> productos = new ArrayList<>();
        String consulta = "SELECT p.nombre_producto, SUM(dv.cantidad) AS total_vendido " +
                          "FROM Productos p " +
                          "INNER JOIN Detalle_Compras dc ON p.id_producto = dc.id_producto " +
                          "INNER JOIN Detalle_Ventas dv ON dc.id_detalle_compra = dv.id_detalle_compra " +
                          "GROUP BY p.nombre_producto " +
                          "ORDER BY total_vendido DESC";
        

        try (PreparedStatement ps = conexion.getConexion().prepareStatement(consulta);
             ResultSet rs = ps.executeQuery()) {
            

            while (rs.next()) {
                String nombreProducto = rs.getString("nombre_producto");
                int totalVendido = rs.getInt("total_vendido");
                prod_VendidosDTO producto = new prod_VendidosDTO(nombreProducto, totalVendido);
                productos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }
}