$(function () {
    console.log("principio");
    cargarTabla(1);

    $(document).on("click", "#registrar", function (e) {
        e.preventDefault();
        console.log("entro");
        $("#formulario_registro").trigger("reset");
        $("#md_registrar_empleado").modal("show");
        //document.querySelector('#codigoUsuario').readOnly = false;
    });
    $(document).on("submit", "#formulario_registro", function (e) {
        e.preventDefault();
        mostrar_cargando("Procesando Solicitud", "Espere mientras se almacenan los datos");
        var datos = $("#formulario_registro").serialize();

        $.ajax({
            dataType: "json",
            method: "POST",
            url: "../EmpleadoServlet",
            data: datos
        }).done(function (json) {
            console.log("Datos", json);
            Swal.close();
            if (json[0].resultado == "exito") {
                Swal.fire('Exito', 'Empleado Registrado', 'success');
                $("#md_registrar_empleado").modal("hide");
                cargarTabla();
                location.reload();
            } else {
                Swal.fire('Accion no completada', "No se puede registrar el Autor", "error");
            }
        }).fail(function () {


        });
    });
    
      //METODO PARA CERRA EL MODAL
   //DESDE LA X
   $(document).on("click","#cerrarmodal",function(e){
       e.preventDefault();
       Swal.fire('Cancelado','Se cancelo el registro','info');
       $("#md_registrar_empleado").modal("hide");
   });
   //DESDE EL BOTON CERRAR
   $(document).on("click","#btn_cerrar",function(e){
      e.preventDefault();
      Swal.fire('Cancelado','Se cancelo el registro','info');
      $("#md_registrar_empleado").modal("hide");
   });
});


