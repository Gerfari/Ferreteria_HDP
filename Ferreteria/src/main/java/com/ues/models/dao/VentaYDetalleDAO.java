package com.ues.models.dao;

import com.ues.models.dtos.DetalleVentaDTO;
import com.ues.models.dtos.VentaDTO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VentaYDetalleDAO {

    private Connection connection;

    public VentaYDetalleDAO() {
        // Establecer la conexión con la base de datos
        try {
            Class.forName("org.postgresql.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Ferreteria_HDP", "postgres", "root");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<VentaDTO> getVentas() {
        ArrayList<VentaDTO> ventas = new ArrayList<>();
      String query = "SELECT "
            + "v.id_venta AS idVenta, "
            + "v.fecha_venta AS fechaVenta, "
            + "CONCAT(emp.nombre_empleado, ' ', emp.apellido_empleado) AS empleado, "
            + "CONCAT(cli.nombre_cliente, ' ', cli.apellido_cliente) AS cliente "
            + "FROM ventas v "
            + "INNER JOIN empleados emp ON emp.dui_empleado = v.dui_empleado "
            + "INNER JOIN clientes cli ON cli.dui_cliente = v.dui_cliente "
            + "GROUP BY v.id_venta, v.fecha_venta, emp.nombre_empleado, emp.apellido_empleado, cli.nombre_cliente, cli.apellido_cliente "
            + "ORDER BY v.fecha_venta DESC, v.id_venta DESC";


        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                VentaDTO venta = new VentaDTO();
                venta.setIdVenta(resultSet.getInt("idVenta"));
                venta.setFechaVenta(resultSet.getDate("fechaVenta"));
                venta.setEmpleado(resultSet.getString("empleado"));
                venta.setCliente(resultSet.getString("cliente"));
                ventas.add(venta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ventas;
    }

    public ArrayList<DetalleVentaDTO> getDetallesVentaPorId(int idVenta) {
        ArrayList<DetalleVentaDTO> detallesVenta = new ArrayList<>();
        String query = "SELECT "
                + "pr.nombre_producto AS producto, "
                + "CONCAT(emp.nombre_empleado, ' ', emp.apellido_empleado) AS empleado, "
                + "CONCAT(cli.nombre_cliente, ' ', cli.apellido_cliente) AS cliente, "
                + "v.fecha_venta AS fechaVenta, "
                + "dv.cantidad, "
                + "dv.precio_venta AS precio, "
                + "(dv.cantidad * dv.precio_venta) AS total "
                + "FROM detalle_ventas dv "
                + "INNER JOIN ventas v ON dv.id_venta = v.id_venta "
                + "INNER JOIN detalle_compras dc ON dv.id_detalle_compra = dc.id_detalle_compra "
                + "INNER JOIN productos pr ON dc.id_producto = pr.id_producto "
                + "INNER JOIN empleados emp ON emp.dui_empleado = v.dui_empleado "
                + "INNER JOIN clientes cli ON cli.dui_cliente = v.dui_cliente "
                + "WHERE v.id_venta = ? "
                + "ORDER BY total DESC";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idVenta);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                DetalleVentaDTO detalle = new DetalleVentaDTO();
                detalle.setProducto(resultSet.getString("producto"));
                detalle.setEmpleado(resultSet.getString("empleado"));
                detalle.setCliente(resultSet.getString("cliente"));
                detalle.setFechaVenta(resultSet.getDate("fechaVenta"));
                detalle.setCantidad(resultSet.getInt("cantidad"));
                detalle.setPrecio(resultSet.getFloat("precio"));
                detalle.setTotal(resultSet.getFloat("total"));
                detallesVenta.add(detalle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detallesVenta;
    }
}
