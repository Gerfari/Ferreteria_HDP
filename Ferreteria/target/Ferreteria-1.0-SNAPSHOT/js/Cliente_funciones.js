/* global Inputmask */

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
        var action = $("#action").val();
        if (action === "add") {
            addCliente();
        } else if (action === "edit") {
            editCliente();
        }
    });
  
    $('#nombre, #apellido').on('input', function () {
        var value = $(this).val();
        var cleanedValue = value.replace(/[^a-zA-ZáéíóúÁÉÍÓÚ\s]/g, '');
        $(this).val(cleanedValue);
    });

    Inputmask("99999999-9", {
        placeholder: "00000000-0",
        clearIncomplete: true,
        clearMaskOnLostFocus: false
    }).mask('#dui');


    Inputmask("9999-9999", {
        placeholder: "0000-0000",
        clearIncomplete: true,
        clearMaskOnLostFocus: false
    }).mask('#telefono');


    
    function loadClientes() {
        $.ajax({
            url: "ClienteServlet",
            type: "GET",
            dataType: "json",
            success: function (clientes) {
                console.log("Clientes recibidos:", clientes); 
                
                $("#clienteList").empty();
                $("#CuantosClientes").text(clientes.length);

                
                clientes.forEach(function (cliente) {
                    var row = $("<tr>");
                    row.append("<td class='row-data text-center'>" + cliente.duiCliente + "</td>");
                    row.append("<td class='row-data text-center'>" + cliente.nombreCliente + " " + cliente.apellidoCliente + "</td>");
                    row.append("<td class='row-data text-center'>" + cliente.direccionCliente + "</td>");
                    row.append("<td class='row-data text-center'>" + cliente.telefonoCliente + "</td>");
                    row.append("<td class='row-data text-center'>" + (cliente.estadoCliente ? "Activo" : "Inactivo") + "</td>");

                    var editBtn = $("<button>").text("Editar").click(function () {
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
        // Obtener valores de los campos
        var duiCliente = $("#duiCliente").val().trim();
        var nombreCliente = $("#nombreCliente").val().trim();
        var apellidoCliente = $("#apellidoCliente").val().trim();
        var direccionCliente = $("#direccionCliente").val().trim();
        var telefonoCliente = $("#telefonoCliente").val().trim();

        // Verificar que los campos obligatorios no estén vacíos
        if (duiCliente === "" || nombreCliente === "" || apellidoCliente === "" || direccionCliente === "" || telefonoCliente === "") {
            alert("Por favor, complete todos los campos.");
            return;
        }

        var formData = $("#formulario_cliente").serialize();
        $.post("ClienteServlet", formData, function () {
            $("#formulario_cliente")[0].reset();
            loadClientes();
            $("#formulario").hide(); // Ocultar el formulario después de agregar
        });
    }
    
    // Editar cliente
    function editCliente() {
        var formData = $("#formulario_cliente").serialize();
        $.post("ClienteServlet", formData, function () {
            $("#formulario_cliente")[0].reset();
            $("#action").val("add");
            $("#submitBtn").text("Agregar");
            loadClientes();
            $("#formulario").hide(); 
        });
    }

    // Editar 
    function editForm(duiCliente, nombreCliente, apellidoCliente, direccionCliente, telefonoCliente, estadoCliente) {
        $("#action").val("edit");
        $("#duiCliente").val(duiCliente);
        $("#duiCliente").prop('readonly', true);
        $("#nombreCliente").val(nombreCliente);
        $("#apellidoCliente").val(apellidoCliente);
        $("#direccionCliente").val(direccionCliente);
        $("#telefonoCliente").val(telefonoCliente);
        $("#estadoCliente").prop('checked', estadoCliente);
        $("#submitBtn").text("Editar");
        $('#formulario').show();
    }
});
