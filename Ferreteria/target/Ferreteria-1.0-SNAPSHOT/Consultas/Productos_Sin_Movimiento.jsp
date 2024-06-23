<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Productos Sin Movimiento de Venta</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.21/css/dataTables.bootstrap4.min.css">
    <style>
        .top-controls {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 10px;
        }
        .btn-actualizar {
            background-color: blue;
            color: white;
        }
        .btn-eliminar {
            background-color: red;
            color: white;
        }
    </style>
</head>
<body>
    <div class="container mt-5">
        <h2>PRODUCTOS SIN MOVIMIENTO DE VENTA</h2>
        <div id="aqui_tabla">
            <table id="tabla_producto" class="table table-bordered table-striped">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>PRODUCTO</th>
                        <th>EXISTENCIA</th>
                        <th>PRECIO</th>
                        <th>TOTAL</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- Las filas se cargarán dinámicamente con JavaScript -->
                </tbody>
                <tfoot>
                    <tr>
                        <th colspan="4">Total Global</th>
                        <th id="total_global"></th>
                    </tr>
                </tfoot>
            </table>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.21/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.21/js/dataTables.bootstrap4.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script src="../js/ProductoSinMovimiento.js"></script>
</body>
</html>
