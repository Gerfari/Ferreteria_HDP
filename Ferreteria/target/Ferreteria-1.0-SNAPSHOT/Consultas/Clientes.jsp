<!DOCTYPE html>
<html>
    <head>
        <title>CLIENTES</title>
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
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
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

           
        </style>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="container">
    
            <div id="tabla-container">
                <div class="table_header"> 
                    <h2>Clientes</h2>
                </div>
                <table id="tabla" class="table table-responsive table-bordered navbarResponsive" width="100%" cellspacing="0">
                    <thead>
                        <tr> 
                            <th>DUI <i class="bi bi-chevron-expand"></i></th>
                            <th>NOMBRE <i class="bi bi-chevron-expand"></i></th>
                            <th>CANTIDAD <i class="bi bi-chevron-expand"></i></th>
                            <th>MONTO <i class="bi bi-chevron-expand"></i></th>
                        </tr>
                    </thead>
                    <tbody id="clientesList"></tbody>
                </table>
            </div>
        </div>
         <script src="../js/Clientes_funciones.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
       
    </body>
</html>
