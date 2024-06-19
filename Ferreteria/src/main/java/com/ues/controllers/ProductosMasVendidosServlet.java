/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ues.controllers;

import com.ues.models.dao.prod_VendidosDAO;
import com.ues.models.dtos.prod_VendidosDTO;
import java.io.IOException;
import java.sql.SQLException; 
import java.util.ArrayList;
import java.util.List;
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
@WebServlet(name = "ProductosMasVendidosServlet", urlPatterns = {"/productosMasVendidos"})
public class ProductosMasVendidosServlet extends HttpServlet {

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
                JSONArray array_vendidos = new JSONArray();
                JSONObject json_vendidos = new JSONObject();
                ArrayList<prod_VendidosDTO> categoriasList = new ArrayList<>();
                String html = "";
                String el_estado = request.getParameter("estado");

                 {
                    try {
                        prod_VendidosDAO daoCat = new prod_VendidosDAO();
                        categoriasList = daoCat.selectDisponiblesCategoria();
                        html += "<table id=\"tabla_vendido\""
                                + "class=\"table table-bordered dt-responsive nowrap\""
                                + "cellspacing=\"0\" width=\"100%\">\n"
                                + "<thead>\n"
                                + "<tr>\n"
                                + "<th> # </th>\n"
                                + "<th>NOMBRE DEL PRODUCTO </th>\n"
                                + "<th>CANTIDAD VENDIDA</th>\n"
                                + "</tr>\n"
                                + "</thead>\n"
                                + "</tbody>";
                        int cont = 0;
                        for (prod_VendidosDTO dto : categoriasList) {
                            cont++;
                            html += "<tr>";
                            html += "<td>" + cont + "</td>";
                            html += "<td>" + dto.getNombreProducto() + "</td>";
                            html += "<td>" + dto.getTotalVendido() + "</td>";
                            html += "</tr>";
                        }//CIERRE DEL CICLO FOR
                        html += "</tbody>\n"
                                + "\t\t </table>";

                        json_vendidos.put("resultado", "exito");
                        json_vendidos.put("tabla", html);
                        json_vendidos.put("cuantos", cont);

                    } catch (SQLException ex) {
                        json_vendidos.put("resultado", "error sql");
                        json_vendidos.put("error", ex.getMessage());
                        json_vendidos.put("code error", ex.getErrorCode());
                    } catch (ClassNotFoundException ex) {
                        json_vendidos.put("resultado", "class not found");
                        json_vendidos.put("error", ex.getMessage());
                        throw new RuntimeException(ex);
                    }
                }
                 array_vendidos.put(json_vendidos);
                 response.getWriter().write(array_vendidos.toString());
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
      
    