function cargarTabla(estado = 1) {
    mostrar_cargando("procesando solicitud", "Espere mientras se procesa la informacion");
    var datos = {"consultar_datos": "si_consulta", "estado": estado};
    console.log("Datos: " + datos.consultar_datos + " Estado: " + datos.estado);
    $.ajax({
        dataType: "json",
        method: "POST",
        url: "../EmpleadoServlet",
        data: datos
    }).done(function (json) {
        Swal.close();
        console.log("datos consultados: " + json);
        if (json[0].resultado === "exito") {
            //LE MANDO LOS DATOS A LA TABLA CON ID aqui_tabla
            $("#aqui_tabla").empty().html(json[0].tabla);
            document.querySelector("#contar_empleado").textContent=json[0].cuantos;
            
            $("#tabla_empleado").DataTable({
                "languaje": {
                    url: "//cdn.datatables.net/plug-ins/1.10.15/i18n/Spanish.json"
                },
                "searching": true 
            });
        } else {
            Swal.fire('Accion no completada', 'No se pudo obtener los datos', 'error');
        }
    }).fail(function() {}).always(function() {});
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


function agregarEmpleado() {
    var form = document.getElementById("formAgregarEmpleado");
    var formData = new FormData(form);

    fetch("../EmpleadoServlet", {
        method: "POST",
        body: formData
    })
            .then(response => response.text())
            .then(data => {
                // Recargar la página para mostrar los cambios
                location.reload();
            })
            .catch(error => {
                console.error("Error al agregar empleado:", error);
            });
}

// Función para enviar una solicitud Ajax al servlet para eliminar un empleado
function eliminarEmpleado(dui) {
    var formData = new FormData();
    formData.append("action", "delete");
    formData.append("dui", dui);

    fetch("../EmpleadoServlet", {
        method: "POST",
        body: formData
    })
            .then(response => response.text())
            .then(data => {
                // Recargar la página para mostrar los cambios
                location.reload();
            })
            .catch(error => {
                console.error("Error al eliminar empleado:", error);
            });
}

$(document).on("click", ".btn_eliminar", function (e) {
    e.preventDefault();
    Swal.fire({
        title: '¿Desea eliminar el registro?',
        text: 'Al continuar, los datos seran dados de baja',
        showDenyButton: true,
        showCancelButton: false,
        confirmButton: 'si',
        denyButton: 'NO'
    }).then((result) => {
        if (result.isConfirmed) {
            eliminar($(this).attr('data-id'));
        } else if (result.isDenied) {
            Swal.fire("Opcion canceladada por el usuario", '', 'info');
        }
    });
});
$(document).on("click", ".btn_editar", function (e) {
    e.preventDefault();
    mostrar_cargando("ESPERE", "Obteniendo datos");
    var dui = $(this).attr("data-id");
    var datos = {"consultar_datos": "llamar_consulta", "dui": dui};
          //antes
                    console.log("antes");
    $.ajax({
        dataType: "json",
        method: "POST",
        url: "../EmpleadoServlet",
        data: datos
  

    }).done(function (json) {
        if (json[0].resultado === "exito") {
            $("#formulario_registro").trigger("reset");
            $("#consultar_datos").val("si_actualizado");
                                console.log("aqui");
    
        console.log("#consultar_datos");

            

            // $("#llave_persona").val(json[0].id_persona);
            $("#duiEmpleado").val(json[0].dui);
            $("#nombre").val(json[0].nombre);
            $("#apellido").val(json[0].apellido);
            $("#fechaNacimiento").val(json[0].fechaNacimiento);
            $("#direccion").val(json[0].direccion);
            $("#telefono").val(json[0].telefono);
            $("#genero").val(json[0].genero);
            $("#estado").val(json[0].estado);
            $("#idRol").val(json[0].rol);
            $("#correo").val(json[0].correo);
            $("#contraseña").val(json[0].contrasenia);


console.log(json);
            document.querySelector('#duiEmpleado').readonly = true;
            $("#md_registrar_empleado").modal("show");
        }
    }).fail(function () {
    }).always(function () {
    });
});

function eliminar(dui) {
    mostrar_cargando("Procesando Solicitud", "Espere mientras se almacena los datos" + dui);
    var datos = {"consultar_datos": "si_eliminalo", "dui": dui};
    $.ajax({
        dataType: "json",
        method: "POST",
        url: "../EmpleadoServlet",
        data: datos
    }).done(function (json) {
        Swal.close();
        if (json[0].resultado === "exito") {
            Swal.fire(
                    'Excelente',
                    'El dato fue dado de baja',
                    'success'
                    );
            cargarTabla();
        } else {
            Swal.fire(
                    'ERROR',
                    "No se puede eliminar el dato intentelo mas tarde",
                    "ERROR"
                    );
        }
    }).fail(function () {
        console.log("Error al eliminar");
    }).always(function () {
        console.log("Error al eliminar");
    });

}
// Función para enviar una solicitud Ajax al servlet para actualizar un empleado
//function actualizarEmpleado(dui) {
//    var form = document.getElementById("formActualizarEmpleado_" + dui);
//    var formData = new FormData(form);
//
//    fetch("EmpleadoServlet", {
//        method: "POST",
//        body: formData
//    })
//    .then(response => response.text())
//    .then(data => {
//        // Recargar la página para mostrar los cambios
//        location.reload();
//    })
//    .catch(error => {
//        console.error("Error al actualizar empleado:", error);
//    });
//});
const comboRol = document.querySelector("#idRol");
const cargarCombos = () => {
    var datos = {"consultar_datos": "llenar_combo"};
    $.ajax({
        dataType: "json",
        method: "POST",
        url: "../EmpleadoServlet",
        data: datos
    }).done(function (json) {
        if (json[0].resultado === "exito") {
            if (json[0].resultado === "exito") {
                comboRol.innerHTML += json[0].roles;
            }
            console.log("combobox", json);
        } else {
            console.log("error roles");
            }
        });
};

document.addEventListener("DOMContentLoaded", () => {
    cargarCombos();
    $('#idRol').select();
    //$('#genero').select();
    //$('#estado').select();

    $('#formulario_registro').parsley();


});

