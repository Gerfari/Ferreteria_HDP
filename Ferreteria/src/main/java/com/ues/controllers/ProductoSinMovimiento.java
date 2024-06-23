import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import com.ues.models.dao.ProductosNoCompradosDAO;
import com.ues.models.Productos;
import com.ues.models.DetalleCompra;

@WebServlet("/ProductoSinMovimiento")
public class ProductoSinMovimiento extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String consultarDatos = request.getParameter("consultar_datos");
        if ("si_consulta".equals(consultarDatos)) {
            ProductosNoCompradosDAO dao = new ProductosNoCompradosDAO();
            List<Productos> productos = dao.obtenerProductosNoComprados();
            JSONArray jsonArray = new JSONArray();
            JSONObject json = new JSONObject();
            json.put("resultado", "exito");
            StringBuilder tabla = new StringBuilder();
            double totalGlobal = 0.0; // Variable para almacenar el total global
            tabla.append("<table id='tabla_producto' class='table table-bordered table-striped'>");
            tabla.append("<thead><tr><th>ID</th><th>Producto</th><th>Existencia</th><th>Precio</th><th>Total</th></tr></thead><tbody>");
            for (Productos producto : productos) {
                for (DetalleCompra detalle : producto.getDetallesCompra()) {
                    double total = detalle.getExistencia() * detalle.getPrecio();
                    totalGlobal += total; // Sumar al total global
                    
                    tabla.append("<tr>");
                    tabla.append("<td>").append(producto.getIdProducto()).append("</td>");
                    tabla.append("<td>").append(producto.getNombreProducto()).append("</td>");
                    tabla.append("<td>").append(detalle.getExistencia()).append("</td>");
                    tabla.append("<td>").append(detalle.getPrecio()).append("</td>");
                    tabla.append("<td>").append(total).append("</td>");
                    tabla.append("</tr>");
                }
            }
            tabla.append("</tbody>");
            tabla.append("<tfoot><tr><th colspan='4'>Total Global</th><th>").append(totalGlobal).append("</th></tr></tfoot>");
            tabla.append("</table>");
            json.put("tabla", tabla.toString());
            jsonArray.put(json);
            response.setContentType("application/json");
            response.getWriter().write(jsonArray.toString());
        }
    }
}
