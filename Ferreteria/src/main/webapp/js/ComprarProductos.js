verificarSesion();
let lsSeleccionados = [];
let cont = 0;
let total = 0;
$(function () {

    $('#formulario_registro').parsley();
    ocultarBotonComprar();
    cargarTabla();


    //METODOS DEL MODAL
    $(document).on("click", "#realizar_compra", function (e) {
        //TOMAMOS EL DUI DEL EMPLEADO QUE REALIZA LA COMPRA
        let empleado = JSON.parse(localStorage.getItem('empleado'));
        //CODIGO PARA ENVIAR EL PROXIMO CODIGO PERO QUE SOLO LO MUESTRE
        //var codEnNumero=document.getElementById('Compras_realizadas').textContent;
        //CONVERTIMOS DE TEXTO A NUMERO BASE 10
        //var codSiguiente=parseInt(codEnNumero,10)+1;
        //PARA ENVIAR LA FECHA ACTUAL AL CALENDARIO
        var fecha = new Date();
        //FECHA CON FORMATO YYYY-MM-DD
        var fechaLocal = new Date(fecha.getTime() - fecha.getTimezoneOffset() * 60000);
        var fechaFormateada = fechaLocal.toISOString().slice(0, 10);

        e.preventDefault();
        mostrarTotal();
        $("#formulario_registro").trigger("reset");
        $("#md_registrar_compra").modal("show");
        //PARA QUE LOS CAMPOS NO SE PUEDAN EDITAR POR QUE SON REGISTROS QUE DEPENDEN DE QUIEN HAYA INICIADO SESION  
        //PARA MOSTRA EL SIGUIENTE CODIGO A REGISTRAR
        document.querySelector('#cant-productos').value = lsSeleccionados.length;
        document.querySelector('#total-pagar').value = "$" + total.toFixed(2);
        document.querySelector("#empleado").value = empleado.duiEmpleado;
        document.querySelector("#nombreEmpleado").value = empleado.nombreEmpleado + " " + empleado.apellidoEmpleado;

        //PARA ENVIAR LA FECHA ACTUAL AL CALENDARIO
        document.querySelector('#fechacompra').value = fechaFormateada;
        document.getElementById('fechacompra').readOnly = true;
        document.querySelector('#cant-productos').readOnly = true;
        document.querySelector('#empleado').readOnly = true;
        
        formatearValoresVentas();
        cargar_combo_proveedores();
        
    });

    //METODO PARA REGISTRAR LA COMPRA
    $(document).on("submit", "#formulario_registro", function (e) {
        e.preventDefault();
        Swal.fire({
            title: "¿Confirmar compra?",
            showDenyButton: true,
            showCancelButton: true,
            confirmButtonText: "Comprar",
            denyButtonText: `No comprar`,
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            /* Read more about isConfirmed, isDenied below */
            if (result.isConfirmed) {
                mostrar_cargando("Procesando Solicitud", "Espere mientras se almacenan los datos");
                var datos = $("#formulario_registro").serialize();

                $.ajax({
                    dataType: "json",
                    method: "POST",
                    url: "../ControllerCompra",
                    data: datos
                }).done(function (json) {
                    Swal.close();
                    if (json[0].resultado === "exito") {
                        Swal.fire('Exito', 'Compra realizada', 'success');
                        //ACCEDO A LAS FILAS QUE CONTIENEN LOS CELDAS PARA EXTRAER SU INFORMACION
                        let obtenerDatos = document.querySelectorAll("#tabla_selec tr");

                        console.log(obtenerDatos);
                        //CREO UN ARRAY QUE GUARDARA LO QUE ESTA EN LA TABLA
                        let detallesComprasObj = [];
                        let nvDeta;
                        console.log("Paso del objeto");
                        //ACCEDO A LOS ELEMENTOS QUE CONTIENEN LAS FILAS Y LOS AGREGO AL ARRAY QUE MANDARE POR PARAMETRO AL CONTROLADOR
                        for (let i = 0; i < obtenerDatos.length; i++) {
                            let celdas = obtenerDatos[i].querySelectorAll("td");
                            //POR QUE AL TOMAR LAS FILAS, TAMBIEN SE TOMARA LA DE LOS ENCABEZADOS ASI QUE LA LIMITAMOS
                            if (celdas.length >= 3) {
                                console.log("Entro al for " + i + " vez");
                                console.log(celdas);
                                //CREAMOS UN OBJETO DE TIPO PRODUCTO CON EL ID PARA AGREGARLO AL ARRAY
                                let producto = {idProducto: celdas[2].querySelector("button").id};
                                nvDeta = {cantidad: celdas[0].querySelector("input").value,
                                    precio: parseFloat(celdas[1].querySelector("input").value).toFixed(2),
                                    producto: producto
                                };

                                detallesComprasObj.push(nvDeta);
                            }

                        }
                        //CONVIRTO EL array A json
                        let jsonArray = JSON.stringify(detallesComprasObj);
                        console.log(jsonArray);
                        var datosArray = {"jsonArray": jsonArray, "consultar_datos": "guardar_detalle"};
                        $.ajax({
                            dataType: "json",
                            method: "POST",
                            url: "../ControllerComprarProductos",
                            data: datosArray

                        }).done(function (json) {
                            if (json[0].resultado === "exito") {
                                $("#md_registrar_compra").modal("hide");
                                location.reload();
                            } else {
                                console.log("HUBO UN ERROR AL GUARDAR");
                            }

                        });

//                cargarTabla();
//                location.reload();
                    } else {
                        Swal.fire('Accion no completada', "No se puede realizar la compra", "Error");
                    }
                }).fail(function () {

                }).always(function () {

                });
            } else if (result.isDenied) {
                Swal.fire("Se cancelo la compra", "", "info");
                $("#md_registrar_compra").modal("hide");
            }
        });


    });


    //METODO QUE AGREGA A LA LISTA LOS PRODUCTOS
    $(document).on("click", ".btn_seleccionar", function (e) {
        e.preventDefault();

        let listaPro = $(this).a;
        var id = $(this).attr("id");//TOMA EL VALOR DEL data-id QUE ES EL CODIGO O ID
        var datos = {"consultar_datos": "si_producto_especifico", "id": id, "lsSeleccionados": lsSeleccionados};
        $.ajax({
            dataType: "json",
            method: "POST",
            url: "../ControllerComprarProductos",
            data: datos
        }).done(function (json) {
            if (json[0].resultado === "exito") {
                let resultado = false;
                let encontrado = 0;
                let k;
                for (k = 0; k < lsSeleccionados.length; k++) {
                    if (json[0].id_producto == lsSeleccionados[k]) {
                        encontrado = json[0].id_producto;
                        resultado = true;
                    }
                }
                if (resultado == true) {
                    //TOMAMOS EL INPUT
                    let inputEncont = document.getElementById("cantidad" + encontrado);
                    let valorInput = parseInt(inputEncont.value, 10);
                    let incrementarInput = valorInput + 1;
                    inputEncont.value = incrementarInput;
                } else {
                    lsSeleccionados.push(json[0].id_producto);
                    var $tabla = $("#tabla_selec tbody");
                    $tabla.append(
                            '<tr>' +
                            '<th>' + json[0].nombre_producto + '</th>' +
                            '<td><input type="number" class="input-numero" id="cantidad' + json[0].id_producto + '" min="1" value="1"></td>' +
                            '<td><input type="number" class="input-numero" id="precio' + json[0].id_producto + '"  value="10.01" step="1" min="0.01"></td>' +
                            '<td><button class="btn btn-danger btn_eliminar" id="' + json[0].id_producto + '" value="' + json[0].id_producto + '">Eliminar</button></td>' +
                            '</tr>'
                            );
                    cont++;
                }
                ocultarBotonComprar();
            }
        }).fail(function () {
        }).always(function () {
        });
    });

    //METODO QUE VERIFICA QUE NO EXCEDA EL LIMITE NI QUE SE SELECCIONE 0 PRODUCTOS A VENDER
    $(document).on("blur", ".input-numero", function (e) {
        //TOMAREMOS EL INPUT ESPECIFICO QUE DESENCADENO LA ACCION, OSEA SOBRE EL CUAL SE ESTA ESCRIBIENDO
        var valor = parseFloat($(this).val());
        var maximo = parseFloat($(this).attr('max'));
        var minimo = parseFloat($(this).attr('min'));
        //PRIMERO VALIDAMOS QUE NO ACEPTE OPERACIONES EL INPUT
        if (!/^\$?\d*\.?\d*$/.test(valor)) {
            $(this).val(minimo);
        }
        //TOMAMOS EL VALOR MAXIMO

        if (valor > maximo) {
            //SI ESTAMOS EXCECIENDO MAXIMO POR TECLADO LE ASIGNAMOS EL VALOR MAXIMO QUE PODEMOS OFRECER DE PRODUCTO
            $(this).val(maximo);
        }
        //SI ES NEGATIVO O CERO LE PONEMOS COMO MINIMO 1

        if (valor < minimo) {
            $(this).val(minimo);
        }


    });

    $(document).on("click", ".btn_eliminar", function (e) {

        //OBTENEMOS EL BOTON QUE CLICKEAMOS
        var idBoton = this.id;
        console.log(idBoton);
        //ELIMINAREMOS TAMBIEN DEL ARRAY DE LOS PRODUCTOS SELECCIONADOS
        const indice = lsSeleccionados.indexOf(parseInt(idBoton));
        //REVISAMOS SI EL INDICE ES CORRECTO
        console.log("indice" + indice);
        if (indice !== -1) {
            lsSeleccionados.splice(indice, 1);
            console.log("Elemento eliminado " + lsSeleccionados);
        } else {
            console.log("Elemento no encontrado" + lsSeleccionados);
        }
        //OBTENEMOS LA FILA QUE ELIMINAREMOS
        var fila = $(this).closest('tr');
        fila.remove();
        console.log("boton clickeado" + idBoton);
        ocultarBotonComprar();
    });

    //METODOS PARA CERRAR MODAL
    $(document).on("click", "#cerrarmodal", function (e) {
        e.preventDefault();
        Swal.fire('Cancelado', 'Se cancelo el registro', 'info');
        $("#md_registrar_compra").modal("hide");
    });

    $(document).on("click", "#btn_cerrar", function (e) {
        e.preventDefault();
        Swal.fire('Cancelado', 'Se cancelo el registro', 'info');
        $("#md_registrar_compra").modal("hide");
    });
});


