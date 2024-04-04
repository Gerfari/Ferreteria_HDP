package com.ues.controllers;

import com.ues.models.Productos;
import com.ues.models.dao.ProductosDisponiblesDAO;
import com.ues.models.dtos.ProductosDisponiblesDTO;

import java.io.IOException;
import java.io.PrintWriter;
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
 * @author kevin
 */
@WebServlet(name = "ControllerProductoDisponible", urlPatterns = {"/ControllerProductoDisponible"})
public class ControllerProductoDisponible extends HttpServlet {
    
    //por si no funciona quitar
    ProductosDisponiblesDAO dao = new ProductosDisponiblesDAO();
    Productos producto = new Productos();
    
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
            out.println("<title>Servlet ControllerProductoDisponible</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControllerProductoDisponible at " + request.getContextPath() + "</h1>");
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
        String filtro = request.getParameter("consultar_datos");
        if (filtro == null) {
            return;
        }
        switch (filtro) {
            case "si_consulta":
                JSONArray array_producto = new JSONArray();
                JSONObject json_producto = new JSONObject();
                ArrayList<ProductosDisponiblesDTO> ListProduto = new ArrayList<>();
                String html = "";
                String el_estado = request.getParameter("estado");
                 {
                     ProductosDisponiblesDAO dao = new ProductosDisponiblesDAO();
                     ListProduto = dao.selectAllProductoDisponible();
                     html += "<table id=\"tabla_producto\""
                             + "class=\"table table-bordered dt-responsive nowrap\""
                             + "cellspacing=\"0\" width=\"100%\">\n"
                             + "<thead>\n"
                             + "<tr>\n"
                             + "<th># Registro</th>\n"
                             + "<th>Producto</th>\n"
                             + "<th>Cantidad</th>\n"
                             + "</tr>\n"
                             + "</thead>\n"
                             + "</tbody>";
                     int cont = 0;
                     for (ProductosDisponiblesDTO dto : ListProduto) {
                         cont++;
                         html += "<tr>";
                         html += "<td>" + cont + "</td>";
                         html += "<td>" + dto.getProducto() + "</td>";
                         html += "<td>" + dto.getCantidadDisponible()+ "</td>";
                         html += "</tr>";
                     }//CIERRE DEL CICLO FOR
                     html += "</tbody>\n"
                             + "\t\t </table>";
                     json_producto.put("resultado", "exito");
                     json_producto.put("tabla", html);
                     json_producto.put("cuantos", cont);
                }
                array_producto.put(json_producto);
                response.getWriter().write(array_producto.toString());
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
