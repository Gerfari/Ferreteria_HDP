verificarSesion();
$(function () {
    //.parsley ES PARA VALIDAR LOS DATOS DEL FORMULARIO
    $('#formulario_registro').parsley();
    cargarTabla();

    //METODO PARA VER EL DETALLE DE LA COMPRA
    //METODO QUE AGREGA A LA LISTA LOS PRODUCTOS
    $(document).on("click", ".btn_seleccionar", function (e) {
        e.preventDefault();

        var id = $(this).attr("id");//TOMA EL VALOR DEL data-id QUE ES EL CODIGO O ID
        var datos = {"consultar_datos": "si_detalle_especifico", "id": id};
        $.ajax({
            dataType: "json",
            method: "POST",
            url: "../ControllerCompra",
            data: datos
        }).done(function (json) {
            if (json[0].resultado === "exito") {

                $("#formulario_registro").trigger("reset");
                $("#md_ver_detalle").modal("show");
                console.log(json[0].listaProductos);
                document.querySelector('#fechaRealizada').value = json[0].listaProductos[0].fechaCompra;
                document.querySelector('#vendedorDetalle').value = json[0].listaProductos[0].empleado;
                document.querySelector('#provedorDetalle').value = json[0].listaProductos[0].proveedor;
                document.getElementById('fechaRealizada').readOnly = true;
                document.querySelector('#vendedorDetalle').readOnly = true;
                document.querySelector('#provedorDetalle').readOnly = true;

                let listaDetalles = JSON.stringify(json[0].listaProductos);
                console.log(listaDetalles);
                var datosLista = {"consultar_datos": "tabla_detalles", "listaPro": listaDetalles};
                $.ajax({
                    dataType: "json",
                    method: "POST",
                    url: "../ControllerCompra",
                    data: datosLista
                }).done(function (jsonLista) {

                    console.log(jsonLista[0].tabla);
                    console.log(jsonLista);
                    if (jsonLista[0].resultado == "exito") {
                        document.querySelector('#cantidadComprado').value = jsonLista[0].cuantos;
                        document.querySelector('#totalGastado').value = "$" + jsonLista[0].totalCompra;
                        document.querySelector('#cantidadComprado').readOnly = true;
                        
                        $("#tabla_modal").empty().html(jsonLista[0].tabla);
                        // Inicializar DataTables después de cargar la tabla
                        $('#tabla_detalles').DataTable({
                            "language": {
                                "url": "//cdn.datatables.net/plug-ins/1.10.24/i18n/Spanish.json"
                            },
                            "searching": true // Habilitar la funcionalidad de búsqueda
                        });
                        formatearValoresVentas();

                    } else {
                        Swal.fire('Accion no completada', 'No se pudo obtener los datos', 'error');
                    }
                }).fail(function () {
                }).always(function () {
                });


            }
        }).fail(function () {
        }).always(function () {
        });
    });



    //METODO PARA REGISTRAR LA COMPRA
    $(document).on("submit", "#formulario_registro", function (e) {
        e.preventDefault();
        window.location.href = '../Cruds/Compras.jsp';

    });
    //METODO PARA CERRA EL MODAL
    //DESDE LA X
    $(document).on("click", "#cerrarmodal", function (e) {
        e.preventDefault();
        $("#md_ver_detalle").modal("hide");
    });
    //DESDE EL BOTON CERRAR
    $(document).on("click", "#btn_cerrar", function (e) {
        e.preventDefault();
        $("#md_ver_detalle").modal("hide");
    });
});
//CREAMOS EL METODO PARA MOSTRAR
function cargarTabla(estado = 1) {
    mostrar_cargando("procesando solicitud", "Espere mientras se procesa la informacion");
    var datos = {"consultar_datos": "si_consulta", "estado": estado};

    $.ajax({
        dataType: "json",
        method: "POST",
        url: "../ControllerCompra",
        data: datos
    }).done(function (json) {
        Swal.close();
        if (json[0].resultado === "exito") {
            $("#aqui_tabla").empty().html(json[0].tabla);
            document.querySelector("#Compras_realizadas").textContent = json[0].cuantos;

            // Inicializar DataTables después de cargar la tabla
            $('#tabla_compras').DataTable({
                "language": {
                    "url": "//cdn.datatables.net/plug-ins/1.10.24/i18n/Spanish.json"
                },
                "searching": true // Habilitar la funcionalidad de búsqueda
            });
        } else {
            Swal.fire('Accion no completada', 'No se pudo obtener los datos', 'error');
        }
    }).fail(function () {}).always(function () {});
}

//FUNCION PARA CARGAR EL COMBOBOX DEL PROVEEDOR

function mostrar_cargando(titulo, mensaje = "") {
    Swal.fire({
        title: titulo,
        html: mensaje,
        timer: 2000,
        timerProgessBar: true,
        didOpen: () => {
            Swal.showLoading();
        },
        willClose: () => {
        }
    }).then((result) => {
        if (result.dismiss === Swal.DismissReason.timer) {
            console.log('I was closed by timer');
        }
    });
}

function formatearValoresVentas() {
        const ventas = document.querySelectorAll('#tabla_detalles td');

    ventas.forEach(cell => {
        let text = cell.textContent;
        if (text.startsWith('$')) {
            let num = parseFloat(text.replace('$', '').trim());
            if (!isNaN(num)) {
                cell.textContent = "$ " + num.toLocaleString('en-US', {minimumFractionDigits: 2, maximumFractionDigits: 2});
            }
        }
        
    });
    
    
    //AGREGO FORMATO AL TOTAL
        var input = document.getElementById('totalGastado');
        let contenido = input.value;
        console.log(contenido);
        if (contenido.startsWith('$')) {
            let totalComprado = parseFloat(contenido.replace('$', ''));
            console.log(totalComprado);
            if (!isNaN(totalComprado)) {
                document.getElementById('totalGastado').value = "$ " + totalComprado.toLocaleString('en-US', {minimumFractionDigits: 2, maximumFractionDigits: 2});
            }
        }
        document.querySelector('#totalGastado').readOnly = true;
}

function verificarSesion() {
    let empleado = JSON.parse(localStorage.getItem('empleado'));
    if (empleado == null) {
        console.log("Deberia mostrar mensaje de error");
        window.top.location.href = '../Utilidades/RestringirAcceso.jsp';
    }
    console.log("Paso el if de verificarSesion");
}
