$(document).ready(function () {
    console.log("Entro al javascript");

    // Deshabilitar la entrada de texto en el campo del año
    var inputAnio = document.getElementById("anio");
    inputAnio.addEventListener("keydown", function (event) {
        event.preventDefault();
    });

    $("#btnMostrar").click(function() {
        // Llamamos a la función cargarTabla al hacer clic en el botón
        cargarTabla();
        actualizarDescripcion();
    });
});

function cargarTabla(estado = 1) {
    // Obtenemos los valores de los elementos select y input
    var mesInicio = parseInt($("#mes-inicio").val(), 10);
    var mesFin = parseInt($("#mes-fin").val(), 10);
    var anio = $("#anio").val();

    //mostrar los valores de mesInicio y mesFin en la consola
    console.log("mesInicio:", mesInicio);
    console.log("mesFin:", mesFin);
    // Creamos el objeto con los datos a enviar al servlet
    var datos = {
        "consultar_datos": "si_consulta",
        "estado": estado,
        "mes-inicio": mesInicio,
        "mes-fin": mesFin,
        "anio": anio
    };
    // Realizamos la petición AJAX al servlet
    $.ajax({
        dataType: "json",
        method: "POST",
        url: "../ControllerInformeVentas",
        data: datos
    }).done(function (json) {
        if (json[0].resultado === "exito") {
            $("#aqui_tabla").empty().html(json[0].tabla);
            document.querySelector("#Lista_Ventas").textContent = json[0].cuantos;
            $("#tabla_provprod").DataTable({
                "language": {
                    "url": "//cdn.datatables.net/plug-ins/1.10.15/i18n/Spanish.json"
                }
            });
            formatearValoresVentas();
        } else {
            // Mostramos un mensaje de error si la petición falla
            Swal.fire('Acción no completada', 'No se pudieron obtener los datos', 'error');
        }
    }).fail(function () {
        Swal.fire('Error', 'Hubo un problema al procesar la solicitud', 'error');
    });
}


function formatearValoresVentas() {
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



function actualizarDescripcion() {
    var mesInicio = parseInt($("#mes-inicio").val(), 10); // Obtener el mes-inicio seleccionado
    var mesFin = parseInt($("#mes-fin").val(), 10); // Obtener el mes-fin seleccionado
    var anio = parseInt($("#anio").val(), 10); // Obtener el año seleccionado
    var descripcion = '';
    var nombresMeses = [
        "ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO",
        "JULIO", "AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE"
    ];

    // Verificar que el usuario elija el mes correcto
    if (mesFin < mesInicio) {
        Swal.fire('Error', 'Por favor, seleccione los meses en el orden correcto.', 'error');
        $('#descripcion').text('');
        return;
    }

    if (mesInicio === 1 && mesFin === 3) {
        descripcion = `1° TRIMESTRE ${anio}`;
    } else if (mesInicio === 4 && mesFin === 6) {
        descripcion = `2° TRIMESTRE ${anio}`;
    } else if (mesInicio === 7 && mesFin === 9) {
        descripcion = `3° TRIMESTRE ${anio}`;
    } else if (mesInicio === 10 && mesFin === 12) {
        descripcion = `4° TRIMESTRE ${anio}`;
    } else if (mesInicio === 1 && mesFin === 6) {
        descripcion = `1° SEMESTRE ${anio}`;
    } else if (mesInicio === 7 && mesFin === 12) {
        descripcion = `2° SEMESTRE ${anio}`;
    } else if (mesInicio === 1 && mesFin === 12) {
        descripcion = `ANUAL ${anio}`;
    } else {
        descripcion = `DESDE ${nombresMeses[mesInicio - 1]} A ${nombresMeses[mesFin - 1]} ${anio}`;
    } 

    $('#descripcion').text(descripcion);
}

