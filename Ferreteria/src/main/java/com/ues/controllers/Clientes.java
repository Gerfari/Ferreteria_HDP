
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


@WebServlet(name = "Clientes", urlPatterns = {"/Clientes"})
public class Clientes extends HttpServlet {
private ConsultasDao clientesDao;

    @Override
    public void init() throws ServletException {
        clientesDao = new ConsultasDao();
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    }
    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        out.print(gson.toJson(clientesDao.getAllCliente()));
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
