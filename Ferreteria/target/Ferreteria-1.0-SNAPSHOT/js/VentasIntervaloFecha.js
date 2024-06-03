$(function () {

    //console.log("Entro al javascript");
    document.getElementById('mostrar_info').style.visibility='hidden';

    $(document).on("submit", "#formulario_datos", function (e) {
        e.preventDefault();

        if (validarFechas() == false) {
            console.log("Las fechas estan al reves")
            Swal.fire('Accion no completada', "Coloque las fechas correctamentes", "Error")
        } else {
            var datos = $("#formulario_datos").serialize();
            
            console.log("Entro al else");
            //console.log("Datos capturados del formulario: " + datos);
            $.ajax({
                dataType: "json",
                method: "POST",
                url: "../ControllerVenta",
                data: datos
            }).done(function (json) {
                Swal.close();
                if (json[0].resultado === "exito") {
                    //FALTA CREAR ESTA TABLA
                    $("#aqui_tabla").empty().html(json[0].tabla);
                    document.getElementById('mostrar_info').style.visibility='visible';
                    document.querySelector("#Cantidad_registros").textContent = json[0].cuantos;
                    let totalRecaudado=json[0].totalRecaudado;
                    document.querySelector("#totalRecaudado").textContent=totalRecaudado.toFixed(2);;
                    document.querySelector("#fechaDesde").textContent=json[0].desde;
                    document.querySelector("#fechaHasta").textContent=json[0].hasta;
                    $("#tabla_registros").DataTable({
                        "languaje": {
                            "url": "//cdn.datatables.net/plug-ins/1.10.15/i18n/Spanish.json"
                        }
                    });


                } else {
                    Swal.fire('Accion no completada', "No se puede realizar la consulta", "Error");
                }
            }).fail(function () {

            }).always(function () {

            });

        }
    });

});
//LLAMO EL METODO CARGARCOMBOS AFUERA PARA QUE SOLO SE EJECUTE UNA VEZ
cargarCombos();

//METODO PARA CARGAR LOS COMBOBOX
function cargarCombos(estado = 1) {
    var datos = {"consultar_datos": "llenar_combo", "estado": estado};
    const inicioFecha = document.getElementById('fechaInicio');
    const finFecha = document.getElementById('fechaFin');
    //LIMPIAMOS EL COMBO BOX
    inicioFecha.innerHTML = "<option value=''>Seleccione</option>";
    finFecha.innerHTML = "<option value=''>Seleccione</option>";
    console.log("Entro a cargar combos");
    $.ajax({
        dataType: 'json',
        method: "POST",
        url: "../ControllerVenta",
        data: datos
    }).done(function (json) {
        Swal.close();
        console.log("datos recibidos: ", json);
        if (json[0].resultado === "exito") {
            const fechas = json[0].fechas;

            for (var i = 0; i < fechas.length; i++) {
                const fecha = fechas[i];
                //AGREGANDO LA FECHA COMO UNA OPCION DEL COMBOBOX
                //AGREGAMOS VALOR Y SU ID
                //CREO DOS VALORES POR QUE UN ELEMENTO DEL DOM SOLO PUEDE ESTAR EN UN ELEMENTO
                const optionInicio = new Option(fecha, fecha);
                const optionFin = new Option(fecha, fecha);
                //LOS AGREGO AL COMBOBOX
                inicioFecha.appendChild(optionInicio);
                finFecha.appendChild(optionFin);
            }
        } else {
            console.log("El resultado fue un fallo");
        }
    }).fail(function () {

    }).always(function () {

    });
}
//METODO PARA VALIDAR SI QUE LAS FECHAS NO ESTEN AL REVEZ
function validarFechas() {
    //VALIDACION DE DATOS
    const inicioFecha = document.getElementById('fechaInicio');
    const finFecha = document.getElementById('fechaFin');

    var inicioDate = new Date(inicioFecha.value);
    var finDate = new Date(finFecha.value);
//  NO ESTOY OBTENIENDO LAS FECHAS CORRECTAMENTE
    if (inicioDate > finDate) {
        return false;
    } else {
        return true;
    }
}



