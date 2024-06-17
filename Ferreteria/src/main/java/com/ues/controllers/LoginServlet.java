package com.ues.controllers;

import com.google.gson.Gson;
import com.ues.models.Empleados;
import com.ues.models.dao.LoginDao;
import com.ues.utils.Encriptar;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener parámetros del formulario de inicio de sesión
        String usuario = request.getParameter("usuario");
        String contrasenia = request.getParameter("contrasenia");
        
       // usar cuando ya hayan claves encriptadas en la base
        
       String cadena = Encriptar.getStringMessageDigest(contrasenia, Encriptar.SHA256);
       
        boolean autenticado = false;
        // Instanciar el DAO para verificar las credenciales
        LoginDao loginDao = new LoginDao();
        Empleados emp=loginDao.verificarCredenciales(usuario, cadena);
        if (emp!=null) {
            autenticado=true;
        }

        // Crear un objeto JSON de respuesta
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("autenticado", autenticado);
        //UN OBJETO JSON DENTRO DE OTRO OBJETO JSON
        Gson gson = new Gson();
        JSONObject empJson= new JSONObject(gson.toJson(emp));
          
        System.out.println("EL JSON TIENE "+empJson);
        jsonResponse.put("empleado", empJson);
        // Configurar la respuesta HTTP
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(jsonResponse.toString());
        out.flush();
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
