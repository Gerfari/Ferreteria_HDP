$(document).ready(function () {
    // Cargar clientes desde la base
    loadProuctos();

    // Cargar clientes de la base
    function loadProuctos() {
        $.ajax({
            url: "Productos",
            type: "GET",
            dataType: "json",
            success: function (productos) {
                // Limpiar la tabla
                $("#productoList").empty();

                // Agregar clientes a la tabla
                productos.forEach(function (producto) {
                    var row = $("<tr>");
                    row.append("<td class='row-data text-center'>" + producto.nombre + "</td>");
                    row.append("<td class='row-data text-center'>" +  producto.descripcion + "</td>");
                    row.append("<td class='row-data text-center'>" +  producto.categoria + "</td>");
                    row.append("<td class='row-data text-center'>" +  producto.existencia + "</td>");
           
                    $("#productoList").append(row);                   
                });
            }
        });
    }

});
