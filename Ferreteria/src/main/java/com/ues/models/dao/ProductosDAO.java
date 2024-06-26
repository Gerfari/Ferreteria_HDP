package com.ues.models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.ues.models.Conexion;
import com.ues.models.Productos;
import com.ues.models.Categorias;

/**
 *
 * @author kevin
 */
public class ProductosDAO {

    Conexion conexion = null;
    private ArrayList<Productos> productoList;
    private ResultSet rs = null;
    private PreparedStatement ps;
    private Connection accesoDB;
    private Productos producto;

    private static final String INSERT_PRODUCTOS = "INSERT INTO productos "
            + "(id_producto, nombre_producto, descripcion, estado_producto, id_categoria) "
            + "VALUES (?,?,?,?,?)";

    private static final String SELECT_ALL_PRODUCTOS = "SELECT pr.*, cat.categoria FROM productos as pr"
            + " INNER JOIN categorias as cat ON pr.id_categoria = cat.id_categoria ORDER BY  pr.id_producto DESC";

    private static final String UPDATE_PRODUCTOS = "UPDATE productos SET nombre_producto= ?, descripcion= ?, estado_producto= ?, id_categoria= ? WHERE id_producto= ?";

    private static final String SELECT_PRODUCTO_BY_ID = "SELECT * FROM productos WHERE id_producto=?";

    private static final String OBTENER_CATEGORIAS = "SELECT * FROM categorias ";

    public ProductosDAO() throws SQLException, ClassNotFoundException {
        this.conexion = new Conexion();
    }

    //Insertar Productos
    public String insertarProducto(Productos producto) throws SQLException, ClassNotFoundException {
        String resultado;
        int resultado_insertar;
        try {
            this.conexion = new Conexion();
            this.conexion.getConexion();
            this.accesoDB = conexion.getConexion();
            this.ps = this.accesoDB.prepareStatement(INSERT_PRODUCTOS);
            this.ps.setInt(1, producto.getIdProducto());
            this.ps.setString(2, producto.getNombreProducto());
            this.ps.setString(3, producto.getDescripcion());
            this.ps.setBoolean(4, producto.isEstadoProducto());
            this.ps.setInt(5, producto.getCategoria().getIdCategoria());

            System.out.println("producto_insertar" + producto);
            resultado_insertar = this.ps.executeUpdate();
            this.conexion.cerrarConexiones();
            if (resultado_insertar > 0) {
                resultado = "exito";
            } else {
                resultado = "error_insertar_producto";
            }
        } catch (SQLException e) {
            resultado = "error_excepcion";
            System.out.println("fallo insertar" + e.getErrorCode());
            e.printStackTrace();
        }
        return resultado;
    }

    //Mostrar Productos
    public ArrayList<Productos> SelectAllProductos(Integer estado, String quien) throws SQLException, ClassNotFoundException {
        this.productoList = new ArrayList();
        try {
            this.accesoDB = this.conexion.getConexion();
            this.ps = this.accesoDB.prepareStatement(SELECT_ALL_PRODUCTOS);
            this.rs = ps.executeQuery();
            Productos obj = null;
            while (this.rs.next()) {
                obj = new Productos();
                obj.setIdProducto(rs.getInt("id_producto"));
                obj.setNombreProducto(rs.getString("nombre_producto"));
                obj.setDescripcion(rs.getString("descripcion"));
                obj.setEstadoProducto(rs.getBoolean("estado_producto"));

                int idCategoria = rs.getInt("id_categoria");
                String cat = rs.getString("categoria");
                Categorias categoria = new Categorias();
                categoria.setIdCategoria(idCategoria);
                categoria.setCategoria(cat);
                obj.setCategoria(categoria);
                this.productoList.add(obj);
            }
            this.conexion.cerrarConexiones();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.productoList;
    }

    //Actualizar Registros
    public String updateProducto(Productos producto, int codigo) throws SQLException {
        System.out.println(producto.getIdProducto());
        String resultado;
        int res_actualizar;
        try {
            this.accesoDB = this.conexion.getConexion();
            this.ps = this.accesoDB.prepareStatement(UPDATE_PRODUCTOS);
            this.ps.setString(1, producto.getNombreProducto());
            this.ps.setString(2, producto.getDescripcion());
            this.ps.setBoolean(3, producto.isEstadoProducto());
            this.ps.setInt(4, producto.getCategoria().getIdCategoria());
            this.ps.setInt(5, producto.getIdProducto());
            
            res_actualizar = this.ps.executeUpdate();
            System.out.println(res_actualizar);
            if (res_actualizar > 0) {
                resultado = "exito";
            } else {
                resultado = "error_actualizar";
            }
        } catch (SQLException e) {
            resultado = "error_exception";
            e.printStackTrace();
        }
        return resultado;
    }
    
    public Productos findById(int quien) throws SQLException, ClassNotFoundException{
        try {
            this.accesoDB = this.conexion.getConexion();
            System.out.println("sql"+ quien + UPDATE_PRODUCTOS);
            this.ps = this.accesoDB.prepareStatement(SELECT_PRODUCTO_BY_ID);
            this.ps.setInt(1, quien);
            this.rs = ps.executeQuery();
            while(this.rs.next()){
                this.producto = new Productos();
                this.producto.setIdProducto(rs.getInt("id_producto"));
                this.producto.setNombreProducto(rs.getString("nombre_producto"));
                this.producto.setDescripcion(rs.getString("descripcion"));
                this.producto.setEstadoProducto(rs.getBoolean("estado_producto"));
                Categorias categoria = new Categorias();
                categoria.setIdCategoria(rs.getInt("id_categoria"));
                this.producto.setCategoria(categoria);               
            }
            this.conexion.cerrarConexiones();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Debe de retornar el objeto encontrado
        return this.producto;
    }
    //Cargar Combobox
    public ArrayList<Categorias> comboCategorias() {
        ArrayList<Categorias> listaCat = new ArrayList<>();
        try {
            this.conexion = new Conexion();
            this.accesoDB = conexion.getConexion();
            this.ps = accesoDB.prepareStatement(OBTENER_CATEGORIAS);
            this.rs = ps.executeQuery();
            while (rs.next()) {
                Categorias obj = new Categorias();
                obj.setIdCategoria(rs.getInt("id_categoria"));
                obj.setCategoria(rs.getString("categoria"));
                listaCat.add(obj);
            }
            this.conexion.cerrarConexiones();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listaCat;
    }
}
