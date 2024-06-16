
package com.ues.controllers;

import com.google.gson.Gson;
import com.ues.models.dao.ConsultasDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "ReporteVentasServlet", urlPatterns = {"/ReporteVentasServlet"})
public class ReporteVentasServlet extends HttpServlet {

    private ConsultasDao reporte;

    @Override
    public void init() throws ServletException {
        reporte = new ConsultasDao();
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
     
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String fechaInicioStr = request.getParameter("fechainicio");
    String fechaFinStr = request.getParameter("fechafin");

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    try {
        java.util.Date parsedFechaInicio = dateFormat.parse(fechaInicioStr);
        java.util.Date parsedFechaFin = dateFormat.parse(fechaFinStr);

        Date fechaInicio = new Date(parsedFechaInicio.getTime());
        Date fechaFin = new Date(parsedFechaFin.getTime());

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        out.print(gson.toJson(reporte.getReporteVentas(fechaInicio, fechaFin)));
        out.flush();
    }catch (ParseException e) {
        e.printStackTrace();
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error en el formato de fecha.");
    }
 
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
