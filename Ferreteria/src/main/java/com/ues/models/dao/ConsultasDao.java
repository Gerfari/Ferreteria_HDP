
package com.ues.models.dao;

import com.ues.models.Cliente;
import com.ues.utils.ClienteTotal;
import com.ues.utils.ProductoCatExis;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class ConsultasDao {
    private Connection connection;
    

    public ConsultasDao() {
        // Establecer la conexión con la base de datos
        try {
           
            Class.forName("org.postgresql.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ferreteria", "postgres", "root");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<ProductoCatExis> getAllProductos() {
        ArrayList<ProductoCatExis> productos = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT  p.nombre_producto as nombre,p.descripcion, c.categoria,COALESCE(dc.existencia,0) AS existencia FROM productos p JOIN categorias c ON p.id_categoria = c.id_categoria LEFT JOIN detalle_compras dc on dc.id_producto=p.id_producto ORDER BY p.id_producto");
            while (resultSet.next()) {
                ProductoCatExis producto = new ProductoCatExis();
                producto.setNombre(resultSet.getString("nombre"));
                producto.setDescripcion(resultSet.getString("descripcion"));
                producto.setCategoria(resultSet.getString("categoria"));
                producto.setExistencia(resultSet.getInt("existencia"));
               
                
                productos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }
    
     public ArrayList<ClienteTotal> getAllCliente() {
        ArrayList<ClienteTotal> clientes = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT c.dui_cliente as dui, c.nombre_cliente as nombre, COUNT(dv.id_detalle_venta) AS cantidad, SUM(dv.cantidad * dv.precio_venta) AS monto FROM Ventas v INNER JOIN Detalle_Ventas dv ON v.id_venta = dv.id_venta INNER JOIN Clientes c ON v.dui_cliente = c.dui_cliente GROUP BY c.dui_cliente, c.nombre_cliente");
            while (resultSet.next()) {
                ClienteTotal cliente = new ClienteTotal();
                cliente.setDui(resultSet.getString("dui"));
                cliente.setNombre(resultSet.getString("nombre"));
                cliente.setCantidad(resultSet.getInt("cantidad"));
                cliente.setMonto(resultSet.getFloat("monto"));
                
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }
}
