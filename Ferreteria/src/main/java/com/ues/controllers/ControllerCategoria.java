/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ues.controllers;

import com.ues.models.dao.CategoriasDAO;
import com.ues.models.dtos.CantidadPorCategoriaDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
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
@WebServlet(name = "ControllerCategoria", urlPatterns = {"/ControllerCategoria"})
public class ControllerCategoria extends HttpServlet {

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
            out.println("<title>Servlet ControllerCategoria</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControllerCategoria at " + request.getContextPath() + "</h1>");
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
        resp.setContentType("aplication/json;charset=utf-8");
        String filtro = req.getParameter("consultar_datos");//LLAMO EL DATOS QUE CONTIENE CONSULTAR DATO PARA VER QUE CASE DEL SWITCH SE EJECUTARA

        //SI NO TRAE NADA NO EJECUTARA NADA Y AHI TERMINARA ESTE PROCESO
        if (filtro == null) {
            return;
        }
        switch (filtro) {
            case "si_consulta":
                JSONArray array_productos = new JSONArray();
                JSONObject json_productos = new JSONObject();
                ArrayList<CantidadPorCategoriaDTO> categoriasList = new ArrayList<>();
                String html = "";
                String el_estado = req.getParameter("estado");

                 {
                    try {
                        CategoriasDAO daoCat = new CategoriasDAO();
                        categoriasList = daoCat.selectDisponiblesCategoria();
                         html += "<div class='table-responsive'>"; //ASEGURA LA RESPONSIVIDAD
                        html += "<table id=\"tabla_producto\""
                                + "class=\"table table-bordered dt-responsive nowrap\""
                                + "cellspacing=\"0\" width=\"100%\">\n"
                                + "<thead>\n"
                                + "<tr>\n"
                                + "<th>Numero #</th>\n"
                                + "<th>Categoria </th>\n"
                                + "<th>Cantidad de productos</th>\n"
                                + "</tr>\n"
                                + "</thead>\n"
                                + "<tbody>";
                        int cont = 0;
                        for (CantidadPorCategoriaDTO dto : categoriasList) {
                            cont++;
                            html += "<tr>";
                            html += "<td>" + cont + "</td>";
                            html += "<td>" + dto.getNombreCategoria() + "</td>";
                            html += "<td>" + dto.getCantidadCategoria() + "</td>";
                            html += "</tr>";
                        }//CIERRE DEL CICLO FOR
                        html += "</tbody>\n"
                                + "\t\t </table>";
                        html += "</div>";  // Cierre del div table-responsive

                        json_productos.put("resultado", "exito");
                        json_productos.put("tabla", html);
                        json_productos.put("cuantos", cont);

                    } catch (SQLException ex) {
                        json_productos.put("resultado", "error sql");
                        json_productos.put("error", ex.getMessage());
                        json_productos.put("code error", ex.getErrorCode());
                    } catch (ClassNotFoundException ex) {
                        json_productos.put("resultado", "class not found");
                        json_productos.put("error", ex.getMessage());
                        throw new RuntimeException(ex);
                    }
                }
                 array_productos.put(json_productos);
                 resp.getWriter().write(array_productos.toString());
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
