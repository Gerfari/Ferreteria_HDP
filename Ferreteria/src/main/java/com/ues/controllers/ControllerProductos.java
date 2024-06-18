package com.ues.controllers;

import com.ues.models.Categorias;
import com.ues.models.Productos;
import com.ues.models.dao.ProductosDAO;
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
@WebServlet(name = "ControllerProductos", urlPatterns = {"/ControllerProductos"})
public class ControllerProductos extends HttpServlet {

    private ArrayList<Productos> productosList;

    private Productos pr = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ControllerProductos</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControllerProductos at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out = resp.getWriter();
        String filtro = req.getParameter("consultar_datos");

        System.out.println(filtro);
        if (filtro == null) {
            return;
        }
        switch (filtro) {
            //Cargar categorias al Combobox
            case "llenar_combo_categorias":
                JSONArray array_categorias = new JSONArray();
                JSONObject json_categorias = new JSONObject();
                try {
                    ArrayList<Categorias> listaCat = new ArrayList<>();
                    ProductosDAO daocat = new ProductosDAO();
                    listaCat = daocat.comboCategorias();
                    json_categorias.put("resultado", "exito");
                    json_categorias.put("categorias", listaCat);
                    array_categorias.put(json_categorias);
                    resp.getWriter().write(array_categorias.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            //Insertar registros
            case "si_registro":
                JSONArray array_productos = new JSONArray();
                JSONObject json_productos = new JSONObject();
                String resultado_insert = "";
                Productos producto = new Productos();

                try {
                    ProductosDAO p = new ProductosDAO();
                    producto.setIdProducto(Integer.parseInt(req.getParameter("id_producto")));
                    producto.setNombreProducto(req.getParameter("nombre_producto"));
                    producto.setDescripcion(req.getParameter("descripcion"));
                    producto.setEstadoProducto(Boolean.parseBoolean(req.getParameter("estado_producto")));
                    Categorias cat = new Categorias();
                    cat.setIdCategoria(Integer.parseInt(req.getParameter("id_categoria")));
                    producto.setCategoria(cat);

                    resultado_insert = p.insertarProducto(producto);
                    if ("exito".equals(resultado_insert)) {
                        json_productos.put("resultado", "exito");
                        json_productos.put("id_producto", producto.getIdProducto());
                        json_productos.put("nombre_producto", producto.getNombreProducto());
                        json_productos.put("descripcion", producto.getDescripcion());
                        json_productos.put("estado_producto", producto.isEstadoProducto());
                        json_productos.put("id_categoria", producto.getCategoria().getIdCategoria());
                    } else {
                        json_productos.put("resultado", "error");
                        json_productos.put("resultado_insertar", resultado_insert);
                    }
                } catch (ClassNotFoundException | SQLException e) {
                    json_productos.put("resultado", "error_sql");
                    json_productos.put("error_mostrado", e.getMessage());
                    throw new RuntimeException(e);
                }
                array_productos.put(json_productos);
                resp.getWriter().write(array_productos.toString());
                break;
            //Mostrar registros
            case "si_consulta":
                JSONArray array_producto = new JSONArray();
                JSONObject json_producto = new JSONObject();
                String html = "";
                String el_estado = req.getParameter("estado");
                try {
                    ProductosDAO dao = new ProductosDAO();
                    this.productosList = new ArrayList();
                    this.productosList = dao.SelectAllProductos(Integer.valueOf(el_estado), "todos");
                    //Creando tabla
                    html += "<table id=\"tabla_productos\""
                            + "class=\"table table-bordered dt-responsive nowrap\""
                            + "cellspacing=\"0\" width=\"100%\">\n"
                            + "<thead>\n"
                            + "<tr>\n"
                            + "<th>#</th>\n"
                            + "<th>PRODUCTO</th>\n"
                            + "<th>DESCRIPCIÓN</th>\n"
                            + "<th>ESTADO</th>\n"
                            + "<th>CATEGORÍA</th>\n"
                            + "<th>OPCIÓN</th>\n"
                            + "</tr>\n"
                            + "</thead>\n"
                            + "<tbody>";
                    for (Productos objproducto : productosList) {
                        html += "<tr>";
                        html += "<td>" + objproducto.getIdProducto() + "</td>";
                        html += "<td style='white-space: pre-wrap;'>" + objproducto.getNombreProducto() + "</td>";
                        html += "<td style='white-space: pre-wrap;'>" + objproducto.getDescripcion() + "</td>";
                        html += "<td style='white-space: pre-wrap;'>" + (objproducto.isEstadoProducto() ? "Disponible" : "No Disponible") + "</td>";
                        html += "<td style='white-space: pre-wrap;'>" + objproducto.getCategoria().getCategoria() + "</td>";
                        html += "<td>";
                        html += "<div class='dropdown m-b-10'>";
                        html += "<button class='btn btn-primary btn_editar' type='button' data-id='" + objproducto.getIdProducto() + "'>Modificar</button>";
                        html += "</div>";
                        html += "</td>";
                        html += "</tr>";
                    }
                    html += "</tbody>\n\t\t</table>";
                    json_producto.put("resultado", "exito");
                    json_producto.put("tabla", html);
                    json_producto.put("cuantos", productosList.size());
                    System.out.println("que tiene" + html);
                } catch (SQLException e) {
                    json_producto.put("resultado", "error sql");
                    json_producto.put("error", e.getMessage());
                    json_producto.put("code error", e.getErrorCode());
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    json_producto.put("resultado", "class not found");
                    json_producto.put("error", e.getMessage());
                    throw new RuntimeException(e);
                }
                array_producto.put(json_producto);
                resp.getWriter().write(array_producto.toString());
                break;
            //Actualizar Productos
            case "si_actualizalo":
                JSONArray array_actulizar = new JSONArray();
                JSONObject json_actualizar = new JSONObject();
                this.pr = new Productos();
                String result_actualizar = "";
                 {
                    try {
                        ProductosDAO dao = new ProductosDAO();
                        this.pr.setIdProducto(Integer.parseInt(req.getParameter("id_producto")));
                        this.pr.setNombreProducto(req.getParameter("nombre_producto"));
                        this.pr.setDescripcion(req.getParameter("descripcion"));
                        this.pr.setEstadoProducto(Boolean.parseBoolean(req.getParameter("estado_producto")));
                        Categorias categoria = new Categorias();
                        categoria.setIdCategoria(Integer.parseInt(req.getParameter("id_categoria")));
                        this.pr.setCategoria(categoria);

                        result_actualizar = dao.updateProducto(this.pr, Integer.parseInt(req.getParameter("id_producto")));

                        if (result_actualizar == "exito") {
                            json_actualizar.put("resultado", "exito");
                            json_actualizar.put("id_producto", this.pr.getIdProducto());
                            json_actualizar.put("nombre_producto", this.pr.getNombreProducto());
                            json_actualizar.put("descripcion", this.pr.getDescripcion());
                            json_actualizar.put("estado_producto", this.pr.isEstadoProducto());
                            json_actualizar.put("id_categoria", this.pr.getCategoria().getIdCategoria());
                        } else {
                            json_actualizar.put("resultado", "error_sql");
                            json_actualizar.put("resultado_actualizar", result_actualizar);
                        }
                    } catch (SQLException ex) {
                        json_actualizar.put("resultado", "error_sql");
                        json_actualizar.put("error_mostrado", ex.getMessage());
                        System.out.println("Error Code error: " + ex.getErrorCode());
                    } catch (ClassNotFoundException ex) {
                        json_actualizar.put("resultado", "error_class");
                        json_actualizar.put("error_mostrado", ex.getMessage());
                    }
                }
                array_actulizar.put(json_actualizar);
                resp.getWriter().write(array_actulizar.toString());
                break;

            case "si_producto_especifico":
                JSONArray array_especifico = new JSONArray();
                JSONObject json_especifico = new JSONObject();
                ProductosDAO daoP = null;
                 {
                    try {
                        daoP = new ProductosDAO();
                        int idpr = Integer.parseInt(req.getParameter("id"));

                        Productos res_indiv = daoP.findById(idpr);

                        json_especifico.put("resultado", "exito");
                        json_especifico.put("id_producto", res_indiv.getIdProducto());
                        json_especifico.put("nombre_producto", res_indiv.getNombreProducto());
                        json_especifico.put("descripcion", res_indiv.getDescripcion());
                        json_especifico.put("estado_producto", res_indiv.isEstadoProducto());
                        json_especifico.put("id_categoria", res_indiv.getCategoria().getIdCategoria());
                    } catch (SQLException ex) {
                        json_especifico.put("resultado", "error_sql");
                        json_especifico.put("error_mostrado", ex.getMessage());
                        System.out.println("Error Code error: " + ex.getErrorCode());
                    } catch (ClassNotFoundException ex) {
                        json_especifico.put("resultado", "error_class");
                        json_especifico.put("error_mostrado", ex.getMessage());
                    }
                }
                array_especifico.put(json_especifico);
                resp.getWriter().write(array_especifico.toString());
                break;
        }
    }
}
