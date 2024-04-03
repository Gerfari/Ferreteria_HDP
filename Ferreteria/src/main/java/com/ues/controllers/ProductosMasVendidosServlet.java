/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ues.controllers;

import com.ues.models.dao.prod_VendidosDAO;
import com.ues.models.dtos.prod_VendidosDTO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Emerson
 */
@WebServlet(name = "ProductosMasVendidosServlet", urlPatterns = {"/productosMasVendidos"})
public class ProductosMasVendidosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prod_VendidosDAO dao = new prod_VendidosDAO();
        List<prod_VendidosDTO> productos = dao.obtenerProductosMasVendidos();
        request.setAttribute("productos", productos);
        request.getRequestDispatcher("/productos_mas_vendidos.jsp").forward(request, response);
    }

}
