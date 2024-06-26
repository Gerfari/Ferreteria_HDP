package com.ues.models.dao;

import com.ues.models.Conexion;
import com.ues.models.Empleados;
import com.ues.models.Roles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class EmpleadoDAO {
    private Conexion conexion;
    private Connection accesoDB;
    private PreparedStatement ps;
    private ResultSet rs;

    private static final String INSERT_EMPLEADO = "INSERT INTO empleados (dui_empleado, nombre_empleado, apellido_empleado, fecha_nacimiento, direccion_empleado, telefono_empleado, genero, estado_empleado, id_rol) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_EMPLEADO = "UPDATE empleados SET nombre_empleado = ?, apellido_empleado = ?, fecha_nacimiento = ?, direccion_empleado = ?, telefono_empleado = ?, genero = ?, estado_empleado = ?, id_rol = ? WHERE dui_empleado = ?";
    //private static final String DELETE_EMPLEADO = "UPDATE empleados SET estado_empleado = false WHERE dui_empleado = ?";
    private static final String DELETE_EMPLEADO = "UPDATE empleados SET estado_empleado = false WHERE dui_empleado = ?";
    private static final String SELECT_EMPLEADO_BY_ID = "SELECT e.*, r.* FROM empleados e INNER JOIN roles r ON e.id_rol = r.id_rol WHERE e.dui_empleado = ?";
   // private static final String SELECT_ALL_EMPLEADOS = "SELECT e.*, r.* FROM empleados e INNER JOIN roles r ON e.id_rol = r.id_rol";
     private static final String SELECT_ALL_EMPLEADOS = "SELECT e.*, r.* FROM empleados e INNER JOIN roles r ON e.id_rol = r.id_rol WHERE e.estado_empleado = true";

    public EmpleadoDAO() throws SQLException, ClassNotFoundException {
        this.conexion = new Conexion();
    }

    public String insertEmpleado(Empleados empleado) {
        String resultado = "";

        try {
            this.accesoDB = this.conexion.getConexion();
            this.ps = this.accesoDB.prepareStatement(INSERT_EMPLEADO);
            this.ps.setString(1, empleado.getDuiEmpleado());
            this.ps.setString(2, empleado.getNombreEmpleado());
            this.ps.setString(3, empleado.getApellidoEmpleado());
            this.ps.setDate(4, new java.sql.Date(empleado.getFechaNacimiento().getTime()));
            this.ps.setString(5, empleado.getDireccionEmpleado());
            this.ps.setString(6, empleado.getTelefonoEmpleado());
            this.ps.setString(7, String.valueOf(empleado.getGenero()));
            this.ps.setBoolean(8, empleado.isEstadoEmpleado());
            /*revisar a la hora de ingresar*/
            this.ps.setInt(9, empleado.getRol().getIdRol());

            int filasAfectadas = this.ps.executeUpdate();

            if (filasAfectadas > 0) {
                resultado = "exito";
            } else {
                resultado = "error_insertar_empleado";
            }

            this.conexion.cerrarConexiones();
        } catch (SQLException e) {
            resultado = "error_excepcion";
            e.printStackTrace();
        }

        return resultado;
    }

    public ArrayList<Empleados> selectAllEmpleados() {
        ArrayList<Empleados> empleadosList = new ArrayList<>();

        try {
            this.accesoDB = this.conexion.getConexion();
            this.ps = this.accesoDB.prepareStatement(SELECT_ALL_EMPLEADOS);
            this.rs = this.ps.executeQuery();

            while (this.rs.next()) {
                Empleados empleado = new Empleados();
                empleado.setDuiEmpleado(rs.getString("dui_empleado"));
                empleado.setNombreEmpleado(rs.getString("nombre_empleado"));
                empleado.setApellidoEmpleado(rs.getString("apellido_empleado"));
                empleado.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                empleado.setDireccionEmpleado(rs.getString("direccion_empleado"));
                empleado.setTelefonoEmpleado(rs.getString("telefono_empleado"));
                empleado.setGenero(rs.getString("genero").charAt(0));
                empleado.setEstadoEmpleado(rs.getBoolean("estado_empleado"));
                
                Roles rol = new Roles();
                rol.setIdRol(rs.getInt("id_rol"));
                rol.setRol(rs.getString("rol"));
                empleado.setRol(rol);
                
                empleadosList.add(empleado);
            }

            this.conexion.cerrarConexiones();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return empleadosList;
    }

    public String updateEmpleado(Empleados empleado, String duiEmpleado) {
        String resultado = "";

        try {
            this.accesoDB = this.conexion.getConexion();
            this.ps = this.accesoDB.prepareStatement(UPDATE_EMPLEADO);
            this.ps.setString(1, empleado.getNombreEmpleado());
            this.ps.setString(2, empleado.getApellidoEmpleado());
            this.ps.setDate(3, new java.sql.Date(empleado.getFechaNacimiento().getTime()));
            this.ps.setString(4, empleado.getDireccionEmpleado());
            this.ps.setString(5, empleado.getTelefonoEmpleado());
            this.ps.setString(6, String.valueOf(empleado.getGenero()));
            this.ps.setBoolean(7, empleado.isEstadoEmpleado());
            /*revisar*/
            this.ps.setInt(8, empleado.getRol().getIdRol());
            this.ps.setString(9, duiEmpleado);

            int filasAfectadas = this.ps.executeUpdate();

            if (filasAfectadas > 0) {
                resultado = "exito";
            } else {
                resultado = "error_actualizar_empleado";
            }

            this.conexion.cerrarConexiones();
        } catch (SQLException e) {
            resultado = "error_excepcion";
            e.printStackTrace();
        }

        return resultado;
    }

    public Empleados findById(String duiEmpleado) {
        Empleados empleado = null;

        try {
            this.accesoDB = this.conexion.getConexion();
            this.ps = this.accesoDB.prepareStatement(SELECT_EMPLEADO_BY_ID);
            this.ps.setString(1, duiEmpleado);
            this.rs = this.ps.executeQuery();

            if (rs.next()) {
                empleado = new Empleados();
                empleado.setDuiEmpleado(rs.getString("dui_empleado"));
                empleado.setNombreEmpleado(rs.getString("nombre_empleado"));
                empleado.setApellidoEmpleado(rs.getString("apellido_empleado"));
                empleado.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                empleado.setDireccionEmpleado(rs.getString("direccion_empleado"));
                empleado.setTelefonoEmpleado(rs.getString("telefono_empleado"));
                empleado.setGenero(rs.getString("genero").charAt(0));
                empleado.setEstadoEmpleado(rs.getBoolean("estado_empleado"));
                
                Roles rol = new Roles();
                rol.setIdRol(rs.getInt("id_rol"));
                rol.setRol(rs.getString("rol"));
                empleado.setRol(rol);
            }

            this.conexion.cerrarConexiones();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return empleado;
    }

    public String deleteEmpleado(String duiEmpleado) {
    String resultado = "";

    try {
        this.accesoDB = this.conexion.getConexion();
        this.ps = this.accesoDB.prepareStatement(DELETE_EMPLEADO);
        this.ps.setString(1, duiEmpleado);

        int filasAfectadas = this.ps.executeUpdate();

        if (filasAfectadas > 0) {
            resultado = "exito";
        } else {
            resultado = "error_actualizar_estado_empleado";
        }

        this.conexion.cerrarConexiones();
    } catch (SQLException e) {
        resultado = "error_excepcion";
        e.printStackTrace();
    }

    return resultado;
}
 
}
