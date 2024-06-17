<%-- 
    Document   : VentasEnIntervaloDeFechas
    Created on : 29 mar 2024, 17:13:48
    Author     : herna
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ventas entre fechas</title>
        <!-- Inicio para que funcione class='dropdown m-b-10' -->
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
    <body>
        <div class="row">
            <div class="container">
                <h2 class="text-center">VENTAS REALIZADAS EN UN PERIODO DE TIEMPO</h2>
                <label class="col-sm-6 control-label text-center">Seleccione el intervalo de fechas</label>
                <div class="col-sm-6 mx-auto">
                    <form name="formulario_datos" id="formulario_datos">
                        <input type="hidden" id="consultar_datos" name="consultar_datos" value="envio_fechas"/>
                        <label for="fechaInicio">Desde:</label>
                        <select id="fechaInicio" name="fechaInicio" class="form-control" required>
                            <option value="">Seleccione</option>
                        </select><br>
                        <label for="fechaFin">Hasta:</label>
                        <select id="fechaFin" name="fechaFin" class="form-control" required>
                            <option value="">Seleccione</option>
                        </select><br>
                        <button type="submit" class="btn btn-primary">Enviar</button>
                    </form>

                </div>
            </div>
        </div>

        <!-- Start right Content here -->
        <div class="content-page">
            <!-- Start content -->
            <div class="content">
                <!-- Top Bar Start -->
                <!-- Top Bar End -->
                <!-- ==================
                PAGE CONTENT START
                ================== -->
                <div class="page-content-wrapper">
                    <div class="container-fluid">
                        <div class="row">

                            <br>
                            <div class="col-md-12 col-xl-12" id="mostrar_info" >
                                <div class="mini-stat clearfix bg-white">
                                    <span class="mini-stat-icon bg-teal mr-0 float-right"><i
                                            class="mdi mdi-account"></i></span>
                                    <div class="mini-stat-info text-center">  
                                        <br>
                                        <H4><span id="Cantidad_registros"></span> VENTAS REALIZADAS ENTRE <span id="fechaDesde"></span> Y <span id="fechaHasta"></span>
                                            <br>
                                            TOTAL RECAUDADO: $ <span id="totalRecaudado"></span>
                                        </H4>

                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                        </div><br>
                        <div class="row">
                            <div class="col-12">
                                <div class="card m-b-20">
                                    <div class="card-body">
                                        <div id="aqui_tabla"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div><!-- container -->
                </div> <!-- Page content Wrapper -->
            </div> <!-- content -->
        </div>
    </body>
    <script type="text/javascript" src="//cdn.datatables.net/1.10.24/js/jquery.dataTables.min.js"></script>
    <script src="../js/VentasIntervaloFecha.js"></script>
</html>
