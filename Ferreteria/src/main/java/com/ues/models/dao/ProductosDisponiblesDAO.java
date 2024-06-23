package com.ues.models.dao;

import com.ues.models.Conexion;
import com.ues.models.DetalleCompra;
import com.ues.models.Productos;
import com.ues.models.dtos.DetalleProductoDTO;
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
    private static final String SELECT_ALL_PRODUCTOS = "SELECT pr.id_producto,pr.nombre_producto,pr.descripcion from productos pr WHERE pr.estado_producto=true";
    private static final String PRODUCTO_SELECTED = "SELECT * from productos WHERE id_producto=?";
    private static final String DETALLECOMPRA_PRODUCTO = "SELECT dc.* , pr.nombre_producto from detalle_compras dc INNER JOIN productos pr ON pr.id_producto = dc.id_producto WHERE dc.id_detalle_compra=?";
    private static final String MOSTRAR_PRODUCTOS="SELECT  dc.id_detalle_compra, p.id_producto, p.nombre_producto, c.fecha_compra, dc.precio, dc.existencia  FROM  detalle_compras dc JOIN productos p ON dc.id_producto = p.id_producto JOIN compras c ON dc.id_compra = c.id_compra WHERE dc.existencia > 0 ORDER BY c.fecha_compra,p.nombre_producto"; 
    
    
    
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

    public ArrayList<Productos> selectAllProductos() {
        ArrayList<Productos> lsPro = new ArrayList<>();
        try {
            this.accesoDB = this.conexion.getConexion();
            this.ps = this.accesoDB.prepareStatement(SELECT_ALL_PRODUCTOS);
            this.rs = this.ps.executeQuery();

            while (this.rs.next()) {
                Productos pro = new Productos();
                pro.setIdProducto(rs.getInt("id_producto"));
                pro.setNombreProducto(rs.getString("nombre_producto"));
                pro.setDescripcion(rs.getString("descripcion"));
                lsPro.add(pro);
            }
            this.conexion.cerrarConexiones();
        } catch (SQLException ex) {
            Logger.getLogger(ProductosDisponiblesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lsPro;
    }

    public Productos findById(int quien) throws SQLException, ClassNotFoundException {
        Productos objPr = new Productos();
        try {
            this.accesoDB = this.conexion.getConexion();
            this.ps = this.accesoDB.prepareStatement(PRODUCTO_SELECTED);
            this.ps.setInt(1, quien);
            this.rs = ps.executeQuery();
            while (this.rs.next()) {

                objPr.setIdProducto(rs.getInt("id_producto"));
                objPr.setNombreProducto(rs.getString("nombre_producto"));
            }
            this.conexion.cerrarConexiones();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Debe retornar el objeto encontrado
        return objPr;
    }

    public DetalleCompra buscarProducto(int quien) throws SQLException, ClassNotFoundException {
        DetalleCompra objDc = new DetalleCompra();
        try {
            this.accesoDB = this.conexion.getConexion();
            this.ps = this.accesoDB.prepareStatement(DETALLECOMPRA_PRODUCTO);
            this.ps.setInt(1, quien);
            this.rs = ps.executeQuery();
            while (this.rs.next()) {

                Productos objPr = new Productos();
                objPr.setIdProducto(rs.getInt("id_producto"));
                objPr.setNombreProducto(rs.getString("nombre_producto"));
                objDc.setExistencia(rs.getInt("existencia"));
                objDc.setPrecio(rs.getFloat("precio"));
                objDc.setIdDetalleCompra(rs.getInt("id_detalle_compra"));
                objDc.setProducto(objPr);
            }
            this.conexion.cerrarConexiones();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Debe retornar el objeto encontrado
        return objDc;
    }

    public ArrayList<DetalleProductoDTO> ProductosVendidos(){
        ArrayList<DetalleProductoDTO> ListProductos = new ArrayList<>();
        try {
            this.accesoDB = this.conexion.getConexion();
            this.ps = this.accesoDB.prepareStatement(MOSTRAR_PRODUCTOS);
            this.rs = this.ps.executeQuery();

            while (this.rs.next()) {
                DetalleProductoDTO producto = new DetalleProductoDTO();
                producto.setId_detalle_compra(rs.getInt("id_detalle_compra"));
                producto.setNombre_producto(rs.getString("nombre_producto"));
                producto.setExistencia(rs.getInt("existencia"));
                producto.setFecha_compra(rs.getDate("fecha_compra"));
                producto.setPrecio(rs.getFloat("precio"));
                producto.setId_producto(rs.getInt("id_producto"));
                ListProductos.add(producto);
            }
            this.conexion.cerrarConexiones();

        } catch (SQLException ex) {
            Logger.getLogger(CategoriasDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ListProductos;
    }
}
