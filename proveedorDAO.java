package com.ues.models.dao;

import com.ues.models.Conexion;
import com.ues.models.Proveedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class proveedorDAO {
    private Conexion conexion;
    private Connection accesoDB;
    private PreparedStatement ps;
    private ResultSet rs;

    private static final String INSERT_PROVEEDOR = "INSERT INTO proveedor (nombre_proveedor, direccion_proveedor, telefono, estado_proveedor) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_PROVEEDOR = "UPDATE proveedor SET nombre_proveedor = ?, direccion_proveedor = ?, telefono = ?, estado_proveedor = ? WHERE id_proveedor = ?";
    private static final String DELETE_PROVEEDOR = "DELETE FROM proveedor WHERE id_proveedor = ?";
    private static final String SELECT_PROVEEDOR_BY_ID = "SELECT * FROM proveedor WHERE id_proveedor = ?";
    private static final String SELECT_ALL_PROVEEDORES = "SELECT * FROM proveedor";

    public proveedorDAO() throws SQLException, ClassNotFoundException {
        this.conexion = new Conexion();
    }

    public String insertProveedor(Proveedor proveedor) {
        String resultado = "";

        try {
            this.accesoDB = this.conexion.getConexion();
            this.ps = this.accesoDB.prepareStatement(INSERT_PROVEEDOR, Statement.RETURN_GENERATED_KEYS);
            this.ps.setString(1, proveedor.getNombreProveedor());
            this.ps.setString(2, proveedor.getDireccionProveedor());
            this.ps.setString(3, proveedor.getTelefono());
            this.ps.setBoolean(4, proveedor.isEstadoProveedor());

            int filasAfectadas = this.ps.executeUpdate();

            if (filasAfectadas > 0) {
                resultado = "exito";
            } else {
                resultado = "error_insertar_proveedor";
            }

            this.conexion.cerrarConexiones();
        } catch (SQLException e) {
            resultado = "error_excepcion";
            e.printStackTrace();
        }

        return resultado;
    }

    public List<Proveedor> selectAllProveedores() {
        List<Proveedor> proveedoresList = new ArrayList<>();

        try {
            this.accesoDB = this.conexion.getConexion();
            this.ps = this.accesoDB.prepareStatement(SELECT_ALL_PROVEEDORES);
            this.rs = this.ps.executeQuery();

            while (this.rs.next()) {
                Proveedor proveedor = new Proveedor();
                proveedor.setIdProveedor(rs.getInt("id_proveedor"));
                proveedor.setNombreProveedor(rs.getString("nombre_proveedor"));
                proveedor.setDireccionProveedor(rs.getString("direccion_proveedor"));
                proveedor.setTelefono(rs.getString("telefono"));
                proveedor.setEstadoProveedor(rs.getBoolean("estado_proveedor"));
                proveedoresList.add(proveedor);
            }

            this.conexion.cerrarConexiones();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return proveedoresList;
    }

    public String updateProveedor(Proveedor proveedor) {
        String resultado = "";

        try {
            this.accesoDB = this.conexion.getConexion();
            this.ps = this.accesoDB.prepareStatement(UPDATE_PROVEEDOR);
            this.ps.setString(1, proveedor.getNombreProveedor());
            this.ps.setString(2, proveedor.getDireccionProveedor());
            this.ps.setString(3, proveedor.getTelefono());
            this.ps.setBoolean(4, proveedor.isEstadoProveedor());
            this.ps.setInt(5, proveedor.getIdProveedor());

            int filasAfectadas = this.ps.executeUpdate();

            if (filasAfectadas > 0) {
                resultado = "exito";
            } else {
                resultado = "error_actualizar_proveedor";
            }

            this.conexion.cerrarConexiones();
        } catch (SQLException e) {
            resultado = "error_excepcion";
            e.printStackTrace();
        }

        return resultado;
    }

    public Proveedor findProveedorById(int id) {
        Proveedor proveedor = null;

        try {
            this.accesoDB = this.conexion.getConexion();
            this.ps = this.accesoDB.prepareStatement(SELECT_PROVEEDOR_BY_ID);
            this.ps.setInt(1, id);
            this.rs = this.ps.executeQuery();

            if (rs.next()) {
                proveedor = new Proveedor();
                proveedor.setIdProveedor(rs.getInt("id_proveedor"));
                proveedor.setNombreProveedor(rs.getString("nombre_proveedor"));
                proveedor.setDireccionProveedor(rs.getString("direccion_proveedor"));
                proveedor.setTelefono(rs.getString("telefono"));
                proveedor.setEstadoProveedor(rs.getBoolean("estado_proveedor"));
            }

            this.conexion.cerrarConexiones();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return proveedor;
    }

    public String deleteProveedor(int id) {
        String resultado = "";

        try {
            this.accesoDB = this.conexion.getConexion();
            this.ps = this.accesoDB.prepareStatement(DELETE_PROVEEDOR);
            this.ps.setInt(1, id);

            int filasAfectadas = this.ps.executeUpdate();

            if (filasAfectadas > 0) {
                resultado = "exito";
            } else {
                resultado = "error_eliminar_proveedor";
            }

            this.conexion.cerrarConexiones();
        } catch (SQLException e) {
            resultado = "error_excepcion";
            e.printStackTrace();
        }

        return resultado;
    }
}
