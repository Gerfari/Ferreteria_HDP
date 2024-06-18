$(function () {
    console.log("Entro al javascript");
    cargarTablaProveedorProducto();
});

function cargarTablaProveedorProducto(estado = 1) {
    var datos = {"consultar_datos": "si_consulta", "estado": estado};//CREAMOS UN ARREGLO
    $.ajax({
        dataType: "json",
        method: "POST",
        url: "../ControllerInformeComprasProductos",
        data: datos
    }).done(function (json) {
        Swal.close();
        if (json[0].resultado === "exito") {
            $("#aqui_tabla").empty().html(json[0].tabla);
            console.log("Entro al  done");
            //LE CAMBIE EL NOMBRE AL SPAN QUE CONTIENE EL NUMERO DE REGISTROS
            document.querySelector("#Lista_Prov_Prod").textContent = json[0].cuantos;
            //AQUI LE CAMBIE EL NOMBRE A LA TABLA
            $("#tabla_provprod").DataTable({
                "language": {
                    "url": "//cdn.datatables.net/plug-ins/1.10.15/i18n/Spanish.json"
                }
            });
            formatearValoresCompras();
            console.log("Paso el #tabla_provprod");
        } else {
            Swal.fire('Accion no completada', 'No se pudo obtener los datos', 'error');
        }
    }).fail(function () {

    }).always(function () {

    });
}

function formatearValoresCompras() {
    const ventas = document.querySelectorAll('#tabla_provprod td');

    ventas.forEach(cell => {
        let text = cell.textContent;
        if (text.startsWith('$')) {
            let num = parseFloat(text.replace('$', '').trim());
            if (!isNaN(num)) {
                cell.textContent = "$ " + num.toLocaleString('en-US', { minimumFractionDigits: 2, maximumFractionDigits: 2 });
            }
        }
    });
}