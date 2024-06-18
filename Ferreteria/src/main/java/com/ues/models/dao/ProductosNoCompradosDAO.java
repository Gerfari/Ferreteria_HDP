package com.ues.models.dao;

import com.ues.models.Conexion;
import com.ues.models.Productos;
import com.ues.models.DetalleCompra;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductosNoCompradosDAO {

    public List<Productos> obtenerProductosNoComprados() {
        List<Productos> productosNoComprados = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Conexion conexion = new Conexion();

        try {
            connection = conexion.getConexion();
            String query = "SELECT p.id_producto, p.nombre_producto, dc.existencia, dc.precio " +
                           "FROM Productos p " +
                           "LEFT JOIN Detalle_Compras dc ON p.id_producto = dc.id_producto " +
                           "LEFT JOIN Detalle_Ventas dv ON dc.id_detalle_compra = dv.id_detalle_compra " +
                           "WHERE dv.id_detalle_venta IS NULL";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Productos producto = new Productos();
                producto.setIdProducto(resultSet.getInt("id_producto"));
                producto.setNombreProducto(resultSet.getString("nombre_producto"));

                DetalleCompra detalle = new DetalleCompra();
                detalle.setExistencia(resultSet.getInt("existencia"));
                detalle.setPrecio(resultSet.getFloat("precio"));

                ArrayList<DetalleCompra> detallesCompra = new ArrayList<>();
                detallesCompra.add(detalle);
                producto.setDetallesCompra(detallesCompra);

                productosNoComprados.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cierra los recursos (agrega un método para esto si no existe)
        }

        return productosNoComprados;
    }
}
