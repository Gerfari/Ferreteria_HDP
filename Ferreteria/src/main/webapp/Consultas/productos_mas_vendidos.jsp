<%-- 
    Document   : productos_mas_vendidos
    Created on : 2 abr. 2024, 13:30:09
    Author     : Emerson
--%>

<%@page import="com.ues.models.dtos.prod_VendidosDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.ues.models.dtos.prod_VendidosDTO" %>
<%@ page import="com.ues.models.dao.prod_VendidosDAO" %>

<%
    prod_VendidosDAO dao = new prod_VendidosDAO();
    List<prod_VendidosDTO> productos = dao.obtenerProductosMasVendidos();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Productos más vendidos</title>
    <style>
        table {
            border-collapse: collapse;
            width: 50%;
            margin: 20px auto;
        }
        th, td {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
    <h1>Productos más vendidos</h1>
    <table>
        <tr>
            <th>Nombre del Producto</th>
            <th>Total Vendido</th>
        </tr>
        <% for(prod_VendidosDTO producto : productos) { %>
        <tr>
            <td><%= producto.getNombreProducto() %></td>
            <td><%= producto.getTotalVendido() %></td>
        </tr>
        <% } %>
    </table>
    
    <!-- Mensajes de depuración -->
    <% 
    if (productos.isEmpty()) {
        out.println("<p>No se encontraron productos más vendidos.</p>");
    } else {
        for (prod_VendidosDTO producto : productos) {
            out.println("<p>Nombre: " + producto.getNombreProducto() + ", Total vendido: " + producto.getTotalVendido() + "</p>");
        }
    }
    %>
</body>
<script src="../js/prod_Vendidos.js" type="text/javascript"></script>
</html>
