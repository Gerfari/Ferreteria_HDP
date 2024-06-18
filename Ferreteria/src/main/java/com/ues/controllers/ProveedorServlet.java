package com.ues.controllers;

import com.ues.models.Proveedor;
import com.ues.models.dao.proveedorDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/ProveedorServlet")
public class ProveedorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            handleRequest(request, response);
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error interno del servidor");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        try {
            handleRequest(request, response);
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error interno del servidor");
        }
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "add":
                    agregarProveedor(request, response);
                    break;
                case "update":
                    actualizarProveedor(request, response);
                    break;
                case "list":
                    listarProveedores(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
                    break;
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No se especificó ninguna acción");
        }
    }

    private void agregarProveedor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        String nombre = request.getParameter("nombre");
        String direccion = request.getParameter("direccion");
        String telefono = request.getParameter("telefono");
        boolean estado = Boolean.parseBoolean(request.getParameter("estado"));

        if (nombre.isEmpty() || direccion.isEmpty() || telefono.isEmpty()) {
            response.getWriter().write("error: El nombre, la dirección y el teléfono son campos obligatorios.");
            return;
        }

        Proveedor nuevoProveedor = new Proveedor(0, nombre, direccion, telefono, estado);
        proveedorDAO dao = new proveedorDAO();

        // Verificar duplicados
        if (dao.existeNombreTelefono(nombre, telefono, 0)) {
            response.getWriter().write("error: El nombre o el teléfono ya existen.");
            return;
        }

        String resultado = dao.insertProveedor(nuevoProveedor);

        if ("exito".equals(resultado)) {
            response.getWriter().write(new JSONObject().put("message", "Proveedor agregado exitosamente").toString());
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al agregar el proveedor");
        }
    }

    private void actualizarProveedor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        int id = Integer.parseInt(request.getParameter("id"));
        String nombre = request.getParameter("nombre");
        String direccion = request.getParameter("direccion");
        String telefono = request.getParameter("telefono");
        boolean estado = Boolean.parseBoolean(request.getParameter("estado"));

        if (nombre.isEmpty() || direccion.isEmpty() || telefono.isEmpty()) {
            response.getWriter().write("error: El nombre, la dirección y el teléfono son campos obligatorios.");
            return;
        }

        Proveedor proveedorActualizado = new Proveedor(id, nombre, direccion, telefono, estado);
        proveedorDAO dao = new proveedorDAO();

        // Verificar duplicados
        if (dao.existeNombreTelefono(nombre, telefono, id)) {
            response.getWriter().write("error: El nombre o el teléfono ya existen.");
            return;
        }

        dao.updateProveedor(proveedorActualizado);

        response.getWriter().write(new JSONObject().put("message", "Proveedor actualizado exitosamente").toString());
    }

    private void listarProveedores(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        List<Proveedor> proveedores = new proveedorDAO().selectAllProveedores();
        JSONArray arrayProveedores = new JSONArray();

        for (Proveedor proveedor : proveedores) {
            JSONObject jsonProveedor = new JSONObject();
            jsonProveedor.put("id", proveedor.getIdProveedor());
            jsonProveedor.put("nombre", proveedor.getNombreProveedor());
            jsonProveedor.put("direccion", proveedor.getDireccionProveedor());
            jsonProveedor.put("telefono", proveedor.getTelefono());
            jsonProveedor.put("estado", proveedor.isEstadoProveedor());
            arrayProveedores.put(jsonProveedor);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(arrayProveedores.toString());
    }
}
