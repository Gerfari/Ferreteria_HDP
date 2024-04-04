package com.ues.models.dao;

import com.ues.models.Conexion;
import com.ues.models.dtos.ProductosDisponiblesDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kevin
 */
public class ProductosDisponiblesDAO {

    private Conexion conexion;
    private Connection accesoDB;
    private PreparedStatement ps;
    private ResultSet rs;

    private static final String ProductosDisponibles = "SELECT p.nombre_producto, SUM(dc.existencia) AS cantidad_disponible FROM Productos p INNER JOIN Detalle_Compras dc ON p.id_producto = dc.id_producto GROUP BY p.nombre_producto;";

    public ProductosDisponiblesDAO() {
        this.conexion = new Conexion();
    }

    public ArrayList<ProductosDisponiblesDTO> selectAllProductoDisponible() {
        ArrayList<ProductosDisponiblesDTO> ListProductos = new ArrayList<>();
        try {
            this.accesoDB = this.conexion.getConexion();
            this.ps = this.accesoDB.prepareStatement(ProductosDisponibles);
            this.rs = this.ps.executeQuery();

            while (this.rs.next()) {
                ProductosDisponiblesDTO producto = new ProductosDisponiblesDTO();
                producto.setProducto(rs.getString("nombre_producto"));
                producto.setCantidadDisponible(rs.getInt("cantidad_disponible"));
                ListProductos.add(producto);
            }
            this.conexion.cerrarConexiones();

        } catch (SQLException ex) {
            Logger.getLogger(CategoriasDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ListProductos;
    }

}
