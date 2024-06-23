$(document).ready(function() {
    var table = $('#tablaProveedores').DataTable({
        "ajax": {
            "url": "../ProveedorServlet?action=list",
            "dataSrc": ""
        },
        "order": [[0, "asc"]],
        "columns": [
            { "data": "id" },
            { "data": "nombre" },
            { "data": "direccion" },
            { "data": "telefono" },
            { "data": "estado",
              "render": function(data, type, row) {
                  return data ? 'Activo' : 'Inactivo';
              }
            },
            {
                "data": null,
                "defaultContent": "<button class='btn btn-primary btn-actualizar'>Actualizar</button>"
            }
        ]
    });

    $('#tablaProveedores tbody').on('click', '.btn-actualizar', function () {
        var data = table.row($(this).parents('tr')).data();
        $('#formulario_registro').trigger("reset");
        $('#exampleModalLabel').text('Actualizar Proveedor');
        $('#nombreProveedor').val(data.nombre);
        $('#direccionProveedor').val(data.direccion);
        $('#telefonoProveedor').val(data.telefono);
        $('#estadoProveedor').val(data.estado ? 'true' : 'false');
        if ($('#idProveedor').length) {
            $('#idProveedor').val(data.id);
        } else {
            $('#formulario_registro').append('<input type="hidden" id="idProveedor" name="id" value="' + data.id + '">');
        }
        $('#md_registrar_proveedor').modal('show');
    });

    $('#tituloProveedores').on('click', function() {
        $('#formulario_registro').trigger("reset");
        $('#exampleModalLabel').text('Agregar Proveedor');
        $('#idProveedor').remove();
        $('#md_registrar_proveedor').modal('show');
    });

    $('#formulario_registro').on('submit', function(e) {
        e.preventDefault();

        var nombre = $('#nombreProveedor').val().trim();
        var direccion = $('#direccionProveedor').val().trim();
        var telefono = $('#telefonoProveedor').val().trim();

        if (nombre === '' || direccion === '' || telefono === '') {
            alert('El nombre, la dirección y el teléfono son campos obligatorios.');
            return;
        }

        var telefonoPattern = /^[0-9\-]+$/;
        if (!telefonoPattern.test(telefono)) {
            alert('El teléfono solo puede contener números y el símbolo "-".');
            return;
        }

        var action = $('#idProveedor').length ? 'update' : 'add';
        var estado = $('#estadoProveedor').val();
        var datos = $(this).serialize() + "&estado=" + estado + "&action=" + action;

        $.ajax({
            url: '../ProveedorServlet',
            type: 'POST',
            data: datos,
            success: function(response) {
                if (typeof response === 'string') {
                    response = JSON.parse(response);
                }
                if (response.message && response.message.startsWith("error:")) {
                    alert(response.message.substring(6)); // Mostrar solo el mensaje de error
                } else {
                    $('#md_registrar_proveedor').modal('hide');
                    table.ajax.reload(null, false); // Recargar tabla sin reiniciar la paginación
                }
            },
            error: function(xhr, status, error) {
                alert('Error: ' + xhr.responseText);
            }
        });
    });

    // Solo permitir números y guiones en el campo de teléfono
    $('#telefonoProveedor').on('input', function() {
        var input = $(this).val();
        $(this).val(input.replace(/[^0-9\-]/g, ''));
    });
});
