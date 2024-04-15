package com.ues.models.dao;

import com.ues.models.Conexion;
import com.ues.models.dtos.ListaProveedorProductoDTO;
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
public class ListaProvProdDAO {

    private Conexion conexion;
    private Connection accesoDB;
    private PreparedStatement ps;
    private ResultSet rs;

    private static final String ListaProveedorProducto = "SELECT pr.nombre_proveedor, p.nombre_producto FROM Proveedor pr INNER JOIN Compras c ON pr.id_proveedor = c.id_proveedor INNER JOIN Detalle_Compras dc ON c.id_compra = dc.id_compra INNER JOIN Productos p ON dc.id_producto = p.id_producto;";

    public ListaProvProdDAO() throws SQLException, ClassNotFoundException {
        this.conexion = new Conexion();
    }

    public ArrayList<ListaProveedorProductoDTO> selectAllProveedorProducto() {
        ArrayList<ListaProveedorProductoDTO> ListProvProd = new ArrayList<>();
        try {
            this.accesoDB = this.conexion.getConexion();
            this.ps = this.accesoDB.prepareStatement(ListaProveedorProducto);
            this.rs = this.ps.executeQuery();

            while (this.rs.next()) {
                ListaProveedorProductoDTO ProvProd = new ListaProveedorProductoDTO();
                ProvProd.setProveedor(rs.getString("nombre_proveedor"));
                ProvProd.setProducto(rs.getString("nombre_producto"));
                ListProvProd.add(ProvProd);
            }
            this.conexion.cerrarConexiones();

        } catch (SQLException ex) {
            Logger.getLogger(CategoriasDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ListProvProd;
    }
}