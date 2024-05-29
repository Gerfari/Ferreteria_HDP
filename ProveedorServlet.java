package com.ues.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

import com.ues.models.Proveedor;
import com.ues.models.dao.proveedorDAO;

@WebServlet("/ProveedorServlet")
public class ProveedorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace(); // Manejar la excepción adecuadamente
            
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error interno del servidor");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace(); // Manejar la excepción adecuadamente
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error interno del servidor");
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        // Obtener la acción de la solicitud
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "add":
                    // Manejar la solicitud de agregar un nuevo proveedor
                    agregarProveedor(request, response);
                    break;
                case "update":
                    // Manejar la solicitud de actualizar un proveedor
                    actualizarProveedor(request, response);
                    break;
                case "delete":
                    // Manejar la solicitud de eliminar un proveedor
                    eliminarProveedor(request, response);
                    break;
                default:
                    break;
            }
        }

        // Obtener la lista de proveedores y setearla como atributo de la solicitud
        List<Proveedor> proveedores = new proveedorDAO().selectAllProveedores();
        request.setAttribute("proveedores", proveedores);

        // Redirigir la solicitud a la página de proveedores
        RequestDispatcher dispatcher = request.getRequestDispatcher("Crud/proveedor.jsp");
        dispatcher.forward(request, response);
    }

    private void agregarProveedor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        // obtiene los parámetros del formulario
        String nombre = request.getParameter("nombre");
        String direccion = request.getParameter("direccion");
        String telefono = request.getParameter("telefono");
        boolean estado = Boolean.parseBoolean(request.getParameter("estado"));

        // Crear un objeto Proveedor con los datos recibidos
        Proveedor nuevoProveedor = new Proveedor(0, nombre, direccion, telefono, estado);

        // Llamar al método en el DAO para agregar el proveedor
        proveedorDAO dao = new proveedorDAO();
        String resultado = dao.insertProveedor(nuevoProveedor);

       
    }

    private void actualizarProveedor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        // obtiene los parámetros del formulario
        int id = Integer.parseInt(request.getParameter("id"));
        String nombre = request.getParameter("nombre");
        String direccion = request.getParameter("direccion");
        String telefono = request.getParameter("telefono");
        boolean estado = Boolean.parseBoolean(request.getParameter("estado"));

        // Crear un objeto Proveedor con los datos recibidos
        Proveedor proveedorActualizado = new Proveedor(id, nombre, direccion, telefono, estado);

        // Llama al método en el DAO para actualizar el proveedor
        proveedorDAO dao = new proveedorDAO();
        String resultado = dao.updateProveedor(proveedorActualizado);

        
    }

    private void eliminarProveedor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        // ID del proveedor a eliminar
        int id = Integer.parseInt(request.getParameter("id"));

        // Llamar al método en el DAO para eliminar el proveedor
        proveedorDAO dao = new proveedorDAO();
        String resultado = dao.deleteProveedor(id);

        
    }
}
