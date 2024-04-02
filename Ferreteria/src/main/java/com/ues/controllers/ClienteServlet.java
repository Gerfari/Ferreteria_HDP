package com.ues.controllers;
import com.ues.models.dao.ClienteDAO;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.google.gson.Gson;
import com.ues.models.Cliente;
import javax.servlet.annotation.WebServlet;


@WebServlet("/ClienteServlet")
public class ClienteServlet extends HttpServlet {
    
    private ClienteDAO clienteDAO;

    @Override
    public void init() throws ServletException {
        clienteDAO = new ClienteDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener todos los clientes desde la base de datos
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        out.print(gson.toJson(clienteDAO.getAllClientes()));
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action) {
            case "add":
                addCliente(request, response);
                break;
            case "edit":
                editCliente(request, response);
                break;
            /*case "delete":
                deleteCliente(request, response);
                break;*/
            default:
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                break;
        }
    }

    private void addCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String duiCliente = request.getParameter("duiCliente");
        String nombreCliente = request.getParameter("nombreCliente");
        String apellidoCliente = request.getParameter("apellidoCliente");
        String direccionCliente = request.getParameter("direccionCliente");
        String telefonoCliente = request.getParameter("telefonoCliente");
        boolean estadoCliente = Boolean.parseBoolean(request.getParameter("estadoCliente"));
        Cliente cliente = new Cliente(duiCliente, nombreCliente, apellidoCliente, direccionCliente, telefonoCliente, estadoCliente);
        clienteDAO.addCliente(cliente);
    }

    private void editCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String duiCliente = request.getParameter("duiCliente");
        String nombreCliente = request.getParameter("nombreCliente");
        String apellidoCliente = request.getParameter("apellidoCliente");
        String direccionCliente = request.getParameter("direccionCliente");
        String telefonoCliente = request.getParameter("telefonoCliente");
        boolean estadoCliente = Boolean.parseBoolean(request.getParameter("estadoCliente"));
        Cliente cliente = new Cliente(duiCliente, nombreCliente, apellidoCliente, direccionCliente, telefonoCliente, estadoCliente);
        clienteDAO.updateCliente(cliente);
    }

   /* private void deleteCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String duiCliente = request.getParameter("duiCliente");
        clienteDAO.deleteCliente(duiCliente);
    }*/
}
