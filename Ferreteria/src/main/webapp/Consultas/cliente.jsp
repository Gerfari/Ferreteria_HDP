<!DOCTYPE html>
<html>
    <head>
        <title>Clientes</title>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="stylesheet" href="css/styles.css" />
        <link href="https://fonts.googleapis.com/css2?family=Raleway&display=swap" rel="stylesheet"/>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css"/>
        <style>
            {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }

            body {
                display: flex;
                justify-content: center;
                align-items: center;
                min-height: 100vh;
                font-family: "Raleway", sans-serif;
                background-color: #eef4fd;
            }

            .container {
                display: flex;
                flex-direction: row;
                justify-content: space-between;
                align-items: flex-start;
                padding: 20px;
            }

            #tabla-container {
                flex: 1;
            }

            .table_header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 20px 30px 0;
            }

            button {
                outline: none;
                border: none;
                background-color: #6236ff;
                color: #ffffff;
                padding: 10px 30px;
                border-radius: 20px;
                text-transform: uppercase;
                font-size: 14px;
                cursor: pointer;
            }

            button:hover {
                background-color: #552bee;
            }

            .input_search {
                position: relative;
            }

            .input_search input {
                border-radius: 30px;
                width: 400px;
                outline: none;
                padding: 10px 20px;
                border: 1px solid #c9c9c9;
                box-sizing: border-box;
                padding-right: 50px;
            }

            .input_search #search {
                position: absolute;
                top: 50%;
                right: 0;
                margin-right: 1rem;
                transform: translate(-50%, -50%);
            }

            table {
                border-spacing: 0;
                margin-top: 1rem;
            }

            thead {
                background-color: #fff7b3;
            }

            th {
                padding: 10px;
            }

            tbody tr {
                border-bottom: 1px solid #dfdfdf;
            }

            tbody td {
                padding: 10px;
                border-bottom: 1px solid #dfdfdf;
                text-align: center;
            }

            tbody td #icons {
                font-size: 20px;
                cursor: pointer;
                margin-left: 10px;
                color: #797979;
            }

            tbody tr:hover {
                background-color: #f5f5f5;
            }

            .table_fotter {
                margin-top: 1rem;
                padding: 0 30px 20px;
            }

            #clienteForm {
                width: 300px;
            }
        </style>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="container">
            <form id="clienteForm" class="mr-3" id="formulario">
                <input type="hidden" id="action" name="action" value="add">
                <div class="form-group">
                    <label for="duiCliente">DUI:</label>
                    <input type="text" class="form-control" id="duiCliente" name="duiCliente">
                </div>
                <div class="form-group">
                    <label for="nombreCliente">Nombre:</label>
                    <input type="text" class="form-control" id="nombreCliente" name="nombreCliente">
                </div>
                <div class="form-group">
                    <label for="apellidoCliente">Apellido:</label>
                    <input type="text" class="form-control" id="apellidoCliente" name="apellidoCliente">
                </div>
                <div class="form-group">
                    <label for="direccionCliente">Dirección:</label>
                    <input type="text" class="form-control" id="direccionCliente" name="direccionCliente">
                </div>
                <div class="form-group">
                    <label for="telefonoCliente">Teléfono:</label>
                    <input type="text" class="form-control" id="telefonoCliente" name="telefonoCliente">
                </div>
                <div class="form-group">
                    <label>Estado:</label><br>
                    <div class="form-check form-check-inline">
                        <input type="checkbox" class="form-check-input" id="estadoActivo" name="estadoCliente" value="true">
                        <label class="form-check-label" for="estadoActivo">Activo</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input type="checkbox" class="form-check-input" id="estadoInactivo" name="estadoCliente" value="false">
                        <label class="form-check-label" for="estadoInactivo">Inactivo</label>
                    </div>
                </div>
                <button id="submitBtn" type="submit" class="btn btn-primary">Agregar</button>
            </form>
            <div id="tabla-container">
                <div class="table_header"> 
                    <h2>Clientes</h2>
                </div>
                <table id="tabla" class="table table-responsive table-bordered navbarResponsive" width="100%" cellspacing="0">
                    <thead>
                        <tr>
                            <th>DUI <i class="bi bi-chevron-expand"></i></th>
                            <th>NOMBRE <i class="bi bi-chevron-expand"></i></th>
                            <th>APELLIDO <i class="bi bi-chevron-expand"></i></th>
                            <th>DIRECCION <i class="bi bi-chevron-expand"></i></th>
                            <th>TELEFONO <i class="bi bi-chevron-expand"></i></th>
                            <th>ESTADO <i class="bi bi-chevron-expand"></i></th>
                            <th>ACCIONES <i class="bi bi-chevron-expand"></i></th>
                        </tr>
                    </thead>
                    <tbody id="clienteList"></tbody>
                </table>
            </div>
        </div>
         <script src="../js/Cliente_funciones.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
       
    </body>
</html>
