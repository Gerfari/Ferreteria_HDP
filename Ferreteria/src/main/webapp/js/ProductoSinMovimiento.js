verificarSesion()

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
            // Limpiar completamente el tbody antes de insertar nuevas filas
            $("#aqui_tabla tbody").empty();
            // Insertar las nuevas filas
            $("#aqui_tabla tbody").html(json[0].tabla);
            // Crear el pie de página manualmente
            var tfoot = "<tr><th colspan='4'>Total Global</th><th>$" + json[0].totalGlobal + "</th></tr>";
            $("#aqui_tabla tfoot").html(tfoot);
            
            $("#tabla_producto").DataTable({
                "language": {
                    "url": "//cdn.datatables.net/plug-ins/1.10.15/i18n/Spanish.json"
                }
            });
            
            formatearValoresVentas()
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

function formatearValoresVentas(){
    const ventas = document.querySelectorAll('#tabla_producto');
    
    ventas.forEach(cell=> {
        let text = cell.textContet;
        if (text.startsWith('$')) {
            let num = parseFloat(text.replace('$', '').trim());
            if(!isNaN(num)) {
                cell.textContet = "$ " + num.toLocaleString('en-US', {minimunFractionDigits: 2, maximumFractionDigits: 2});
            }
        }
    });
}

function verificarSesion(){
    let empleado = JSON.parse(localStorage.getItem('empleado'));
    if(empleado == null) {
        console.log("Deberia mostrar mensaje de error");
        window.top.location.href = '../Utilidades/RestringirAcceso.jsp';
    }
    console.log("Paso el if de verificarSesion");
}