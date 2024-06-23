$(document).ready(function () {
 
    loadReporte();

    $('#btnBuscar').click(function () {
        loadReporte();
    });

    function loadReporte() {
        var fechaInicio = $("#fechainicio").val();
        var fechaFin = $("#fechafin").val();

        $.ajax({
            url: "..//ReporteVentasServlet", 
            type: "GET",
            data: {fechainicio: fechaInicio, fechafin: fechaFin},
            dataType: "json",
            success: function (reporte) {
                $("#ventasList").empty();

                reporte.forEach(function (venta) {
                    var row = $("<tr>");
                    row.append("<td class='row-data text-center'>" + venta.fecha + "</td>");
                    row.append("<td class='row-data text-center'>" + venta.nombreCliente + "</td>");
                    row.append("<td class='row-data text-center'>" + venta.nombreEmpleado + "</td>");
                    row.append("<td class='row-data text-center'>" + venta.producto + "</td>");
                    row.append("<td class='row-data text-center'>" + venta.cantidad + "</td>");
                    row.append("<td class='row-data text-center'>" + "$ " + (venta.precio >= 1000 ? venta.precio.toLocaleString() : venta.precio) + "</td>");
                    row.append("<td class='row-data text-center'>" + "$ " + venta.monto + "</td>");
                    

                    $("#ventasList").append(row);
                });

              
                $('#tabla').DataTable();
            },
            error: function (xhr, status, error) {
                console.error("Error al cargar el reporte de ventas:", error);
            }
        });
    }
});
