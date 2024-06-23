$(function() {
    console.log("Entro al javascript");
    cargarTablaProductos();
});

function cargarTablaProductos(estado = 1) {
    var datos = {"consultar_datos": "si_consulta", "estado": estado}; // Creamos un arreglo
    $.ajax({
        dataType: "json",
        method: "POST",
        url: "../ProductoSinMovimiento",
        data: datos
    }).done(function(json) {
        Swal.close();
        if (json[0].resultado === "exito") {
            $("#aqui_tabla tbody").empty().html(json[0].tabla);
            $("#total_global").text(json[0].totalGlobal); // Mostrar el total global
            $("#tabla_producto").DataTable({
                "language": {
                    "url": "//cdn.datatables.net/plug-ins/1.10.15/i18n/Spanish.json"
                }
            });
            console.log("Paso el #tabla_producto");
        } else {
            Swal.fire('Accion no completada', 'No se pudo obtener los datos', 'error');
        }
    }).fail(function() {
        Swal.fire('Error', 'Hubo un error en la consulta', 'error');
    }).always(function() {
        // Código adicional si es necesario
    });
}
