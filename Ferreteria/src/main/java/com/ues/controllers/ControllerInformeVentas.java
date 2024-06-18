package com.ues.controllers;

import com.ues.models.dao.InformeVentasDAO;
import com.ues.models.dtos.InformeVentasTrimestresDTO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet(name = "ControllerInformeVentas", urlPatterns = {"/ControllerInformeVentas"})
public class ControllerInformeVentas extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            /* TODO output your page here. You may use following sample code. */
            response.getWriter().println("<!DOCTYPE html>");
            response.getWriter().println("<html>");
            response.getWriter().println("<head>");
            response.getWriter().println("<title>Servlet ControllerInformeVentas</title>");
            response.getWriter().println("</head>");
            response.getWriter().println("<body>");
            response.getWriter().println("<h1>Servlet ControllerInformeVentas at " + request.getContextPath() + "</h1>");
            response.getWriter().println("</body>");
            response.getWriter().println("</html>");
        } finally {
            response.getWriter().close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String consultarDatos = request.getParameter("consultar_datos");
        
        if (consultarDatos == null) {
            return;
        }
        
        switch (consultarDatos) {
            case "si_consulta":
                JSONArray arrayProvProd = new JSONArray();
                JSONObject jsonProvProd = new JSONObject();
                ArrayList<InformeVentasTrimestresDTO> informeVentas = new ArrayList<>();
                
                int mesInicio = Integer.parseInt(request.getParameter("mes-inicio"));
                int mesFin = Integer.parseInt(request.getParameter("mes-fin"));
                int year = Integer.parseInt(request.getParameter("anio"));
                
                String html = "";
                
                try {
                    InformeVentasDAO dao = new InformeVentasDAO();
                    informeVentas = dao.selectInformeVentasTrimestres(year, mesInicio, mesFin);
                    html += "<table id=\"tabla_provprod\""
                            + "class=\"table table-bordered dt-responsive nowrap\""
                            + "cellspacing=\"0\" width=\"100%\">\n"
                            + "<thead>\n"
                            + "<tr>\n"
                            + "<th style='white-space: pre-wrap; text-align: center;'>#</th>\n"
                            + "<th style='white-space: pre-wrap; text-align: center;'>MES</th>\n"
                            + "<th style='white-space: pre-wrap; text-align: center;'>CANTIDAD DE VENTAS POR MES</th>\n"
                            + "<th style='white-space: pre-wrap; text-align: center;'>TOTAL DE INGRESOS POR MES ($)</th>\n"
                            + "</tr>\n"
                            + "</thead>\n"
                            + "</tbody>";
                    int cont = 0;
                    for (InformeVentasTrimestresDTO dto : informeVentas) {
                        cont++;
                        html += "<tr>";
                        html += "<td style='white-space: pre-wrap; text-align: center;'>" + cont + "</td>";
                        html += "<td style='white-space: pre-wrap; text-align: center;'>" + dto.getMes() + "</td>";
                        html += "<td style='white-space: pre-wrap; text-align: center;'>" + dto.getVentas_por_mes()+ "</td>";
                        html += "<td style='white-space: pre-wrap; text-align: center;'>" + "$ " + dto.getTotal_ventas() + "</td>";
                        html += "</tr>";
                    }
                    html += "</tbody>\n"
                            + "\t\t </table>";

                    jsonProvProd.put("resultado", "exito");
                    jsonProvProd.put("tabla", html);
                    jsonProvProd.put("cuantos", cont);

                } catch (Exception ex) {
                    jsonProvProd.put("resultado", "error");
                    jsonProvProd.put("mensaje", ex.getMessage());
                }
                
                arrayProvProd.put(jsonProvProd);
                response.getWriter().write(arrayProvProd.toString());
                break;
                
            default:
                System.out.println("No se realizó ninguna acción");
                break;
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
