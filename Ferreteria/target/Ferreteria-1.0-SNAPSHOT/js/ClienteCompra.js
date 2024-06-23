$(document).ready(function () {

    $('#buscarDUI').on('input', function () {
        var value = $(this).val();
        var cleanedValue = value.replace(/[^0-9-]/g, '');
        $(this).val(cleanedValue);
    });
    
    function load() {
        var dui = $("#buscarDUI").val().trim();
        
        if (dui === '') {
            Swal.fire('Error', 'SE DEBE INGRESAR UN NUMERO DE DUI', 'error');
            return; 
        }

        $.ajax({
            url: "..//ClienteCompraServlet",
            type: "GET",
            data: {dui: dui},
            dataType: "json",
            success: function (clientes) {

                $("#clientesList").empty();
                
                var nombre = "";
                clientes.forEach(function (cliente) {
                    nombre = cliente.nombreCliente;
                    var row = $("<tr>");
                    row.append("<td class='row-data text-center'>" + cliente.nombreEmpleado + "</td>");
                    row.append("<td class='row-data text-center'>" + cliente.producto + "</td>");
                    row.append("<td class='row-data text-center'>" + cliente.fecha + "</td>");
                    var precioComas = cliente.precio >= 1000 ? cliente.precio.toLocaleString() : cliente.precio;
                    row.append("<td class='row-data text-center'>$ " + precioComas + "</td>");
                    $("#clientesList").append(row);
                });
                $("#Cuantos").text(nombre);

                $('#tabla').DataTable();
            },
            error: function (xhr, status, error) {
                console.error("Error al cargar empleados:", error);
            }
        });
    }

    $('#btnBuscar').click(function () {
        load();
    });
});
