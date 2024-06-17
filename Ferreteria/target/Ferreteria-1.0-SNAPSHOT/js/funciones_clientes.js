$(document).ready(function () {
    // Cargar clientes desde la base
    loadClientes();
    
    $('#estado_activo').click(function () {
        $('#estado_inactivo').prop('checked', !this.checked);
    });
    $('#estado_inactivo').click(function () {
        $('#estado_activo').prop('checked', !this.checked);
    });

    $('#mantenimiento').on('click', function () {
        $('#formulario').toggle();
    });

    $("#formulario_cliente").submit(function (event) {
        event.preventDefault();
        if (!validateForm()) {
            return;
        }
        setEstadoCliente(); 
        var action = $("#action").val();
        if (action === "add") {
            addCliente();
        } else if (action === "edit") {
            editCliente();
        }
    });

    // Evitar que los campos DUI y teléfono acepten letras
    $('#duiCliente, #telefonoCliente').on('input', function () {
        var value = $(this).val();
        var cleanedValue = value.replace(/[^0-9-]/g, ''); // Permitir solo números y guiones
        $(this).val(cleanedValue);
    });

    // Evitar que los campos nombre y apellido acepten números
    $('#nombreCliente, #apellidoCliente').on('input', function () {
        var value = $(this).val();
        var cleanedValue = value.replace(/[^a-zA-ZáéíóúÁÉÍÓÚ\s]/g, ''); 
        $(this).val(cleanedValue);
    });

    // Aplicar máscaras de entrada a DUI y teléfono
    Inputmask("99999999-9", {
        placeholder: "00000000-0",
        clearIncomplete: true,
        clearMaskOnLostFocus: false
    }).mask('#duiCliente');

     Inputmask("9999-9999", {
        placeholder: "0000-0000",
        clearIncomplete: true,
        clearMaskOnLostFocus: false
    }).mask('#telefonoCliente');

    function loadClientes() {
        $.ajax({
            url: "..//ClienteServlet",
            type: "GET",
            dataType: "json",
            success: function (clientes) {
                console.log("Clientes recibidos:", clientes);
                
                if ($.fn.DataTable.isDataTable('#tabla')) {
                    $('#tabla').DataTable().destroy();
                }
                
                $("#clienteList").empty();
                $("#CuantosClientes").text(clientes.length);

                clientes.forEach(function (cliente) {
                    var row = $("<tr>");
                    row.append("<td class='row-data text-center'>" + cliente.duiCliente + "</td>");
                    row.append("<td class='row-data text-center'>" + cliente.nombreCliente + " " + cliente.apellidoCliente + "</td>");
                    row.append("<td class='row-data text-center'>" + cliente.direccionCliente + "</td>");
                    row.append("<td class='row-data text-center'>" + cliente.telefonoCliente + "</td>");
                    row.append("<td class='row-data text-center'>" + (cliente.estadoCliente ? "Activo" : "Inactivo") + "</td>");

                   
                    var editBtn = $("<button class='btn btn-primary'>").text("MODIFICAR").click(function () {
                        editForm(cliente.duiCliente, cliente.nombreCliente,
                                cliente.apellidoCliente,
                                cliente.direccionCliente,
                                cliente.telefonoCliente,
                                cliente.estadoCliente);
                    });

                    var edit = $("<td>").append(editBtn);
                    row.append(edit);
                    $("#clienteList").append(row);
                });

                $('#tabla').DataTable();
            },
            error: function (xhr, status, error) {
                console.error("Error al cargar clientes:", error);
            }
        });
    }

    // Agregar cliente
    function addCliente() {
        var formData = $("#formulario_cliente").serialize();
        $.post("..//ClienteServlet", formData, function () {
            $("#formulario_cliente")[0].reset();
            $("#estado_activo").prop('checked', true);
            $("#estado_inactivo").prop('checked', false);
            loadClientes();
            $("#formulario").hide();
        });
    }
    
    // Editar cliente
    function editCliente() {
        var formData = $("#formulario_cliente").serialize();
        $.post("..//ClienteServlet", formData, function () {
            $("#formulario_cliente")[0].reset();
            $("#estado_activo").prop('checked', true);
            $("#estado_inactivo").prop('checked', false);
            $("#action").val("add");
            $("#submitBtn").text("GUARDAR");
            loadClientes();
            $("#formulario").hide();
        });
    }

    // Editar formulario
    function editForm(duiCliente, nombreCliente, apellidoCliente, direccionCliente, telefonoCliente, estadoCliente) {
        $("#action").val("edit");
        $("#duiCliente").val(duiCliente).prop('readonly', true);
        $("#nombreCliente").val(nombreCliente);
        $("#apellidoCliente").val(apellidoCliente);
        $("#direccionCliente").val(direccionCliente);
        $("#telefonoCliente").val(telefonoCliente);
        if (estadoCliente) {
            $("#estado_activo").prop('checked', true);
            $("#estado_inactivo").prop('checked', false);
        } else {
            $("#estado_activo").prop('checked', false);
            $("#estado_inactivo").prop('checked', true);
        }
        $("#submitBtn").text("MODIFICAR");
        $('#formulario').show();
    }

    // Establecer el valor de estadoCliente antes de enviar el formulario
    function setEstadoCliente() {
        var estadoCliente = $('#estado_activo').is(':checked') ? true : false;
        $('#estadoCliente').val(estadoCliente);
    }
     function validateForm() {
        var nombre = $("#nombreCliente").val().trim();
        var apellido = $("#apellidoCliente").val().trim();
        var direccion = $("#direccionCliente").val().trim();
        var telefono = $("#telefonoCliente").val().trim();

        if (nombre === '' || apellido === '' || direccion === '' || telefono === '') {
           // alert("Por favor, complete todos los campos obligatorios.");
            Swal.fire('Cancelado', 'Se canceló el registro', 'info');
            return false;
        }

        return true;
    }
});