function cargarTabla(estado = 1) {
    //mostrar_cargando("procesando solicitud", "Espere mientras se procesa la informacion");
    var datos = {"consultar_datos": "si_consulta", "estado": estado};

    $.ajax({
        dataType: "json",
        method: "POST",
        url: "../ControllerComprarProductos",
        data: datos
    }).done(function (json) {
        Swal.close();
        if (json[0].resultado === "exito") {
            $("#aqui_tabla").empty().html(json[0].tabla);
            document.querySelector("#Compras_realizadas").textContent = json[0].cuantos;
            $('#tabla_productos').DataTable({
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
//FUNCION PARA CARGAR EL COMBOBOX DEL PROVEEDOR
function cargar_combo_proveedores(estado = 1) {
    var datos = {"consultar_datos": "llenar_combo_proveedor", "estado": estado};
    const proveedorSelec = document.getElementById("proveedorSeleccionado");
    proveedorSelec.innerHTML = "<option value=''>Seleccione</option>";
    console.log("Entro a cargar combo");
    $.ajax({
        dataType: 'json',
        method: "POST",
        url: "../ControllerCompra",
        data: datos
    }).done(function (json) {
        Swal.close();
        console.log("datos recibidos: ", json);
        if (json[0].resultado === "exito") {
            const proveedores = json[0].proveedores;
            for (var i = 0; i < proveedores.length; i++) {
                const id_prove = proveedores[i].idProveedor;
                const nombre_prove = proveedores[i].nombreProveedor;
                //AGREGANDO EL PROVEEDOR COMO UNA OPCION DEL COMBOBOX
                //AGREGAMOS VALOR Y SU ID
                const optionProveedor = new Option(nombre_prove, id_prove);
                proveedorSelec.appendChild(optionProveedor);
            }
        } else {
            console.log("El resultado del if fue un fallo");
        }
    }).fail(function () {

    }).always(function () {

    });
}

function ocultarBotonComprar() {
    if (lsSeleccionados.length == 0) {
        document.getElementById('realizar_compra').style.visibility = 'hidden';
    } else {
        document.getElementById('realizar_compra').style.visibility = 'visible';
    }
}

function mostrarTotal() {

    total = 0;
    let obtenerDatos = document.querySelectorAll("#tabla_selec tr");
    for (let i = 0; i < obtenerDatos.length; i++) {
        let celdas = obtenerDatos[i].querySelectorAll("td");
        if (celdas.length >= 3) {
            total += celdas[0].querySelector("input").value * parseFloat(celdas[1].querySelector("input").value).toFixed(2);
        }

    }
}
function formatearValoresVentas() {
    //AGREGO FORMATO AL TOTAL
        var input = document.getElementById('total-pagar');
        let contenido = input.value;
        console.log(contenido);
        if (contenido.startsWith('$')) {
            let totalAPagar = parseFloat(contenido.replace('$', ''));
            console.log(totalAPagar);
            if (!isNaN(totalAPagar)) {
                document.getElementById('total-pagar').value = "$ " + totalAPagar.toLocaleString('en-US', {minimumFractionDigits: 2, maximumFractionDigits: 2});
            }
        }
        document.querySelector('#total-pagar').readOnly = true;
}

function verificarSesion() {
    let empleado = JSON.parse(localStorage.getItem('empleado'));
    if (empleado == null) {
        console.log("Deberia mostrar mensaje de error");
        window.top.location.href = '../Utilidades/RestringirAcceso.jsp';
    }
    console.log("Paso el if de verificarSesion");
}



