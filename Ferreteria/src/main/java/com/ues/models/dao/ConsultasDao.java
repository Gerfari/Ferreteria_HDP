package com.ues.models.dao;

import com.ues.models.dtos.ClientesCompras;
import com.ues.models.dtos.ReporteVentas;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Ferreteria_HDP", "postgres", "root");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   /* public ArrayList<ProductoCatExis> getAllProductos() {
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
    }*/

   /* public ArrayList<ClienteTotal> getAllCliente() {
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
    }*/

    /*public ArrayList<EmpleadoVentas> getAllEmpleadoVentas() {
        ArrayList<EmpleadoVentas> empleadosVentas = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT e.nombre_empleado as nombre, e.apellido_empleado as apellido, COUNT(DISTINCT v.id_venta) AS cantidadVentas, SUM(dv.cantidad * dv.precio_venta) AS monto FROM Empleados e JOIN Ventas v ON e.dui_empleado = v.dui_empleado JOIN Detalle_Ventas dv ON v.id_venta = dv.id_venta GROUP BY e.nombre_empleado, e.apellido_empleado");
            while (resultSet.next()) {
                EmpleadoVentas empleadoVentas = new EmpleadoVentas();
                empleadoVentas.setNombre(resultSet.getString("nombre"));
                empleadoVentas.setApellido(resultSet.getString("apellido"));
                empleadoVentas.setCantidadVentas(resultSet.getInt("cantidadVentas"));
                empleadoVentas.setMonto(resultSet.getDouble("monto"));

                empleadosVentas.add(empleadoVentas);
            }
            // Cerrando recursos
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Maneja la excepción de alguna manera adecuada según tu aplicación
        }
        return empleadosVentas;
    }*/

    public ArrayList<ClientesCompras> getComprasPorCliente(String duiCliente) {
        ArrayList<ClientesCompras> comprasCliente = new ArrayList<>();
        try {
          
            String query = "SELECT "
                    + " CONCAT(c.apellido_cliente, ' ', c.nombre_cliente) AS nombreCliente, "
                    + "e.nombre_empleado AS nombreEmpleado, "
                    + "p.nombre_producto AS producto, "
                    + "dv.precio_venta AS precio, "
                    + "dc.fecha_detalle_compra AS fecha "
                    + "FROM "
                    + "Clientes c "
                    + "JOIN "
                    + "Ventas v ON c.dui_cliente = v.dui_cliente "
                    + "JOIN "
                    + "Detalle_Ventas dv ON v.id_venta = dv.id_venta "
                    + "JOIN "
                    + "Detalle_Compras dc ON dv.id_detalle_compra = dc.id_detalle_compra "
                    + "JOIN "
                    + "Productos p ON dc.id_producto = p.id_producto "
                    + "JOIN "
                    + "Empleados e ON v.dui_empleado = e.dui_empleado "
                    + "WHERE "
                    + "c.dui_cliente = ?"
                    + "ORDER BY fecha DESC";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, duiCliente);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ClientesCompras compra = new ClientesCompras();
                compra.setNombreCliente(resultSet.getString("nombreCliente"));
                compra.setNombreEmpleado(resultSet.getString("nombreEmpleado"));
                compra.setProducto(resultSet.getString("producto"));
                compra.setPrecio(resultSet.getDouble("precio"));
                compra.setFecha(resultSet.getDate("fecha"));
                comprasCliente.add(compra);
            }
            
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
          
        }
        return comprasCliente;
    }
    
    public ArrayList<ReporteVentas> getReporteVentas(Date fechaInicio, Date fechaFin) {
        ArrayList<ReporteVentas> reporteVentas = new ArrayList<>();
        try {
            String query = "SELECT "
                    + " c.nombre_cliente || ' ' || c.apellido_cliente AS nombreCliente, "
                    + " e.nombre_empleado || ' ' || e.apellido_empleado AS nombreEmpleado, "
                    + " p.nombre_producto AS producto, "
                    + " dv.cantidad, "
                    + " dv.precio_venta AS precio, "
                    + " dv.cantidad * dv.precio_venta AS montoTotal, "
                    + " v.fecha_venta AS fecha "
                    + "FROM "
                    + " Ventas v "
                    + "JOIN "
                    + " Clientes c ON v.dui_cliente = c.dui_cliente "
                    + "JOIN "
                    + " Empleados e ON v.dui_empleado = e.dui_empleado "
                    + "JOIN "
                    + " Detalle_Ventas dv ON v.id_venta = dv.id_venta "
                    + "JOIN "
                    + " Detalle_Compras dc ON dv.id_detalle_compra = dc.id_detalle_compra "
                    + "JOIN "
                    + " Productos p ON dc.id_producto = p.id_producto "
                    + "WHERE "
                    + " v.fecha_venta BETWEEN ? AND ? "
                    + "ORDER BY v.fecha_venta";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDate(1, fechaInicio);
            statement.setDate(2, fechaFin);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ReporteVentas reporte = new ReporteVentas();
                reporte.setNombreCliente(resultSet.getString("nombreCliente"));
                reporte.setNombreEmpleado(resultSet.getString("nombreEmpleado"));
                reporte.setProducto(resultSet.getString("producto"));
                reporte.setCantidad(resultSet.getInt("cantidad"));
                reporte.setPrecio(resultSet.getDouble("precio"));
                reporte.setMonto(resultSet.getDouble("montoTotal"));
                reporte.setFecha(resultSet.getDate("fecha"));
                reporteVentas.add(reporte);
            }
            
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reporteVentas;
    }
}
