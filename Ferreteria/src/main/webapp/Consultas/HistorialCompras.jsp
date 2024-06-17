<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Consulta 4</title>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js" integrity="sha384-w1Q4orYjBQndcko6MimVbzY0tgp4pWB4lZ7lr30WKz0vr/aWKhXdBNmNb5D92v7s" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script  src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="http://parsleyjs.org/dist/parsley.js"></script>
    <link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.24/css/jquery.dataTables.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
</head>
<body class="fixed-left">
<div id="wrapper">
    <div class="content-page">
        <div class="content">
            <div class="page-content-wrapper">
                <div class="container-fluid">
                    <div class="row">
                        <div class="mini-stat clearfix bg-white">
                            <span class="mini-stat-icon bg-teal mr-0 float-right"><i class="mdi mdi-account"></i></span>
                            <div class="mini-stat-info">
                                <h2 style="text-align: center;">Historial de Compras de</h2>
                                 <h2 id="Cuantos" style="text-align: center;"></h2>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                    <br>
                    <div class="row">
                        <div class="col-12">
                            <div class="card m-b-20">
                                <div class="card-body">
                                    <div class="row mb-3">
                                        <div class="col-md-6">
                                            <input type="text" id="buscarDUI" class="form-control" placeholder="Buscar por DUI">
                                        </div>
                                        <div class="col-md-6">
                                            <button id="btnBuscar" class="btn btn-primary">Buscar</button>
                                        </div>
                                    </div>
                                    <table id="tabla" class="table table-striped table-bordered">
                                        <thead>
                                            <tr>
                                               
                                                <th>Vendedor que Atendio</th>
                                                <th>Producto Comprado</th>
                                                 <th>Fecha Compra</th>
                                                <th>Precio</th>
                                               
                                            </tr>
                                        </thead>
                                        <tbody id="clientesList"></tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="//cdn.datatables.net/1.10.24/js/jquery.dataTables.min.js"></script>
<script src="..//js/ClienteCompra.js"></script>

</body>
</html>
