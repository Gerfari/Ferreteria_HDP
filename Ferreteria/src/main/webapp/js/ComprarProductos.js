
let lsSeleccionados = [];
let cont = 0;
$(function () {
    $('#formulario_registro').parsley();
    cargarTabla();

    //METODOS DEL MODAL
    $(document).on("click", "#realizar_compra", function (e) {
        //CODIGO PARA ENVIAR EL PROXIMO CODIGO PERO QUE SOLO LO MUESTRE
        //var codEnNumero=document.getElementById('Compras_realizadas').textContent;
        //CONVERTIMOS DE TEXTO A NUMERO BASE 10
        //var codSiguiente=parseInt(codEnNumero,10)+1;
        //PARA ENVIAR LA FECHA ACTUAL AL CALENDARIO
        var fecha = new Date();
        //FECHA CON FORMATO YYYY-MM-DD
        var fechaFormateada = fecha.toISOString().slice(0, 10);

        e.preventDefault();
        $("#formulario_registro").trigger("reset");
        $("#md_registrar_compra").modal("show");
        //PARA QUE LOS CAMPOS NO SE PUEDAN EDITAR POR QUE SON REGISTROS QUE DEPENDEN DE QUIEN HAYA INICIADO SESION  
        //PARA MOSTRA EL SIGUIENTE CODIGO A REGISTRAR
        document.querySelector('#codigocompra').value = null;
        //PARA ENVIAR LA FECHA ACTUAL AL CALENDARIO
        document.querySelector('#fechacompra').value = fechaFormateada;
        document.querySelector('#codigocompra').readOnly = true;
        document.querySelector('#empleado').readOnly = true;

        cargar_combo_proveedores();
    });

    //METODO PARA REGISTRAR LA COMPRA
    $(document).on("submit", "#formulario_registro", function (e) {
        e.preventDefault();
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
                let detallesComprasObj =[];
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
                        let producto={idProducto: celdas[2].querySelector("button").id};
                        nvDeta = {cantidad: celdas[0].querySelector("input").value,
                            precio: parseFloat(celdas[1].querySelector("input").value).toFixed(2),
                            producto:producto
                            };

                        detallesComprasObj.push(nvDeta);
                    }

                }
                //CONVIRTO EL array A json
                let jsonArray=JSON.stringify(detallesComprasObj);
                console.log(jsonArray);
                var datosArray={"jsonArray":jsonArray,"consultar_datos":"guardar_detalle"};
                $.ajax({
                    dataType: "json",
                    method:"POST",
                    url:"../ControllerComprarProductos",
                    data:datosArray
                    
                }).done(function(json){
                    if (json[0].resultado === "exito") {
                        $("#md_registrar_compra").modal("hide");
                    console.log("YA RETORNO DEL AJAX DE GUARDAR DETALLE");
                    }else{
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
                            '<tr">' +
                            '<th>' + json[0].nombre_producto + '</th>' +
                            '<td><input type="number" id="cantidad' + json[0].id_producto + '" min="1" value="1"></td>' +
                            '<td><input type="number" id="precio' + json[0].id_producto + '" min="0.01" value="10.00"></td>' +
                            '<td><button class="btn btn-danger btn_eliminar" id="' + json[0].id_producto + '" value="' + json[0].id_producto + '">Eliminar</button></td>' +
                            '</tr>'
                            );
                    cont++;
                }
            }
        }).fail(function () {
        }).always(function () {
        });
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

function completarCompra() {

}

