/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ues.models.dao;

import com.ues.models.Categorias;
import com.ues.models.Conexion;
import com.ues.models.Productos;
import com.ues.models.dtos.CantidadPorCategoriaDTO;
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
public class CategoriasDAO {
    private Conexion conexion;
    private Connection accesoDB;
    private PreparedStatement ps;
    private ResultSet rs;
    
    private static final String DISPONIBLES_POR_CATEGORIA = "SELECT c.categoria, SUM(dc.existencia) AS cantidad_disponible FROM Categorias c INNER JOIN Productos p ON c.id_categoria = p.id_categoria INNER JOIN Detalle_Compras dc ON p.id_producto = dc.id_producto GROUP BY c.categoria order by cantidad_disponible desc";
    
    public CategoriasDAO() throws SQLException, ClassNotFoundException{
        this.conexion = new Conexion();
    }
    
    //METODO PARA OBTENER LA QUERY 6
    //CANTIDAD DE PRODUCTOS DISPONIBLES POR CATEGORIA
    public ArrayList<CantidadPorCategoriaDTO> selectDisponiblesCategoria(){
        ArrayList<CantidadPorCategoriaDTO> cantidadList = new ArrayList<>();
        
        
        
        try {
            this.accesoDB=this.conexion.getConexion();
            this.ps=this.accesoDB.prepareStatement(DISPONIBLES_POR_CATEGORIA);
            this.rs=this.ps.executeQuery();
            
            while(this.rs.next()){
                CantidadPorCategoriaDTO canDTO = new CantidadPorCategoriaDTO();
                canDTO.setNombreCategoria(rs.getString("categoria"));
                canDTO.setCantidadCategoria(rs.getInt("cantidad_disponible"));
                cantidadList.add(canDTO);
            }
                    
                    this.conexion.cerrarConexiones();
                    
        } catch (SQLException ex) {
            Logger.getLogger(CategoriasDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cantidadList;
    }
    
}
