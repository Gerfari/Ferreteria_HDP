/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ues.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ues.models.Cliente;
import com.ues.models.DetalleCompra;
import com.ues.models.DetalleVenta;
import com.ues.models.Empleados;
import com.ues.models.Ventas;
import com.ues.models.dao.ProductosDisponiblesDAO;
import com.ues.models.dao.VentaDAO;
import com.ues.models.dtos.DetalleProductoDTO;
import com.ues.models.dtos.VentasIntervaloFecha;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.sql.SQLException;
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
    SimpleDateFormat formatoF = new SimpleDateFormat("yyyy-MM-dd");
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
                                + "<th>#</th>\n"
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
                            html += "<td>$" + montoFormateado + "</td>";
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
                
                case "llenar_combo_cliente":
                JSONArray array_clientes = new JSONArray();
                JSONObject json_clientes = new JSONObject();
                //Gson gson = new Gson();
                String estado = req.getParameter("estado");
                ArrayList<Cliente> lsClientes = new ArrayList<>();
                 {
                    try {
                        //VentaDAO daoVenta = new VentaDAO();
                        lsClientes = daoVenta.listaClientes();
                        //CONVERTIENDO LOS DATOS DIRECTAMENTE EN JSON
                        //String fechasJson = gson.toJson(lsFechas);
                        //resp.getWriter().write(fechasJson);
                    } catch (SQLException ex) {
                        Logger.getLogger(ControllerVenta.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                //OTRA MANERA DE HACERLO
                json_clientes.put("resultado", "exito");
                json_clientes.put("clientes", lsClientes);

                array_clientes.put(json_clientes);
                resp.getWriter().write(array_clientes.toString());
                break;
                case "si_producto_especifico": {
                ArrayList<DetalleCompra> lsPro = new ArrayList<>();
                JSONArray array_especifico = new JSONArray();
                JSONObject json_especifico = new JSONObject();
                try {
                    ProductosDisponiblesDAO pdao = new ProductosDisponiblesDAO();
                    int idSelec = Integer.parseInt(req.getParameter("id"));
                    DetalleCompra pro_espe = pdao.buscarProducto(idSelec);
                    lsPro.add(pro_espe);
                    json_especifico.put("resultado", "exito");
                    json_especifico.put("listaProductos", lsPro);
                    json_especifico.put("id_producto", pro_espe.getProducto().getIdProducto());
                    json_especifico.put("nombre_producto", pro_espe.getProducto().getNombreProducto());
                    json_especifico.put("existencia", pro_espe.getExistencia());
                    json_especifico.put("id_detalle_compra", pro_espe.getIdDetalleCompra());
                    json_especifico.put("precio", pro_espe.getPrecio());
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
                case "mostrar_productos":
                ProductosDisponiblesDAO pd = new ProductosDisponiblesDAO();
                JSONArray array_productos = new JSONArray();
                JSONObject json_productos = new JSONObject();
                ArrayList<DetalleProductoDTO> lsPro = new ArrayList<>();
                lsPro = pd.ProductosVendidos();
                String html1 = "";
                html1 += "<div class='table-responsive'>";  // Asegura la responsividad
                html1 += "<table id='tabla_productos' class='table table-bordered nowrap' cellspacing='1px' width='100%'>";  // Ancho corregido
                html1 += "<thead>";
                html1 += "<tr>";
                html1 += "<th>#</th>";
                html1 += "<th>PRODUCTO</th>";
                html1 += "<th>EXISTENCIAS</th>";
                html1 += "<th>PRECIO</th>";
                html1 += "<th>OPCIÓN</th>";
                html1 += "</tr>";
                html1 += "</thead>";
                html1 += "<tbody>";  // Apertura correcta de tbody
                int cont1 = 0;
                for (DetalleProductoDTO objP : lsPro) {
                    cont1++;
                    html1 += "<tr>";
                    html1 += "<td>" + cont1 + "</td>";
                    html1 += "<td>" + objP.getNombre_producto() + "</td>";
                    html1 += "<td class='descrip'>" + objP.getExistencia() + "</td>";
                    html1 += "<td class='descrip'>$" + objP.getPrecio() + "</td>";
                    html1 += "<td>";
                    html1 += "<div class='dropdown m-b-10'>";
                    html1 += "<button class='btn btn-success btn_seleccionar' type='button' id='" + objP.getId_detalle_compra() + "'>Agregar</button>";

                    html1 += "</div>";
                    html1 += "</td>";
                    html1 += "</tr>";
                }
                html1 += "</tbody>";
                html1 += "</table>";
                html1 += "</div>";  // Cierre del div table-responsive

                json_productos.put("resultado", "exito");
                json_productos.put("cuantos", cont1);
                json_productos.put("tabla", html1);
                array_productos.put(json_productos);
                resp.getWriter().write(array_productos.toString());
                break;
                
                case "si_registro":
                JSONArray array_insertar = new JSONArray();
                JSONObject json_insertar = new JSONObject();

                boolean resultado_insert = false;
                Ventas ventas = new Ventas();

                VentaDAO dao = new VentaDAO();

                //OBTENGO LA FECHA Y LE DOY FORMATO
                String fechaventa = req.getParameter("fechaventa");
                try {
                    ventas.setFechaVenta(formatoF.parse(fechaventa));

                } catch (ParseException ex) {
                    Logger.getLogger(ControllerCompra.class.getName()).log(Level.SEVERE, null, ex);
                }
                Empleados emp = new Empleados();
                emp.setDuiEmpleado(req.getParameter("empleado"));
                ventas.setEmpleado(emp);

                Cliente pr = new Cliente();
                pr.setDuiCliente(req.getParameter("clienteSeleccionado"));
                ventas.setCliente(pr);

                resultado_insert = dao.insertVenta(ventas);
                if (resultado_insert == true) {
                    json_insertar.put("resultado", "exito");
                } else {
                    json_insertar.put("resultado", "error");
                    json_insertar.put("resultado_insertar", resultado_insert);
                }

                array_insertar.put(json_insertar);
                resp.getWriter().write(array_insertar.toString());
                break;
                case "guardar_detalle": {
                JSONArray array_vent = new JSONArray();
                JSONObject json_vent = new JSONObject();
                VentaDAO daoV = new VentaDAO();
                int codVenta = daoV.ultimaVenta();
                //RECUPERAMOS EL ARRAY 
                String jsonArray = req.getParameter("jsonArray");

                Gson gson = new Gson();
                //EXTRAEMOS EL TIPO DE LISTA QUE ESTAMOS RECUPERANDO
                Type tipoLs = new TypeToken<ArrayList<DetalleVenta>>() {
                }.getType();

                ArrayList<DetalleVenta> lsDetalles = gson.fromJson(jsonArray, tipoLs);
                System.out.println(lsDetalles.size());
                VentaDAO cdao = new VentaDAO();
                boolean resultado = false;
                int actualizacion = 0;
                for (DetalleVenta obj : lsDetalles) {
                    resultado = cdao.registrarDetalle(obj, codVenta);
                    if (resultado == false) {
                        json_vent.put("resultado", "error_sql");

                    } else {
                        cdao.actualizarExistencia(obj);
                        if (actualizacion < 0) {
                            json_vent.put("resultado", "error_sql");
                        }
                    }
                }

                if (resultado == true) {
                    json_vent.put("resultado", "exito");
                } else {
                    json_vent.put("resultado", "error_sql");
                }
                System.out.println("El resultado fue: " + resultado);
                array_vent.put(json_vent);
                resp.getWriter().write(array_vent.toString());

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
