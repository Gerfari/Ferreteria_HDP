package com.ues.controllers;

import com.ues.models.dao.InformeComprasProductosDAO;
import com.ues.models.dtos.InformeComprasProductosDTO;
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
 * @author kevin
 */
@WebServlet(name = "ControllerInformeComprasProductos", urlPatterns = {"/ControllerInformeComprasProductos"})
public class ControllerInformeComprasProductos extends HttpServlet {

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
            out.println("<title>Servlet ControllerListProvProd</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControllerListProvProd at " + request.getContextPath() + "</h1>");
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
                JSONArray array_provprod = new JSONArray();
                JSONObject json_provprod = new JSONObject();
                ArrayList<InformeComprasProductosDTO> InformeComprasProd = new ArrayList<>();
                String html = "";
                String el_estado = request.getParameter("estado");
                 {
                    try {
                        InformeComprasProductosDAO dao = new InformeComprasProductosDAO();
                        InformeComprasProd = dao.selectAllProveedorProducto();
                        html += "<table id=\"tabla_provprod\""
                                + "class=\"table table-bordered dt-responsive nowrap\""
                                + "cellspacing=\"0\" width=\"100%\">\n"
                                + "<thead>\n"
                                + "<tr>\n"
                                + "<th>#</th>\n"
                                + "<th>PRODUCTO</th>\n"
                                + "<th>CATEGORÍA</th>\n"
                                + "<th>PROVEEDOR</th>\n"
                                + "<th>FECHA COMPRA</th>\n"
                                + "<th>CANTIDAD</th>\n"
                                + "<th>PRECIO</th>\n"
                                + "<th>TOTAL</th>\n"
                                + "<th>EXISTENCIA</th>\n"
                                + "</tr>\n"
                                + "</thead>\n"
                                + "</tbody>";
                        int cont = 0;
                        for (InformeComprasProductosDTO dto : InformeComprasProd) {
                            cont++;
                            html += "<tr>";
                            html += "<td style='white-space: pre-wrap; text-align: center;'>" + cont + "</td>";
                            html += "<td style='white-space: pre-wrap;'>" + dto.getProducto() + "</td>";
                            html += "<td style='white-space: pre-wrap;'>" + dto.getCategoria() + "</td>";
                            html += "<td style='white-space: pre-wrap;'>" + dto.getProveedor() + "</td>";
                            html += "<td style='white-space: pre-wrap; text-align: center;'>" + dto.getFecha_compra() + "</td>";
                            html += "<td style='white-space: pre-wrap; text-align: center;'>" + dto.getCantidad_compra() + "</td>";
                            html += "<td style='white-space: pre-wrap; text-align: center;'>" + "$" + dto.getPrecio_producto() + "</td>";
                            html += "<td style='white-space: pre-wrap; text-align: center;'>" + "$" + dto.getTotal_compra() + "</td>";
                            html += "<td style='white-space: pre-wrap; text-align: center;'>" + dto.getExistencia() + "</td>";
                            html += "</tr>";
                        }//CIERRE DEL CICLO FOR
                        html += "</tbody>\n"
                                + "\t\t </table>";

                        json_provprod.put("resultado", "exito");
                        json_provprod.put("tabla", html);
                        json_provprod.put("cuantos", cont);

                    } catch (SQLException ex) {
                        json_provprod.put("resultado", "error sql");
                        json_provprod.put("error", ex.getMessage());
                        json_provprod.put("code error", ex.getErrorCode());
                    } catch (ClassNotFoundException ex) {
                        json_provprod.put("resultado", "class not found");
                        json_provprod.put("error", ex.getMessage());
                        throw new RuntimeException(ex);
                    }
                }
                array_provprod.put(json_provprod);
                response.getWriter().write(array_provprod.toString());
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
