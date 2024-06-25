verificarSesion();
let lsSeleccionados = [];
let cont = 0;
let total = 0;
$(function () {
    $('#formulario_registro').parsley();
    ocultarBotonComprar();
    cargarTabla();


    //METODOS DEL MODAL
    $(document).on("click", "#realizar_venta", function (e) {
        //TOMAMOS EL DUI DEL EMPLEADO QUE REALIZA LA COMPRA
        let empleado = JSON.parse(localStorage.getItem('empleado'));
        var fecha = new Date();
        //FECHA CON FORMATO YYYY-MM-DD
        var fechaLocal = new Date(fecha.getTime() - fecha.getTimezoneOffset() * 60000);
        var fechaFormateada = fechaLocal.toISOString().slice(0, 10);
        e.preventDefault();
        mostrarTotal();
        $("#formulario_registro").trigger("reset");
        $("#md_registrar_venta").modal("show");
        //PARA QUE LOS CAMPOS NO SE PUEDAN EDITAR POR QUE SON REGISTROS QUE DEPENDEN DE QUIEN HAYA INICIADO SESION  
        //PARA MOSTRA EL SIGUIENTE CODIGO A REGISTRAR
        document.querySelector('#cant-productos').value = lsSeleccionados.length;
        
        document.querySelector('#total-pagar').value = "$ " + total.toFixed(2);
        document.querySelector("#empleado").value = empleado.duiEmpleado;
        document.querySelector("#nombreEmpleado").value = empleado.nombreEmpleado + " " + empleado.apellidoEmpleado;
        //PARA ENVIAR LA FECHA ACTUAL AL CALENDARIO
        document.querySelector('#fechaventa').value = fechaFormateada;
        document.querySelector('#fechaventa').readOnly=true;
        document.querySelector('#cant-productos').readOnly = true;
        document.querySelector('#empleado').readOnly = true;
        formatearTotal();
        cargar_combo_clientes();
    });

    //METODO PARA REGISTRAR LA COMPRA
    $(document).on("submit", "#formulario_registro", function (e) {
        e.preventDefault();
        Swal.fire({
            title: "¿Confirmar venta?",
            showDenyButton: true,
            showCancelButton: true,
            confirmButtonText: "Vender",
            denyButtonText: `No vender`,
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed) {
                mostrar_cargando("Procesando Solicitud", "Espere mientras se almacenan los datos");
                var datos = $("#formulario_registro").serialize();
                $.ajax({
                    dataType: "json",
                    method: "POST",
                    url: "../ControllerVenta",
                    data: datos
                }).done(function (json) {
                    Swal.close();
                    if (json[0].resultado === "exito") {
                        Swal.fire('Exito', 'Venta realizada', 'success');
                        //ACCEDO A LAS FILAS QUE CONTIENEN LOS CELDAS PARA EXTRAER SU INFORMACION
                        let obtenerDatos = document.querySelectorAll("#tabla_selec tr");
                        console.log(obtenerDatos);
                        //CREO UN ARRAY QUE GUARDARA LO QUE ESTA EN LA TABLA
                        let detallesVentasObj = [];
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
                                let detalleCompra = {idDetalleCompra: celdas[3].querySelector("button").id,
                                    existencia: celdas[0].querySelector("input").value};
                                let valorConDolar = celdas[2].textContent.trim();
                                let valorSinDolar = valorConDolar.replace('$', '');
                                let valorSinComilla=valorSinDolar.replace(',','');
                                nvDeta = {cantidad: celdas[1].querySelector("input").value,
                                    precioVenta: parseFloat(valorSinComilla).toFixed(2),
                                    detalleCompra: detalleCompra
                                };
                                detallesVentasObj.push(nvDeta);
                                console.log(nvDeta);
                            }
                        }
                        //CONVIRTO EL array A json
                        let jsonArray = JSON.stringify(detallesVentasObj);
                        console.log(jsonArray);
                        var datosArray = {"jsonArray": jsonArray, "consultar_datos": "guardar_detalle"};
                        $.ajax({
                            dataType: "json",
                            method: "POST",
                            url: "../ControllerVenta",
                            data: datosArray
                        }).done(function (json) {
                            if (json[0].resultado === "exito") {
                                $("#md_registrar_venta").modal("hide");
                                cargarTabla();
                                location.reload();
                            } else {
                                console.log("HUBO UN ERROR AL GUARDAR");
                            }
                        });     
                    } else {
                        Swal.fire('Accion no completada', "No se puede realizar la venta", "Error");
                    }
                }
                ).fail(function () {

                }).always(function () {

                });
            } else if (result.isDenied) {
                Swal.fire("Se cancelo la venta", "", "info");
                $("#md_registrar_venta").modal("hide");
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
            url: "../ControllerVenta",
            data: datos
        }).done(function (json) {
            if (json[0].resultado === "exito") {
                let resultado = false;
                let encontrado = 0;
                let k;
                for (k = 0; k < lsSeleccionados.length; k++) {
                    if (json[0].id_detalle_compra == lsSeleccionados[k]) {
                        encontrado = json[0].id_detalle_compra;
                        resultado = true;
                    }
                }
                if (resultado == true) {
                    //TOMAMOS EL INPUT
                    let inputEncont = document.getElementById("cantidad" + encontrado);
                    //REVISAREMOS SI YA LLEGAMOS AL MAXIMO DE PRODUCTOS EN EXISTENCIA
                    let maximo = inputEncont.getAttribute('max');
                    let valorInput = parseInt(inputEncont.value, 10);
                    if (valorInput < maximo) {
                        let incrementarInput = valorInput + 1;
                        inputEncont.value = incrementarInput;
                    } else {
                        swal.fire('Limite alcanzado', 'Ya selecciono el maximo disponible de este producto en este lote', 'info');
                    }

                } else {
                    lsSeleccionados.push(json[0].id_detalle_compra);
                    var $tabla = $("#tabla_selec tbody");
                    console.log(json);
                    $tabla.append(
                            '<tr>' +
                            '<th>' + json[0].nombre_producto + '</th>' +
                            '<td style="display:none;"><input type="hidden" id="existencia" name="existencia" value="' + json[0].existencia + '"></td>' +
                            '<td><input type="number" id="cantidad' + json[0].id_detalle_compra + '"  class="input-numero" min="1" max="' + json[0].existencia + '" value="1"></td>' +
                            '<td>$' + json[0].precio +'</td>' +
                            '<td><button class="btn btn-danger btn_eliminar" id="' + json[0].id_detalle_compra + '" value="' + json[0].id_detalle_compra + '">Eliminar</button></td>' +
                            '</tr>'
                            );
                    cont++;
                    formatearValoresVentas("tabla_selec");
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
        var valor = parseInt($(this).val());

        //PRIMERO VALIDAMOS QUE NO ACEPTE OPERACIONES EL INPUT
        if (!/^\d*$/.test(valor)) {
            $(this).val(1);
        }
        //TOMAMOS EL VALOR MAXIMO
        var maximo = parseInt($(this).attr('max'));
        if (valor > maximo) {
            //SI ESTAMOS EXCECIENDO MAXIMO POR TECLADO LE ASIGNAMOS EL VALOR MAXIMO QUE PODEMOS OFRECER DE PRODUCTO
            $(this).val(maximo);
        }
        //SI ES NEGATIVO O CERO LE PONEMOS COMO MINIMO 1
        if (valor < 1) {
            $(this).val(1);
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
        $("#md_registrar_venta").modal("hide");
    });

    $(document).on("click", "#btn_cerrar", function (e) {
        e.preventDefault();
        $("#md_registrar_venta").modal("hide");
    });
});


function cargarTabla(estado = 1) {
    //mostrar_cargando("procesando solicitud", "Espere mientras se procesa la informacion");
    var datos = {"consultar_datos": "mostrar_productos", "estado": estado};
    
    
    $.ajax({
        dataType: "json",
        method: "POST",
        url: "../ControllerVenta",
        data: datos
    }).done(function (json) {
        Swal.close();
        if (json[0].resultado === "exito") {
           
            $("#aqui_tabla").empty().html(json[0].tabla);
            document.querySelector("#Productos_Disponibles").textContent = json[0].cuantos;
            
            
            $('#tabla_productos').DataTable({
                "language": {
                    "url": "//cdn.datatables.net/plug-ins/1.10.24/i18n/Spanish.json"
                },
                "searching": true // Habilitar la funcionalidad de búsqueda
            });
            formatearValoresVentas("tabla_productos");
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
//FUNCION PARA CARGAR EL COMBOBOX DEL CLIENTES
function cargar_combo_clientes(estado = 1) {
    var datos = {"consultar_datos": "llenar_combo_cliente", "estado": estado};
    const clienteSelec = document.getElementById("clienteSeleccionado");
    clienteSelec.innerHTML = "<option value=''>Seleccione</option>";
    console.log("Entro a cargar combo");
    $.ajax({
        dataType: 'json',
        method: "POST",
        url: "../ControllerVenta",
        data: datos
    }).done(function (json) {
        Swal.close();
        console.log("datos recibidos: ", json);
        if (json[0].resultado === "exito") {
            const clientes = json[0].clientes;
            for (var i = 0; i < clientes.length; i++) {
                const dui_cliente = clientes[i].duiCliente;
                const nombre_cliente = clientes[i].nombreCliente + " " + clientes[i].apellidoCliente;
                //AGREGANDO EL PROVEEDOR COMO UNA OPCION DEL COMBOBOX
                //AGREGAMOS VALOR Y SU ID
                const optioncliente = new Option(nombre_cliente, dui_cliente);
                clienteSelec.appendChild(optioncliente);
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
        document.getElementById('realizar_venta').style.visibility = 'hidden';
    } else {
        document.getElementById('realizar_venta').style.visibility = 'visible';
    }
}

function mostrarTotal() {

    total = 0;
    let obtenerDatos = document.querySelectorAll("#tabla_selec tr");
    for (let i = 0; i < obtenerDatos.length; i++) {
        let celdas = obtenerDatos[i].querySelectorAll("td");
        if (celdas.length >= 3) {
            let valorConDolar = celdas[2].textContent.trim();
            console.log(valorConDolar);
            let valorSinDolar = valorConDolar.replace('$', '');
            let valorSinComilla= valorSinDolar.replace(',', '');
                console.log(valorSinComilla);
            total += celdas[1].querySelector("input").value * parseFloat(valorSinComilla).toFixed(2);
        }

    }
}

function formatearValoresVentas(tabla) {
        var tablaselec="#"+tabla+" td"
        console.log(tabla);
        console.log(tablaselec);
        const ventas = document.querySelectorAll(tablaselec);
        console.log(ventas);
    ventas.forEach(cell => {
        let text = cell.textContent;
        console.log("ANTES DE BUSCAR EL DOLAR");
        if (text.startsWith('$')) {
            console.log("ENCONTRO EL SIGNO DE EL DOLAR");
            let num = parseFloat(text.replace('$', '').trim());
            if (!isNaN(num)) {
                console.log("ES UN NUMERO");
                cell.textContent = "$ " + num.toLocaleString('en-US', {minimumFractionDigits: 2, maximumFractionDigits: 2});
            }
        }
        
    });
    
    
  
}
function formatearTotal(){
    var span = document.getElementById('total-pagar');
        let contenido = span.value;
        if (contenido.startsWith('$')) {
            let totalRecaudado = parseFloat(contenido.replace('$', ''));
            if (!isNaN(totalRecaudado)) {
                document.getElementById('total-pagar').value = "$ " + totalRecaudado.toLocaleString('en-US', {minimumFractionDigits: 2, maximumFractionDigits: 2});
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


