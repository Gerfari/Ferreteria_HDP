/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ues.controllers;

import com.ues.models.dao.prod_VendidosDAO;
import com.ues.models.dao.proveedorDemandadoDAO;
import com.ues.models.dtos.prod_VendidosDTO;
import com.ues.models.dtos.proveedorDemandadoDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Emerson
 */
@WebServlet(name = "ControllerProveedorDemandado", urlPatterns = {"/ControllerProveedorDemandado"})
public class ControllerProveedorDemandado extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ControllerProveedorDemandado</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControllerProveedorDemandado at " + request.getContextPath() + "</h1>");
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
response.setContentType("aplication/json;charset=utf-8");
        String filtro = request.getParameter("consultar_dato");//LLAMO EL DATOS QUE CONTIENE CONSULTAR DATO PARA VER QUE CASE DEL SWITCH SE EJECUTARA

        //SI NO TRAE NADA NO EJECUTARA NADA Y AHI TERMINARA ESTE PROCESO
        if (filtro == null) {
            return;
        }
        switch (filtro) {
            case "si_consulta":
                JSONArray array_dem = new JSONArray();
                JSONObject json_dem = new JSONObject();
                ArrayList<proveedorDemandadoDTO> categoriasLst = new ArrayList<>();
                String html = "";
                String el_estado = request.getParameter("estado");

                 {
                    try {
                        proveedorDemandadoDAO daoCat = new proveedorDemandadoDAO();
                        categoriasLst = daoCat.selectDisponiblesCategoria();
                                       html +="<div class = 'table-responsive'>";

                        html += "<table id=\"tabla_demanda\""
                                + "class=\"table table-bordered dt-responsive nowrap\""
                                + "cellspacing=\"0\" width=\"100%\">\n"
                                + "<thead>\n"
                                + "<tr>\n"
                                + "<th> # </th>\n"
                                //+ "<th>ID DE PROVEEDOR </th>\n"
                                + "<th>NOMBRE DE PROVEEDOR</th>\n"
                                //+ "<th>ID DE PRODUCTO</th>\n"
                                + "<th>NOMBRE DE PRODUCTO</th>\n"
                                + "<th>CANTIDAD DE COMPRAS</th>\n"
                                + "</tr>\n"
                                + "</thead>\n"
                                + "</tbody>";
                        int cont = 0;
                        for (proveedorDemandadoDTO dt : categoriasLst) {
                            cont++;
                            html += "<tr>";
                            html += "<td>" + cont + "</td>";
                            //html += "<td>" + dt.getId_proveedor() + "</td>";
                            html += "<td>" + dt.getNombre_proveedor() + "</td>";
                            //html += "<td>" + dt.getId_producto() + "</td>";
                            html += "<td>" + dt.getNombre_producto() + "</td>";
                            html += "<td>" + dt.getTotal_ventas() + "</td>";
                            html += "</tr>";
                        }//CIERRE DEL CICLO FOR
                        html += "</tbody>\n"
                                + "\t\t </table>";

                        json_dem.put("resultado", "exito");
                        json_dem.put("tabla", html);
                        json_dem.put("cuantos", cont);

                    } catch (SQLException ex) {
                        json_dem.put("resultado", "error sql");
                        json_dem.put("error", ex.getMessage());
                        json_dem.put("code error", ex.getErrorCode());
                    } catch (ClassNotFoundException ex) {
                        json_dem.put("resultado", "class not found");
                        json_dem.put("error", ex.getMessage());
                        throw new RuntimeException(ex);
                    }
                }
                 array_dem.put(json_dem);
                 response.getWriter().write(array_dem.toString());
                break;

            default:
                System.out.println("No se realizo ninguna accion");
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
