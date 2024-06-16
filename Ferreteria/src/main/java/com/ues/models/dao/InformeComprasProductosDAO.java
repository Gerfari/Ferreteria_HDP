package com.ues.models.dao;

import com.ues.models.Conexion;
import com.ues.models.dtos.InformeComprasProductosDTO;
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
public class InformeComprasProductosDAO {

    private Conexion conexion;
    private Connection accesoDB;
    private PreparedStatement ps;
    private ResultSet rs;

    private static final String INFORME_COMPRAS_PRODUCTOS = "SELECT P.nombre_producto, C.categoria, PR.nombre_proveedor, "
            + "DC.fecha_detalle_compra, DC.cantidad, DC.precio,  DC.cantidad * DC.precio AS total_compra, DC.existencia "
            + "FROM Detalle_Compras DC INNER JOIN Productos P ON DC.id_producto = P.id_producto "
            + "INNER JOIN Categorias C ON P.id_categoria = C.id_categoria "
            + "INNER JOIN Compras CO ON DC.id_compra = CO.id_compra "
            + "INNER JOIN Proveedor PR ON CO.id_proveedor = PR.id_proveedor "
            + "ORDER BY DC.fecha_detalle_compra ASC;";

    public InformeComprasProductosDAO() throws SQLException, ClassNotFoundException {
        this.conexion = new Conexion();
    }

    public ArrayList<InformeComprasProductosDTO> selectAllProveedorProducto() {
        ArrayList<InformeComprasProductosDTO> ListProvProd = new ArrayList<>();
        try {
            this.accesoDB = this.conexion.getConexion();
            this.ps = this.accesoDB.prepareStatement(INFORME_COMPRAS_PRODUCTOS);
            this.rs = this.ps.executeQuery();

            while (this.rs.next()) {
                InformeComprasProductosDTO ComprasProd = new InformeComprasProductosDTO();
                ComprasProd.setProducto(rs.getString("nombre_producto"));
                ComprasProd.setCategoria(rs.getString("categoria"));
                ComprasProd.setProveedor(rs.getString("nombre_proveedor"));
                ComprasProd.setFecha_compra(rs.getDate("fecha_detalle_compra"));
                ComprasProd.setCantidad_compra(rs.getInt("cantidad"));
                ComprasProd.setPrecio_producto(rs.getInt("precio"));
                ComprasProd.setTotal_compra(rs.getDouble("total_compra"));
                ComprasProd.setExistencia(rs.getInt("existencia"));
                
                ListProvProd.add(ComprasProd);
            }
            this.conexion.cerrarConexiones();

        } catch (SQLException ex) {
            Logger.getLogger(CategoriasDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ListProvProd;
    }
}