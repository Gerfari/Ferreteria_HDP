$(document).ready(function () {
    
    loadProuctos();

   
    function loadProuctos() {
        $.ajax({
            url: "Productos",
            type: "GET",
            dataType: "json",
            success: function (productos) {
                // Limpiar la tabla
                $("#productoList").empty();
                 $("#CuantosProductos").text(productos.length);

                
                productos.forEach(function (producto) {
                    var row = $("<tr>");
                    row.append("<td class='row-data text-center'>" + producto.nombre + "</td>");
                    row.append("<td class='row-data text-center'>" +  producto.descripcion + "</td>");
                    row.append("<td class='row-data text-center'>" +  producto.categoria + "</td>");
                    row.append("<td class='row-data text-center'>" +  producto.existencia + "</td>");
           
                    $("#productoList").append(row); 
                     
                });
                $('#tabla').DataTable();
            },
            error: function (xhr, status, error) {
                console.error("Error al cargar clientes:", error);
            }
        });
    }

});
