<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Proveedores</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.21/css/dataTables.bootstrap4.min.css">
    <style>
        .top-controls {
            display: flex;
            justify-content: flex-end;
            margin-bottom: 10px;
        }
        .btn-actualizar {
            background-color: blue;
            color: white;
        }
    </style>
</head>
<body>
    <div class="container mt-5">
        <h2 id="tituloProveedores" style="cursor:pointer;">LISTA DE PROVEEDORES</h2>
       
        <table id="tablaProveedores" class="table table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>DIRECCIÓN</th>
                    <th>TELÉFONO</th>
                    <th>ESTADO</th>
                    <th>ACCIÓN</th>
                </tr>
            </thead>
            <tbody>
                <!-- Las filas se cargarán dinámicamente con JavaScript -->
            </tbody>
        </table>
    </div>

    <div class="modal fade" id="md_registrar_proveedor" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Registrar Proveedor</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <form id="formulario_registro">
                    <div class="modal-body">
                        <div class="form-group">
                            <label for="nombreProveedor">Nombre</label>
                            <input type="text" class="form-control" id="nombreProveedor" name="nombre" required>
                        </div>
                        <div class="form-group">
                            <label for="direccionProveedor">Dirección</label>
                            <input type="text" class="form-control" id="direccionProveedor" name="direccion" required>
                        </div>
                        <div class="form-group">
                            <label for="telefonoProveedor">Teléfono</label>
                            <input type="text" class="form-control" id="telefonoProveedor" name="telefono" required>
                        </div>
                        <div class="form-group">
                            <label for="estadoProveedor">Estado</label>
                            <select class="form-control" id="estadoProveedor" name="estado" required>
                                <option value="true">Activo</option>
                                <option value="false">Inactivo</option>
                            </select>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                        <button type="submit" class="btn btn-primary">Guardar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.21/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.21/js/dataTables.bootstrap4.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="../Js/proveedor.js"></script>
</body>
</html>
