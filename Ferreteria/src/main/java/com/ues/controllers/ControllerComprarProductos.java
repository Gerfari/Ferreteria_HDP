/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ues.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ues.models.DetalleCompra;
import com.ues.models.Productos;
import com.ues.models.dao.CompraDAO;
import com.ues.models.dao.ProductosDisponiblesDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.sql.Array;
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
@WebServlet(name = "ControllerComprarProductos", urlPatterns = {"/ControllerComprarProductos"})
public class ControllerComprarProductos extends HttpServlet {

    ArrayList<Productos> proSeleccionados = new ArrayList<>();

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
            out.println("<title>Servlet ControllerComprarProductos</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControllerComprarProductos at " + request.getContextPath() + "</h1>");
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
        PrintWriter out = resp.getWriter();
        String filtro = req.getParameter("consultar_datos");
        if (filtro == null) {
            return;
        }
        switch (filtro) {
            case "si_consulta": {
                ProductosDisponiblesDAO pd = new ProductosDisponiblesDAO();
                JSONArray array_productos = new JSONArray();
                JSONObject json_productos = new JSONObject();
                ArrayList<Productos> lsPro = new ArrayList<>();
                lsPro = pd.selectAllProductos();
                String html = "";
                html += "<div class='table-responsive'>";  // Asegura la responsividad
                html += "<table id='tabla_productos' class='table table-bordered nowrap' cellspacing='1px' width='100%'>";  // Ancho corregido
                html += "<thead>";
                html += "<tr>";
                html += "<th>#</th>";
                html += "<th>PRODUCTO</th>";
                html += "<th>DESCRIPCION</th>";
                html += "<th>OPCIONES</th>";
                html += "</tr>";
                html += "</thead>";
                html += "<tbody>";  // Apertura correcta de tbody
                int cont = 0;
                for (Productos objP : lsPro) {
                    cont++;
                    html += "<tr>";
                    html += "<td>" + cont + "</td>";
                    html += "<td>" + objP.getNombreProducto() + "</td>";
                    html += "<td class='descrip'>" + objP.getDescripcion() + "</td>";
                    html += "<td>";
                    html += "<div class='dropdown m-b-10'>";
                    html += "<button class='btn btn-success btn_seleccionar' type='button' id='" + objP.getIdProducto() + "'>Agregar</button>";

                    html += "</div>";
                    html += "</td>";
                    html += "</tr>";
                }
                html += "</tbody>";
                html += "</table>";
                html += "</div>";  // Cierre del div table-responsive

                json_productos.put("resultado", "exito");
                json_productos.put("cuantos", cont);
                json_productos.put("tabla", html);
                array_productos.put(json_productos);
                resp.getWriter().write(array_productos.toString());
                break;
            }

            case "si_producto_especifico": {
                ArrayList<Productos> lsPro = new ArrayList<>();
                JSONArray array_especifico = new JSONArray();
                JSONObject json_especifico = new JSONObject();
                try {
                    ProductosDisponiblesDAO pdao = new ProductosDisponiblesDAO();
                    int idSelec = Integer.parseInt(req.getParameter("id"));
                    Productos pro_espe = pdao.findById(idSelec);
                    lsPro.add(pro_espe);
                    json_especifico.put("resultado", "exito");
                    json_especifico.put("listaProductos", lsPro);
                    json_especifico.put("id_producto", pro_espe.getIdProducto());
                    json_especifico.put("nombre_producto", pro_espe.getNombreProducto());
                } catch (SQLException ex) {
                    json_especifico.put("resultado", "error_sql");
                    json_especifico.put("error_mostrado", ex.getMessage());
                    System.out.println("Error mostrado: " + ex);
                    System.out.println("Error Code error: " + ex.getErrorCode());
                    System.out.println("Error excepcion: " + ex);
                } catch (ClassNotFoundException ex) {
                    json_especifico.put("resultado", "error_class");
                    json_especifico.put("error_mostrado", ex);
                    throw new RuntimeException(ex);
                }
                array_especifico.put(json_especifico);
                resp.getWriter().write(array_especifico.toString());
                break;
            }

            case "guardar_detalle": {
                JSONArray array_compra = new JSONArray();
                JSONObject json_compra = new JSONObject();
                CompraDAO daoC=new CompraDAO();
                int codCompra=daoC.ultimaCompra();
                //RECUPERAMOS EL ARRAY 
                String jsonArray=req.getParameter("jsonArray");
                
                Gson gson = new Gson();
                //EXTRAEMOS EL TIPO DE LISTA QUE ESTAMOS RECUPERANDO
                Type tipoLs=new TypeToken<ArrayList<DetalleCompra>>() {}.getType();
                
                ArrayList<DetalleCompra> lsDetalles=gson.fromJson(jsonArray, tipoLs);
                System.out.println(lsDetalles.size());
                CompraDAO cdao=new CompraDAO();
                boolean resultado=false;
                for (DetalleCompra obj: lsDetalles) {
                    resultado=cdao.registrarDetalle(obj, codCompra);
                    if (resultado==false) {
                        break;
                    }
                }
                
                if(resultado==true){
                    json_compra.put("resultado", "exito");
                }else{
                    json_compra.put("resultado", "error_sql");
                }
                System.out.println("El resultado fue: "+resultado);
                array_compra.put(json_compra);
                resp.getWriter().write(array_compra.toString());
                
                break;
            }
            
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
