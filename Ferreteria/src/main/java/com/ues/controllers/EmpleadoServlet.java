// EmpleadoServlet.java
package com.ues.controllers;

import com.ues.models.Empleados;
import com.ues.models.Roles;
import com.ues.models.dao.EmpleadoDAO;
import com.ues.utils.Encriptar;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet(name = "EmpleadoServlet", urlPatterns = {"/EmpleadoServlet"})
public class EmpleadoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private EmpleadoDAO empleadoDAO;

    public void init() {
        try {
            empleadoDAO = new EmpleadoDAO();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadoServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EmpleadoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadoServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EmpleadoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("aplication/json;charset=utf-8");

        PrintWriter out = response.getWriter();
        String opcion = request.getParameter("consultar_datos");
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

        switch (opcion) {

            case "si_registro":
                String dui = request.getParameter("duiEmpleado");
                String nombre = request.getParameter("nombre");
                String apellido = request.getParameter("apellido");
                String direccion = request.getParameter("direccion");
                String telefono = request.getParameter("telefono");
                String genero = request.getParameter("genero");
                boolean estado = Boolean.parseBoolean(request.getParameter("estado"));
                int idRol = Integer.parseInt(request.getParameter("idRol"));
                String correo = request.getParameter("correo");
                String contraseña = request.getParameter("contraseña");
                String fechaNacimiento = request.getParameter("fechaNacimiento");
                Empleados nuevoEmpleado = new Empleados();

                try {

                    nuevoEmpleado.setFechaNacimiento(formato.parse(fechaNacimiento));//();

                } catch (ParseException ex) {
                }
                Roles rol = new Roles();
                rol.setIdRol(idRol);

                String passAux = Encriptar.getStringMessageDigest(contraseña, Encriptar.SHA256);

                nuevoEmpleado.setDuiEmpleado(dui);
                nuevoEmpleado.setNombreEmpleado(nombre);
                nuevoEmpleado.setApellidoEmpleado(apellido);
                nuevoEmpleado.setDireccionEmpleado(direccion);
                nuevoEmpleado.setTelefonoEmpleado(telefono);
                nuevoEmpleado.setGenero(genero.charAt(0));
                nuevoEmpleado.setEstadoEmpleado(estado);
                nuevoEmpleado.setRol(rol);
                nuevoEmpleado.setCorreo(correo);
                nuevoEmpleado.setContraseña(passAux);

                String resultado = empleadoDAO.insertEmpleado(nuevoEmpleado);

                JSONArray array_insertar = new JSONArray();
                JSONObject json_insertar = new JSONObject();

                json_insertar.put("resultado", resultado);

                array_insertar.put(json_insertar);
                response.getWriter().write(array_insertar.toString());

                break;
            case "si_consulta":
                EmpleadoDAO empleadoDAO = new EmpleadoDAO();
                JSONArray array_compras = new JSONArray();
                JSONObject json_empleado = new JSONObject();

                String el_estado = request.getParameter("estado");
                ArrayList<Empleados> lsEmpleado = new ArrayList<>();
                lsEmpleado = empleadoDAO.selectAllEmpleados();//daoCompra.showCompras();
                String html = "";
                html += "<div class = 'table-responsive'>";
                html += "<table id=\"tabla_Empleados\""
                        + "class=\"table table-bordered dt-responsive nowrap\""//dt-responsive
                        //+ "class=\" table table-striped table-responsive\""

                        // + "class=\"table table-responsive\""  bordered 
                        + "cellspacing=\"0\" width=\"400%\">\n"
                        + "<thead>\n"
                        + "<tr>\n"
                        + "<th> # </th>\n"
                        + "<th>DUI</th>\n"
                        + "<th>NOMBRE </th>\n"
                        + "<th>APELLIDO</th>\n"
                        + "<th>FECHA DE NACIMIENTO</th>\n"
                        + "<th>DIRECCIÓN</th>\n"
                        + "<th>TELÉFONO</th>\n"
                        + "<th>SEXO</th>\n"
                        + "<th>ESTADO</th>\n"
                        + "<th>ROL</th>\n"
                        + "<th>CORREO</th>\n"
                        // + "<th>CONTRASEÑA</th>\n"
                        + "<th>OPCIONES</th>\n"
                        + "</tr>\n"
                        + "</thead>\n"
                        + "<tbody>";
                int cont = 0;
                for (Empleados dto : lsEmpleado) {
                    cont++;
                    html += "<tr>";
                    html += "<td>" + cont + "</td>";
                    html += "<td>" + dto.getDuiEmpleado() + "</td>";
                    html += "<td>" + dto.getNombreEmpleado() + "</td>";
                    html += "<td>" + dto.getApellidoEmpleado() + "</td>";
                    html += "<td>" + dto.getFechaNacimiento() + "</td>";
                    html += "<td>" + dto.getDireccionEmpleado() + "</td>";
                    html += "<td>" + dto.getTelefonoEmpleado() + "</td>";
                    html += "<td>" + dto.getGenero() + "</td>";
                    html += "<td>" + estadoE(dto.isEstadoEmpleado()) + "</td>";
                    html += "<td>" + dto.getRol().getRol() + "</td>";
                    html += "<td>" + dto.getCorreo() + "</td>";
                    // html += "<td>" + dto.getContraseña()+ "</td>";

                    html += "<td>"
                            + "<div class='dropdown m-b-10'>"
                            + "<button class='btn btn-secondary dropdown-toggle'"
                            + "type='button id=dropdownMenuButton' data-toggle='dropdown'  aria-haspopup='true'"
                            + "aria-expanded='false'> Seleccione</button>"
                            + "<div class='dropdown-menu' aria-labelledby='dropdownMenuButton'>"
                            + "<a class='dropdown-item btn_eliminar' data-id='" + dto.getDuiEmpleado() + "' href='javascript:void(0)'>Dar de baja</a>"
                            + "<a class='dropdown-item btn_editar' data-id='" + dto.getDuiEmpleado() + "' href='javascript:void(0)'>Editar</a>"
                            + "</div>"
                            + "</div>"
                            + "</td>"
                            + "</tr>";
                }//CIERRE DLE FOR
                html += "</tbody>\n"
                        + "\t\t </table>";
                html += "</div>";
                json_empleado.put("resultado", "exito");
                json_empleado.put("tabla", html);
                json_empleado.put("cuantos", cont);

                array_compras.put(json_empleado);
                response.getWriter().write(array_compras.toString());

                break;

            case "llenar_combo": {
                JSONArray array_roles = new JSONArray();
                JSONObject json_roles = new JSONObject();

                ArrayList<Roles> lsRoles = new ArrayList<>();
                EmpleadoDAO daoEmple = new EmpleadoDAO();
                lsRoles = daoEmple.comboRoles();
                String comboRoles = "";
                if (!lsRoles.isEmpty()) {
                    for (Roles roles : lsRoles) {
                        comboRoles += "<option value=" + roles.getIdRol() + ">"
                                + roles.getRol() + "</option>";
                    }
                    json_roles.put("resultado", "exito");
                    json_roles.put("roles", comboRoles);
                    array_roles.put(json_roles);
                } else {
                    json_roles.put("resultado", "error");
                }
                response.getWriter().write(array_roles.toString());
            }
            break;

            case "llamar_consulta":
                JSONArray array_especifico = new JSONArray();
                JSONObject json_especifico = new JSONObject();
                EmpleadoDAO empleDAO = null;

                try {
                    empleDAO = new EmpleadoDAO();
                    Empleados res_empleado = empleDAO.findById(request.getParameter("dui"));//.findById(request.getParameter("id"));
                    String tipoGenero = String.valueOf(res_empleado.getGenero());
                    json_especifico.put("resultado", "exito");
                    json_especifico.put("dui", res_empleado.getDuiEmpleado());
                    json_especifico.put("nombre", res_empleado.getNombreEmpleado());
                    json_especifico.put("apellido", res_empleado.getApellidoEmpleado());
                    json_especifico.put("fechaNacimiento", res_empleado.getFechaNacimiento());
                    json_especifico.put("direccion", res_empleado.getDireccionEmpleado());
                    json_especifico.put("telefono", res_empleado.getTelefonoEmpleado());
                    json_especifico.put("genero", tipoGenero);
                    json_especifico.put("estado", estadoEmpl(res_empleado.isEstadoEmpleado()));
                    json_especifico.put("rol", res_empleado.getRol().getIdRol());
                    json_especifico.put("correo", res_empleado.getCorreo());
                    json_especifico.put("contrasenia", res_empleado.getContraseña());

                } catch (SQLException e) {
                    json_especifico.put("resultado", "error_sql");
                    json_especifico.put("error_mostrado", e.getMessage());
                    System.out.println("Error mostrado: " + e);
                    System.out.println("Error Code error: " + e.getErrorCode());
                    throw new RuntimeException();
                } catch (ClassNotFoundException e) {
                    json_especifico.put("resultado", "error_class");
                    json_especifico.put("error_mostrado", e.getMessage());
                    throw new RuntimeException(e);
                }
                array_especifico.put(json_especifico);
                response.getWriter().write(array_especifico.toString());
                break;

            case "si_eliminalo": {
                EmpleadoDAO empleaDAO = new EmpleadoDAO();
                JSONArray array = new JSONArray();
                JSONObject json_elim = new JSONObject();
                try {
                    String resul = "";
                    String idElim = (request.getParameter("dui"));
                    resul = empleaDAO.deleteEmpleado(idElim);
                    if ("exito".equals(resul)) {
                        json_elim.put("resultado", "exito");
                    } else {
                        json_elim.put("resultado", "error_eliminar");
                    }
                } catch (Exception ex) {
                    Logger.getLogger(EmpleadoServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                array.put(json_elim);
                response.getWriter().write(array.toString());
            }

            break;

            case "si_actualizado":

                EmpleadoDAO empleadDAO = new EmpleadoDAO();

                String duiE = request.getParameter("duiEmpleado");
                String nombreE = request.getParameter("nombre");
                String apellidoE = request.getParameter("apellido");
                String direccionE = request.getParameter("direccion");
                String telefonoE = request.getParameter("telefono");
                String generoE = request.getParameter("genero");
                boolean estadoE = Boolean.parseBoolean(request.getParameter("estado"));
                int idRolE = Integer.parseInt(request.getParameter("idRol"));
                String correoE = request.getParameter("correo");
                String contraseñaE = request.getParameter("contraseña");
                String fechaNacimientoE = request.getParameter("fechaNacimiento");
                Empleados nuevoEmpleadoE = new Empleados();

                try {

                    nuevoEmpleadoE.setFechaNacimiento(formato.parse(fechaNacimientoE));//();

                } catch (ParseException ex) {
                }
                Roles rolE = new Roles();
                rolE.setIdRol(idRolE);

                String pasAux = Encriptar.getStringMessageDigest(contraseñaE, Encriptar.SHA256);

                nuevoEmpleadoE.setDuiEmpleado(duiE);
                nuevoEmpleadoE.setNombreEmpleado(nombreE);
                nuevoEmpleadoE.setApellidoEmpleado(apellidoE);
                nuevoEmpleadoE.setDireccionEmpleado(direccionE);
                nuevoEmpleadoE.setTelefonoEmpleado(telefonoE);
                nuevoEmpleadoE.setGenero(generoE.charAt(0));
                nuevoEmpleadoE.setEstadoEmpleado(estadoE);
                nuevoEmpleadoE.setRol(rolE);
                nuevoEmpleadoE.setCorreo(correoE);
                nuevoEmpleadoE.setContraseña(pasAux);

                String resultadoE = empleadDAO.updateEmpleado(nuevoEmpleadoE, duiE);

                JSONArray array_editar = new JSONArray();
                JSONObject json_editar = new JSONObject();

                json_editar.put("resultado", resultadoE);

                array_editar.put(json_editar);
                response.getWriter().write(array_editar.toString());
                break;
        }

    }

    public String estadoE(boolean estadoEmple) {
        if (estadoEmple) {
            return "activo";
        } else {
            return "inactivo";
        }

    }

    public String estadoEmpl(boolean estadoEmplead) {
        if (estadoEmplead) {
            return "true";
        } else {
            return "false";
        }

    }

}
