package com.ues.models.dao;
import com.ues.models.Conexion;
import com.ues.models.dtos.InformeVentasTrimestresDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 *
 * @author kevin
 */
public class InformeVentasDAO {
    private Conexion conexion;
    private Connection accesoDB;
    private PreparedStatement ps;
    private ResultSet rs;
    
    // Array para mapear el número del mes a su nombre correspondiente
    private static final String[] NOMBRES_MESES = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                                                    "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

    private static final String INFORME_VENTAS_TRIMESTRES = "SELECT DATE_PART('month', v.fecha_venta) AS mes, "
            + "COUNT(*) AS ventas_por_mes, "
            + "ROUND(SUM(dv.cantidad * dv.precio_venta)::numeric, 2) AS total_ventas "
            + "FROM Ventas v "
            + "JOIN Detalle_Ventas dv ON v.id_venta = dv.id_venta "
            + "WHERE DATE_PART('year', v.fecha_venta) = ? "
            + "AND DATE_PART('month', v.fecha_venta) BETWEEN ? AND ? "
            + "GROUP BY DATE_PART('month', v.fecha_venta) "
            + "ORDER BY total_ventas DESC;";

    public InformeVentasDAO() throws SQLException, ClassNotFoundException {
        this.conexion = new Conexion();
    }

    public ArrayList<InformeVentasTrimestresDTO> selectInformeVentasTrimestres(int año, int mesInicio, int mesFin) {
        ArrayList<InformeVentasTrimestresDTO> listaInforme = new ArrayList<>();
        try {
            this.accesoDB = this.conexion.getConexion();
            this.ps = this.accesoDB.prepareStatement(INFORME_VENTAS_TRIMESTRES);
            this.ps.setInt(1, año);
            this.ps.setInt(2, mesInicio);
            this.ps.setInt(3, mesFin);
            this.rs = this.ps.executeQuery();

            while (this.rs.next()) {
                InformeVentasTrimestresDTO informe = new InformeVentasTrimestresDTO();
                // Obtener el número del mes
                int numeroMes = rs.getInt("mes");
                // Obtener el nombre del mes utilizando el array
                String nombreMes = obtenerNombreMes(numeroMes);
                informe.setMes(nombreMes);
                informe.setVentas_por_mes(rs.getInt("ventas_por_mes"));
                informe.setTotal_ventas(rs.getDouble("total_ventas"));
                
                listaInforme.add(informe);
            }
            this.conexion.cerrarConexiones();

        } catch (SQLException ex) {
            ex.printStackTrace(); // O manejo de excepción adecuado
        }
        return listaInforme;
    }
    
    // Método auxiliar para obtener el nombre del mes
    private String obtenerNombreMes(int numeroMes) {
        if (numeroMes >= 1 && numeroMes <= 12) {
            return NOMBRES_MESES[numeroMes - 1]; // Restamos 1 para ajustar al índice del array
        } else {
            return "Mes no válido"; // Manejar caso de mes no válido
        }
    }
}
