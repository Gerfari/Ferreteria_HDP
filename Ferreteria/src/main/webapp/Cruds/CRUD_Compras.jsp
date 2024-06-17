<%-- 
    Document   : RealizarCompras
    Created on : 31 mar 2024, 22:17:38
    Author     : herna
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Realizar compra</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js" integrity="sha384-w1Q4orYjBQndcko6MimVbzY0tgp4pWB4lZ7lr30WKz0vr/aWKhXdBNmNb5D92v7s" crossorigin="anonymous"></script>
        <!-- Fin para que funcione class='dropdown m-b-10' -->
        <!-- Inicio para que funcione sweetalert2@11 -->
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <!-- Fin para que funcione class='Mensajes sweetalert2@11 -->
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


                                <div class="col-md-12 col-xl-12" id="registrar_compra">
                                    <div class="mini-stat clearfix bg-white">
                                        <span class="mini-stat-icon bg-teal mr-0 float-right"><i class="mdi mdi-account"></i></span>
                                        <div class="mini-stat-info text-center">
                                            <H3>COMPRAS REALIZADAS</H3>
                                        </div>
                                        <div class="clearfix"></div>
                                        <div class=" text-center">
                                            <span id="Compras_realizadas" class="counter text-blue-grey">0</span>
                                            Compras Realizadas
                                        </div>
                                    </div>
                                </div>

                            </div>
                            <div class="row">
                                <div class="col-12">
                                    <div class="card m-b-20">
                                        <div class="card-body">
                                            <div id="aqui_tabla"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal fade" id="md_ver_detalle" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-lg" role="document">
                    <div class="modal-content">
                        <div class="modal-header text-center">
                            <sub><h6>DETALLE DE LA COMPRA</h6></sub>
                            <button type="button" class="close" data-dismiss="modal" id="cerrarmodal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>

                        <div class="modal-body">
                            <form name="formulario_registro" id="formulario_registro">
                                <input type="hidden" id="llave_compra" name="llave_compra" value="">
                                <input type="hidden" id="consultar_datos" name="consultar_datos" value="si_registro">
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>Fecha de realización: </label>
                                            <input type="text" autocomplete="off" name="fechaRealizada"
                                                   data-parsley-error-message="Campo requerido" id="fechaRealizada"
                                                   class="form-control" >
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>Recibido por:</label>
                                            <input type="text" autocomplete="off" name="vendedorDetalle"
                                                   data-parsley-error-message="Campo requerido" id="vendedorDetalle"
                                                   class="form-control"  placeholder="Seleccione una fecha">
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>Entregada por:</label>
                                            <!-- DE MOMENTO SERA UN VALOR PREDETERMINADO POR QUE SE TOMARA AUTOMATICAMENTE
                                            DEPENDIENTO DE QUE EMPLEADO HAYA INICIADO SESION -->
                                            <input type="text" id="provedorDetalle" name="provedorDetalle" value="" data-parsley-error-message="Campo requerido" class="form-control" >

                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>Total Compra: </label>
                                            <input type="text" id="totalGastado" name="totalGastado" value="" data-parsley-error-message="Campo requerido" class="form-control" >
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>Cantidad de productos comprados: </label>
                                            <input type="text" id="cantidadComprado" name="cantidadComprado" value="" data-parsley-error-message="Campo requerido" class="form-control" >
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="row">
                                <div class="col-12">
                                    <div class="card m-b-20">
                                        <div class="card-body">
                                            <div id="tabla_modal">
                                                
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                                <div class="modal-footer">
                                    <button type="button" id="btn_cerrar" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                                    <button type="submit" class="btn btn-primary">Abastecer</button>
                                </div>
                            </form>
                        </div> 
                    </div>
                </div>
            </div>
        </div>
    </body>
    <script type="text/javascript" src="//cdn.datatables.net/1.10.24/js/jquery.dataTables.min.js"></script>
    <script src="../js/CrudCompras.js"></script>
</html>
