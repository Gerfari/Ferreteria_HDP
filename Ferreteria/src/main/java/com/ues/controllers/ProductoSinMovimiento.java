import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
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
    private static final DecimalFormat df = new DecimalFormat("#.00");

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
            int contador=0;
            double totalGlobal = 0.0; // Variable para almacenar el total global
            for (Productos producto : productos) {
                for (DetalleCompra detalle : producto.getDetallesCompra()) {
                    double total = detalle.getExistencia() * detalle.getPrecio();
                    totalGlobal += total; // Sumar al total global
                    contador++;
                    
                    tabla.append("<tr>");
                    tabla.append("<td>").append(contador).append("</td>");
                    tabla.append("<td>").append(producto.getNombreProducto()).append("</td>");
                    tabla.append("<td>").append(detalle.getExistencia()).append("</td>");
                    tabla.append("<td>").append("$").append(df.format(detalle.getPrecio())).append("</td>");
                    tabla.append("<td>").append("$").append(df.format(total)).append("</td>");
                    tabla.append("</tr>");
                }
            }
            json.put("tabla", tabla.toString());
            json.put("totalGlobal", df.format(totalGlobal));
            jsonArray.put(json);
            response.setContentType("application/json");
            response.getWriter().write(jsonArray.toString());
        }
    }
}
