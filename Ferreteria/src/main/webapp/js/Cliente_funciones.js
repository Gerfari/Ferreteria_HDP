$(document).ready(function () {
    // Cargar clientes desde la base
    loadClientes();

    // Manejar formulario add y edit
    $("#clienteForm").submit(function (event) {
        event.preventDefault();
        var action = $("#action").val();
        if (action === "add") {
            addCliente();
        } else if (action === "edit") {
            editCliente();
        } 
    });

    // Asegurar que solo se pueda seleccionar un checkbox a la vez
    $('#estadoActivo').click(function () {
        $('#estadoInactivo').prop('checked', !this.checked);
    });
    $('#estadoInactivo').click(function () {
        $('#estadoActivo').prop('checked', !this.checked);
    });

    // Cargar clientes de la base
    function loadClientes() {
        $.ajax({
            url: "ClienteServlet",
            type: "GET",
            dataType: "json",
            success: function (clientes) {
                // Limpiar la tabla
                $("#clienteList").empty();

                // Agregar clientes a la tabla
                clientes.forEach(function (cliente) {
                    var row = $("<tr>");
                    row.append("<td class='row-data text-center'>" + cliente.duiCliente + "</td>");
                    row.append("<td class='row-data text-center'>" + cliente.nombreCliente + "</td>");
                    row.append("<td class='row-data text-center'>" + cliente.apellidoCliente + "</td>");
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
                   /* var deleteBtn = $("<button>").text("Eliminar").click(function () {
                        deleteCliente(cliente.duiCliente);
                    });*/
                    var edit = $("<td>").append(editBtn);
                    /*var delete1 = $("<td>").append(deleteBtn);*/
                    row.append(edit);
                    /*row.append(delete1);*/                    
                    $("#clienteList").append(row);                   
                });
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
        return; // Salir de la función si algún campo está vacío
    }

    var formData = $("#clienteForm").serialize();
    $.post("ClienteServlet", formData, function () {
        $("#clienteForm")[0].reset(); 
        loadClientes(); 
    });
}


    // Editar cliente
    function editCliente() {
        var formData = $("#clienteForm").serialize();
        $.post("ClienteServlet", formData, function () {
            $("#clienteForm")[0].reset(); 
            $("#action").val("add"); 
            $("#submitBtn").text("Agregar"); 
            loadClientes();
        });
    }

    // Editar formulario
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
    }

    // Eliminar cliente
    /*function deleteCliente(duiCliente) {
        if (confirm("Estas seguro de que deseas eliminar este cliente ?")) {
            $.post("ClienteServlet", { action: "delete", duiCliente: duiCliente }, function () {
                loadClientes(); 
            });
        }
    }*/

});
