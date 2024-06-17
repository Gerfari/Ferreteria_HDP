
package com.ues.models.dao;

import com.ues.models.Empleados;
import com.ues.models.Roles;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginDao {
   private static final String URL = "jdbc:postgresql://localhost:5432/Ferreteria_HDP";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    public Empleados verificarCredenciales(String correo, String contraseña) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean autenticado = false;
        Empleados emp=null;
        try {
           
            Class.forName("org.postgresql.Driver"); 
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

           
            String sql = "SELECT em.*,rl.rol FROM empleados em INNER JOIN roles rl on em.id_rol=rl.id_rol WHERE em.correo = ? AND em.contraseña = ? AND em.estado_empleado=true";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, correo);
            preparedStatement.setString(2, contraseña);
            
            
            
            resultSet = preparedStatement.executeQuery();
            
            Roles rol = new Roles();
            while(resultSet.next()){
                emp=new Empleados();
                emp.setDuiEmpleado(resultSet.getString("dui_empleado"));
                emp.setNombreEmpleado(resultSet.getString("nombre_empleado"));
                emp.setApellidoEmpleado(resultSet.getString("apellido_empleado"));
                rol.setIdRol(resultSet.getInt("id_rol"));
                rol.setRol(resultSet.getString("rol"));
                emp.setRol(rol);
                emp.setCorreo(correo);
                emp.setContraseña(contraseña);
            }
            
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return emp;
    }
}
