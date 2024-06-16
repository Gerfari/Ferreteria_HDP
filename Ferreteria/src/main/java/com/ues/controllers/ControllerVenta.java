/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ues.controllers;

import com.google.gson.Gson;
import com.ues.models.dao.VentaDAO;
import com.ues.models.dtos.VentasIntervaloFecha;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author herna
 */
@WebServlet(name = "ControllerVenta", urlPatterns = {"/ControllerVenta"})
public class ControllerVenta extends HttpServlet {

    DecimalFormat formato = new DecimalFormat("0.00");
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ControllerVenta</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControllerVenta at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("aplicacion/json;charset=utf-8");
        PrintWriter out = resp.getWriter();
        String filtro = req.getParameter("consultar_datos");
        if (filtro == null) {
            return;
        }
        VentaDAO daoVenta = new VentaDAO();
        switch (filtro) {
            case "llenar_combo":
                JSONArray array_fechas = new JSONArray();
                JSONObject json_fechas = new JSONObject();
                //Gson gson = new Gson();
                String el_estado = req.getParameter("estado");
                ArrayList<Date> lsFechas = new ArrayList<>();
                //VentaDAO daoVenta = new VentaDAO();
                lsFechas = daoVenta.fechasDeVentas();
                //CONVERTIENDO LOS DATOS DIRECTAMENTE EN JSON
                //String fechasJson = gson.toJson(lsFechas);
                //resp.getWriter().write(fechasJson);

                //OTRA MANERA DE HACERLO
                json_fechas.put("resultado", "exito");
                json_fechas.put("fechas", lsFechas);

                array_fechas.put(json_fechas);
                resp.getWriter().write(array_fechas.toString());
                break;
            case "envio_fechas":
                JSONArray array_ventas = new JSONArray();
                JSONObject json_ventas = new JSONObject();
                String fechaInicio = req.getParameter("fechaInicio");
                String fechaFin = req.getParameter("fechaFin");
                System.out.println("Fecha Inicio: " + fechaInicio + " Y fecha de fin: " + fechaFin);
                //SimpleDateFormat formato = new SimpleDateFormat();
                
                ArrayList<VentasIntervaloFecha> lsVentas = new ArrayList<>();

                String html = "";
                 {
                   
                        //CONVERTIMOS LAS FECHAS EN DATE
//                        Date fechaIni = formato.parse(fechaInicio);
//                        Date fechaF = formato.parse(fechaFin);

                        lsVentas = daoVenta.ventasEntreFechas(fechaInicio, fechaFin);

                        //CREO EL CONTENIDO DEL HTML
                        html += "<div class='table-responsive'>"; //ASEGURA LA RESPONSIVIDAD
                        html += "<table id=\"tabla_registros\""
                                + "class=\"table table-bordered dt-responsive nowrap\""
                                + "cellspacing=\"0\" width=\"100%\">\n"
                                + "<thead>\n"
                                + "<tr>\n"
                                + "<th>NUMERO #</th>\n"
                                + "<th>VENDEDOR </th>\n"
                                + "<th>CANTIDAD DE <br>VENTAS REALIZADAS</th>\n"
                                + "<th>TOTAL</th>\n"
                                + "</tr>\n"
                                + "</thead>\n"
                                + "<tbody>";
                        int cont = 0;
                        int totalVentas=0;
                        double totalRecaudado=0;
                        for (VentasIntervaloFecha dto : lsVentas) {
                            totalVentas+=dto.getTotalDeVentas();
                            totalRecaudado+=dto.getMontoTotal();
                            cont++;
                            html += "<tr>";
                            html += "<td>" + cont + "</td>";
                            html += "<td>" + dto.getNombreEmpleado() + "</td>";
                            html += "<td>" + dto.getTotalDeVentas() + "</td>";
                            String montoFormateado=formato.format(dto.getMontoTotal());
                            html += "<td> $" + montoFormateado + "</td>";
                            html += "</tr>";
                        }//CIERRE DEL FOR
                        html += "</tbody>\n"
                                + "\t\t </table>";
                        html += "</div>";  // Cierre del div table-responsive
                        json_ventas.put("resultado", "exito");
                        json_ventas.put("desde", fechaInicio);
                        json_ventas.put("hasta", fechaFin);
                        json_ventas.put("tabla", html);
                        json_ventas.put("cuantos", totalVentas);
                        json_ventas.put("totalRecaudado",totalRecaudado);

                    
                }
                array_ventas.put(json_ventas);
                resp.getWriter().write(array_ventas.toString());
                break;

        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
