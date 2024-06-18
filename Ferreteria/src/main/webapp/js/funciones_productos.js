$(function () {
    $('#formulario_registro').parsley();
    cargarTabla();
    cargar_combo_categorias();

    $(document).on("click", "#registrar_producto", function (e) {
        var codEnNumero = document.getElementById('Productos_registrados').textContent;
        var codSiguiente = parseInt(codEnNumero, 10) + 1;
        console.log("extrayendo el elemento por id " + codEnNumero + " convirtiendo en numero " + codSiguiente);
        e.preventDefault();
        $("#formulario_registro").trigger("reset");
        $("#md_registrar_producto").modal("show");
        document.querySelector('#id_producto').value = codSiguiente;
        document.querySelector('#id_producto').readOnly = true;
    });

    // Validar entrada de caracteres permitidos
    var regex = /^[a-zA-ZÁÉÍÓÚÜÑáéíóúüñ0-9 /*\-_:;.,()#]*$/;

    $(document).on("input", "#formulario_registro input", function () {
        if (!regex.test($(this).val())) {
            $(this).addClass("is-invalid");
        } else {
            $(this).removeClass("is-invalid");
        }
    });

    // Registrar Productos
    $(document).on("submit", "#formulario_registro", function (e) {
        e.preventDefault();

        var valido = true;
        $("#formulario_registro input").each(function () {
            if (!regex.test($(this).val())) {
                valido = false;
                $(this).addClass("is-invalid");
            } else {
                $(this).removeClass("is-invalid");
            }
        });

        if (!valido) {
            Swal.fire('Error', 'Caracteres no permitidos en uno o más campos', 'error');
            return;
        }

        mostrar_cargando("Procesando Solicitud", "Espere mientras se almacena los datos");
        var datos = $("#formulario_registro").serialize();
        console.log(datos);
        $.ajax({
            dataType: "json",
            method: "POST",
            url: "../ControllerProductos",
            data: datos
        }).done(function (json) {
            Swal.close();
            if (json[0].resultado === "exito") {
                Swal.fire('Exito', 'Producto Registrado', 'success');
                $("#md_registrar_producto").modal("hide");
                cargarTabla();
                location.reload();
            } else {
                Swal.fire('Accion no completada ', "no se puede registrar el Producto", "error");
            }
        }).fail(function () {
        }).always(function () {
        });
    });

    // Editar Productos
    $(document).on("click", ".btn_editar", function (e) {
        e.preventDefault();
        mostrar_cargando("Espere", "Obteniendo Productos");
        var id = $(this).attr("data-id");
        var datos = {"consultar_datos": "si_producto_especifico", "id": id};
        $.ajax({
            dataType: "json",
            method: "POST",
            url: "../ControllerProductos",
            data: datos
        }).done(function (json) {
            console.log(json);
            if (json[0].resultado === "exito") {
                $("#formulario_registro").trigger("reset");
                $("#consultar_datos").val("si_actualizalo");
                $("#id_producto").val(json[0].id_producto);
                $("#nombre_producto").val(json[0].nombre_producto);
                $("#descripcion").val(json[0].descripcion);
                $("#estado_producto").val(json[0].estado_producto);
                const estadoSelec = document.getElementById("estado_producto");
                if (json[0].estado_producto === true) {
                    estadoSelec.value = 'true';
                } else {
                    estadoSelec.value = 'false';
                }
                $("#id_categoria").val(json[0].id_categoria);
                document.querySelector('#id_producto').readOnly = true;
                $("#md_registrar_producto").modal("show");

                // Validar entrada de caracteres permitidos en el formulario de edición
                $("#formulario_registro input").each(function () {
                    if (!regex.test($(this).val())) {
                        $(this).addClass("is-invalid");
                    } else {
                        $(this).removeClass("is-invalid");
                    }
                });
            }
        }).fail(function () {
        }).always(function () {
        });
    });

    // Cargar registros de la base de datos
    function cargarTabla(estado = 1) {
        mostrar_cargando("procesando solicitud", "Espere mientras se procesa la información");
        var datos = {"consultar_datos": "si_consulta", "estado": estado};
        console.log("esta aqui");
        $.ajax({
            dataType: "json",
            method: "POST",
            url: "../ControllerProductos",
            data: datos
        }).done(function (json) {
            Swal.close();
            console.log("datos consultados", json);
            if (json[0].resultado === "exito") {
                $("#aqui_tabla").empty().html(json[0].tabla);
                document.querySelector("#Productos_registrados").textContent = json[0].cuantos;
                $("#tabla_productos").DataTable({
                    "language": {
                        "url": "//cdn.datatables.net/plug-ins/1.10.15/i18n/Spanish.json"
                    }
                });
            } else {
                Swal.fire('Accion no completada...', 'No se pueden cargar los Productos...', 'error');
            }
        }).fail(function () {
        }).always(function () {
        });
    }

    // Enviar mensajes al usuario
    function mostrar_cargando(titulo, mensaje = "") {
        Swal.fire({
            title: titulo,
            html: mensaje,
            timer: 2000,
            timerProgressBar: true,
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

    function cargar_combo_categorias(estado = 1) {
        var datos = {"consultar_datos": "llenar_combo_categorias", "estado": estado};
        const categoriaSelec = document.getElementById("id_categoria");
        categoriaSelec.innerHTML = "<option value=''>Seleccione</option>";
        console.log("Entro a cargar combo");
        $.ajax({
            dataType: 'json',
            method: "POST",
            url: "../ControllerProductos",
            data: datos
        }).done(function (json) {
            Swal.close();
            console.log("datos recibidos: ", json);
            if (json[0].resultado === "exito") {
                const categorias = json[0].categorias;
                for (var i = 0; i < categorias.length; i++) {
                    const id_cat = categorias[i].idCategoria;
                    const nombre_cat = categorias[i].categoria;
                    const optionCategoria = new Option(nombre_cat, id_cat);
                    categoriaSelec.appendChild(optionCategoria);
                }
            } else {
                console.log("El resultado del if fue un fallo");
            }
        }).fail(function () {
        }).always(function () {
        });
    }

    $(document).ready(function () {
        // Cerrar el modal cuando se presiona el botón de cerrar en la esquina
        $(document).on("click", "#cerrarmodal", function (e) {
            e.preventDefault();
            Swal.fire('Cancelado', 'Se canceló el registro', 'info');
            $("#md_registrar_producto").modal("hide");
        });

        // Cerrar el modal cuando se presiona el botón "Cerrar" dentro del modal
        $(document).on("click", "#btn_cerrar", function (e) {
            e.preventDefault();
            Swal.fire('Cancelado', 'Se canceló el registro', 'info');
            $("#md_registrar_producto").modal("hide");
        });
    });
});
