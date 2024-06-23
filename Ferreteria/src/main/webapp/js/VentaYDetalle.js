$(document).ready(function () {
    load();
    function load() {
        $.ajax({
            url: "../VentaYDetalleServlet",
            type: "GET",
            dataType: "json",
            success: function (ventas) {
                console.log(ventas); // Mostrar datos recibidos en la consola
                $("#ventasList").empty();
                var i = 1;
                ventas.forEach(function (venta) {
                    var row = $("<tr>");
                    row.append("<td class='row-data text-center'>" + i + "</td>");
                    row.append("<td class='row-data text-center'>" + venta.fechaVenta + "</td>");
                    row.append("<td class='row-data text-center'>" + venta.empleado + "</td>");
                    row.append("<td class='row-data text-center'>" + venta.cliente + "</td>");
                    row.append("<td class='row-data text-center'><button class='btn btn-primary' onclick='verDetalle(" + venta.idVenta+ ")'>Ver Detalle</button></td>");
                    i++;
                    $("#ventasList").append(row);
                });
                $('#tabla').DataTable();
            },
            error: function (xhr, status, error) {
                console.error("Error al cargar el reporte de ventas:", error);
            }
        });
    }
    
       $(document).on("click", "#cerrarmodal", function (e) {
        e.preventDefault();
        $("#md_ver_detalle").modal("hide");
    });
    
      $(document).on("click", "#btn_cerrar", function (e) {
        e.preventDefault();
        $("#md_ver_detalle").modal("hide");
    });
   
});
 function verDetalle(idVenta) {
     
    $.ajax({
        url: "..//DetalleVentaServlet",
        type: "GET",
        data: { idVenta: idVenta },
        dataType: "json",
        success: function (detalleVenta) {
            console.log(detalleVenta);
            console.log(detalleVenta[0].fechaVenta);
            console.log(detalleVenta[0].empleado);
            console.log(detalleVenta[0].cliente);
           var cantidad=0,total=0;
            $("#detalleVentaTableBody").empty();
            detalleVenta.forEach(function (detalle) {
                var row = $("<tr>");
                row.append("<td>" + detalle.producto + "</td>");
                row.append("<td>" + detalle.cantidad + "</td>");
                row.append("<td>" +"$ "+ detalle.precio + "</td>");
                row.append("<td>" +"$ "+ detalle.total + "</td>");
                cantidad=cantidad+detalle.cantidad;
                total=total+detalle.total;
                $("#detalleVentaTableBody").append(row);
            });
            $("#fechaRealizada").val(detalleVenta[0].fechaVenta).attr("readonly", true);
            $("#vendedor").val(detalleVenta[0].empleado).attr("readonly", true);
            $("#cliente").val(detalleVenta[0].cliente).attr("readonly", true);

// Formatear el total
            var formattedTotal = total >= 1000 ? total.toLocaleString() : total;
            $("#totalGastado").val("$ " + formattedTotal).attr("readonly", true);

            $("#cantidadVendido").val(cantidad).attr("readonly", true);

            $("#md_ver_detalle").modal('show');

        },
        error: function (xhr, status, error) {
            console.error("Error al obtener los detalles de la venta:", error);
        }
    });
}
