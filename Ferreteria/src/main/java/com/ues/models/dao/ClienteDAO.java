package com.ues.models.dao;
import java.sql.*;
import java.util.ArrayList;
import com.ues.models.Cliente;

public class ClienteDAO {
    private Connection connection;
    

    public ClienteDAO() {
        // Establecer la conexión con la base de datos
        try {
           
            Class.forName("org.postgresql.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ferreteria", "postgres", "root");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para agregar un nuevo cliente
    public void addCliente(Cliente cliente) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Clientes (dui_cliente, nombre_cliente, apellido_cliente, direccion_cliente, telefono_cliente, estado_cliente) VALUES (?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, cliente.getDuiCliente());
            preparedStatement.setString(2, cliente.getNombreCliente());
            preparedStatement.setString(3, cliente.getApellidoCliente());
            preparedStatement.setString(4, cliente.getDireccionCliente());
            preparedStatement.setString(5, cliente.getTelefonoCliente());
            preparedStatement.setBoolean(6, cliente.isEstadoCliente());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para actualizar un cliente existente
  // Método para actualizar un cliente existente
public void updateCliente(Cliente cliente) {
    try {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Clientes SET nombre_cliente = ?, apellido_cliente = ?, direccion_cliente = ?, telefono_cliente = ?, estado_cliente = ? WHERE dui_cliente = ?");
        preparedStatement.setString(1, cliente.getNombreCliente());
        preparedStatement.setString(2, cliente.getApellidoCliente());
        preparedStatement.setString(3, cliente.getDireccionCliente());
        preparedStatement.setString(4, cliente.getTelefonoCliente());
        preparedStatement.setBoolean(5, cliente.isEstadoCliente());
        preparedStatement.setString(6, cliente.getDuiCliente());
        int rowsUpdated = preparedStatement.executeUpdate(); 
        if (rowsUpdated > 0) {
            System.out.println("Cliente actualizado exitosamente.");
        } else {
            System.out.println("No se pudo encontrar el cliente para actualizar.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    // Método para eliminar un cliente
    public void deleteCliente(String duiCliente) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Clientes WHERE dui_cliente = ?");
            preparedStatement.setString(1, duiCliente);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para obtener todos los clientes
    public ArrayList<Cliente> getAllClientes() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Clientes");
            while (resultSet.next()) {
                Cliente cliente = new Cliente();
                cliente.setDuiCliente(resultSet.getString("dui_cliente"));
                cliente.setNombreCliente(resultSet.getString("nombre_cliente"));
                cliente.setApellidoCliente(resultSet.getString("apellido_cliente"));
                cliente.setDireccionCliente(resultSet.getString("direccion_cliente"));
                cliente.setTelefonoCliente(resultSet.getString("telefono_cliente"));
                cliente.setEstadoCliente(resultSet.getBoolean("estado_cliente"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }
}
