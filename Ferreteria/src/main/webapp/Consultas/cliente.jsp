<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Crud Cliente</title>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js" integrity="sha384-w1Q4orYjBQndcko6MimVbzY0tgp4pWB4lZ7lr30WKz0vr/aWKhXdBNmNb5D92v7s" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script  src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
        <script src="http://parsleyjs.org/dist/parsley.js"></script>
        <link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.24/css/jquery.dataTables.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="../css/cliente.css">
    </head>
    <body class="fixed-left">

        <div id="wrapper">
            <div class="content-page">
                <div class="content">
                    <div class="page-content-wrapper">
                        <div class="container-fluid">
                            <div class="row">
                                <div class="mini-stat clearfix bg-white">
                                    <span class="mini-stat-icon bg-teal mr-0 float-right"><i
                                            class="mdi mdi-account"></i></span>
                                    <div class="mini-stat-info">
                                        <h2 id="mantenimiento" style="text-align: center; cursor: pointer;">Clientes Mantenimiento</h2>
                                        <span id="CuantosClientes" class="counter text-blue-grey">0</span>
                                        Registros
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                            <br> 
                            <div class="row">
                                <div class="col-12">
                                    <div class="card m-b-20">
                                        <div class="card-body">
                                            <table id="tabla" class="table table-striped table-bordered">
                                                <thead>
                                                    <tr>
                                                        <th>DUI</th>
                                                        <th>Nombre</th>
                                                        <th>direccion</th>
                                                        <th>telefono</th>
                                                        <th>estado</th>
                                                        <th>acciones</th>

                                                    </tr>
                                                </thead>
                                                <tbody id="clienteList"></tbody>
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
        <div class="container" style="display:none;" id="formulario">
            <h2 class="my-4">Registro de Cliente</h2>
            <form id="formulario_cliente" class="needs-validation" novalidate>
                <input type="hidden" id="action" name="action" value="add">
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="dui" class="form-label">DUI *</label>
                        <input type="text" class="form-control" id="duiCliente" name="duiCliente" placeholder="00000000-0" required>
                        <div class="invalid-feedback">
                            Por favor ingrese un DUI válido.
                        </div>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="nombre" class="form-label">Nombre *</label>
                        <input type="text" class="form-control" id="nombreCliente" name="nombreCliente" placeholder="Ingrese el Nombre" required>
                        <div class="invalid-feedback">
                            Por favor ingrese el Nombre.
                        </div>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="apellido" class="form-label">Apellido *</label>
                        <input type="text" class="form-control" id="apellidoCliente" name="apellidoCliente" placeholder="Ingrese el Apellido" required>
                        <div class="invalid-feedback">
                            Por favor ingrese el Apellido.
                        </div>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="direccion" class="form-label">Dirección *</label>
                        <input type="text" class="form-control" id="direccionCliente" name="direccionCliente" placeholder="Ingrese la Dirección" required>
                        <div class="invalid-feedback">
                            Por favor ingrese la Dirección.
                        </div>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="telefono" class="form-label">Teléfono *</label>
                        <input type="text" class="form-control" id="telefonoCliente" name="telefonoCliente" placeholder="0000-0000" required>
                        <div class="invalid-feedback">
                            Por favor ingrese un número de teléfono válido.
                        </div>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Estado *</label><br>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="checkbox" id="estado_activo" name="estado" value="activo">
                            <label class="form-check-label" for="estado_activo">Activo</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="checkbox" id="estado_inactivo" name="estado" value="inactivo">
                            <label class="form-check-label" for="estado_inactivo">Inactivo</label>
                        </div>
                        <div class="invalid-feedback">
                            Por favor seleccione el Estado.
                        </div>
                    </div>
                </div>
                <button id="submitBtn" type="submit" class="btn btn-primary">Guardar</button>
            </form>

        </div>
        <script type="text/javascript" src="//cdn.datatables.net/1.10.24/js/jquery.dataTables.min.js"></script>
        <script src="../js/Cliente_funciones.js"></script>
    </body>
</html>
