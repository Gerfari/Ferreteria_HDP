$(document).ready(function () {
    // Cargar clientes desde la base
    loadClientes();

    // Cargar clientes de la base
    function loadClientes() {
        $.ajax({
            url: "Clientes",
            type: "GET",
            dataType: "json",
            success: function (clientes) {
                // Limpiar la tabla
                $("#clientesList").empty();

                // Agregar clientes a la tabla
                clientes.forEach(function (cliente) {
                    var row = $("<tr>");
                    row.append("<td class='row-data text-center'>" + cliente.dui + "</td>");
                    row.append("<td class='row-data text-center'>" + cliente.nombre  + "</td>");
                    row.append("<td class='row-data text-center'>" + cliente.cantidad  + "</td>");
                    row.append("<td class='row-data text-center'>" + cliente.monto  + "</td>");
           
                    $("#clientesList").append(row);                   
                });
            }
        });
    }

});
