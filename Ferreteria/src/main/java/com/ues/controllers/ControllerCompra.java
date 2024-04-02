/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ues.controllers;

import com.ues.models.Compras;
import com.ues.models.Empleados;
import com.ues.models.Proveedor;
import com.ues.models.dao.CompraDAO;
import com.ues.models.dtos.ComprasDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
@WebServlet(name = "ControllerCompra", urlPatterns = {"/ControllerCompra"})
public class ControllerCompra extends HttpServlet {

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
            out.println("<title>Servlet ControllerCompra</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControllerCompra at " + request.getContextPath() + "</h1>");
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        resp.setContentType("aplication/json;charset=utf-8");
        PrintWriter out=resp.getWriter();
        String filtro = req.getParameter("consultar_datos");
        //CREO UN FORMATO DE FECHA
        SimpleDateFormat formato=new SimpleDateFormat("yyyy-MM-dd");
        if (filtro==null) {
            return;
        }
        
        switch(filtro){
            case "llenar_combo_proveedor":
                JSONArray array_proveedores = new JSONArray();
                JSONObject json_proveedores = new JSONObject();
                
                ArrayList<Proveedor> lsProveedores = new ArrayList<>();
                CompraDAO daoComp = new CompraDAO();
                lsProveedores=daoComp.comboProveedores();
                //UNA VEZ OBTENIDOS LOS DATOS LOS ENVIO POR MEDIO DEL JSON
                json_proveedores.put("resultado", "exito");
                json_proveedores.put("proveedores", lsProveedores);
                array_proveedores.put(json_proveedores);
                resp.getWriter().write(array_proveedores.toString());
                break;
                
            case "si_consulta":
                CompraDAO daoCompra = new CompraDAO();
                JSONArray array_compras = new JSONArray();
                JSONObject json_compras = new JSONObject();
                
                String el_estado=req.getParameter("estado");
                ArrayList<ComprasDTO> lsCompras = new ArrayList<>();
                lsCompras=daoCompra.showCompras();
                String html="";
                html += "<table id=\"tabla_compras\""
                        + "class=\"table table-bordered dt-responsive nowrap\""
                        + "cellspacing=\"0\" width=\"100%\">\n"
                        + "<thead>\n"
                        + "<tr>\n"
                        + "<th>NUMERO #</th>\n"
                        + "<th>FECHA DE COMPRA </th>\n"
                        + "<th>DUI DEL EMPLEADO</th>\n"
                        + "<th>EMPLEADO</th>\n"
                        + "<th>PROVEEDOR</th>\n"
                        + "</tr>\n"
                        + "</thead>\n"
                        + "</tbody>";
                int cont=0;
                for (ComprasDTO dto: lsCompras) {
                    cont++;
                    html+="<tr>";
                    html+="<td>"+cont+"</td>";
                    html+="<td>"+dto.getFechaCompra()+"</td>";
                    html+="<td>"+dto.getDuiEmpleado()+"</td>";
                    html+="<td>"+dto.getEmpleado()+"</td>";
                    html+="<td>"+dto.getProveedor()+"</td>";
                }//CIERRE DLE FOR
                html += "</tbody>\n"
                        + "\t\t </table>";
                json_compras.put("resultado", "exito");
                json_compras.put("tabla", html);
                json_compras.put("cuantos", cont);
                
                array_compras.put(json_compras);
                resp.getWriter().write(array_compras.toString());
                break;
            case "si_registro":
                JSONArray array_insertar = new JSONArray();
                JSONObject json_insertar = new JSONObject();
                
                boolean resultado_insert=false;
                Compras compra= new Compras();
                
                CompraDAO dao = new CompraDAO();
                
                //OBTENGO LA FECHA Y LE DOY FORMATO
                String fechaCompra=req.getParameter("fechacompra");
                try {
                    compra.setFechaCompra(formato.parse(fechaCompra));
                    
                } catch (ParseException ex) {
                    Logger.getLogger(ControllerCompra.class.getName()).log(Level.SEVERE, null, ex);
                }
                Empleados emp = new Empleados();
                emp.setDuiEmpleado(req.getParameter("empleado"));
                compra.setEmpleado(emp);
                
                Proveedor pr = new Proveedor();
                pr.setIdProveedor(Integer.parseInt(req.getParameter("proveedorSeleccionado")));
                compra.setProveedor(pr);
                
                resultado_insert=dao.insertCompra(compra);
                if (resultado_insert==true) {
                    json_insertar.put("resultado", "exito");
                }else{
                    json_insertar.put("resultado", "error");
                    json_insertar.put("resultado_insertar",resultado_insert);
                }
                
                array_insertar.put(json_insertar);
                resp.getWriter().write(array_insertar.toString());
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
