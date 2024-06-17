
package com.ues.controllers;

import com.google.gson.Gson;
import com.ues.models.dao.ConsultasDao;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "ClienteCompraServlet", urlPatterns = {"/ClienteCompraServlet"})
public class ClienteCompraServlet extends HttpServlet {
 private ConsultasDao clienteC;

    @Override
    public void init() throws ServletException {
        clienteC = new ConsultasDao();
    }
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
     
    }

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      // Obtener el número de DUI 
        String dui = request.getParameter("dui");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        out.print(gson.toJson(clienteC.getComprasPorCliente(dui)));
        out.flush(); 
        
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
