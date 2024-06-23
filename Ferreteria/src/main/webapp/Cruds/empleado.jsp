

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registro Empleados</title>

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
                                <div class="col-md-6 col-xl-6">
                                    <div class="mini-stat clearfix bg-white">
                                        <span class="mini-stat-icon bg-blue-grey mr-0 float-right"><i class="mdi mdi-black-mesa"></i></span>
                                        <div class="mini-stat-info">
                                            <span id="contar_empleado" class="counter text-blue-grey">0</span>
                                            Empleados
                                        </div>
                                        <div class="clearfix"></div>
                                    </div>
                                </div>

                                <div class="col-md-6 col-xl-6" id="registrar" style="cursor: pointer">
                                    <div class="mini-stat clearfix bg-white">
                                        <span class="mini-stat-icon bg-teal mr-0 float-right"><i class="mdi mdi-account"></i></span>
                                        <div class="mini-stat-info">
                                            <H3>REGISTRAR EMPLEADO</H3>
                                        </div>
                                        <div class="clearfix"></div>
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

            <div class="modal fade" id="md_registrar_empleado" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-lg" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <sub>Campos marcados con * son obligatorios</sub>
                            <button type="button" class="close" data-dismiss="modal" id="cerrarmodal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>

                        <div class="modal-body">
                            <form name="formulario_registro" id="formulario_registro">
                                <input type="hidden" id="dui" name="DUI" value="">
                                <input type="hidden" id="consultar_datos" name="consultar_datos" value="si_registro">
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>DUI*</label>
                                            <input type="text" autocomplete="off" name="duiEmpleado"
                                                   data-parsley-error-message="Campo requerido y no ingresar letras"  id="duiEmpleado"
                                                   class="form-control" pattern="[0123456789-]+" maxlength="10" required placeholder="Ingrese número de DUI" >
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>Nombre*</label>
                                            <input type="text" autocomplete="off" name="nombre"
                                                   data-parsley-error-message="Campo requerido y no ingresar números" id="nombre"
                                                   class="form-control" pattern="[A-Za-zñÑáéíóúÁÉÍÓÚ\s]+" required placeholder="Ingresar Nombre" >
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>Apellido*</label>

                                            <input type="text" autocomplete="off" name="apellido"
                                                   data-parsley-error-message="Campo requerido y no ingresar números" id="apellido"
                                                   class="form-control" pattern="[A-Za-zñÑáéíóúÁÉÍÓÚ\s]+" required placeholder="Ingresar Apellido">
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>Fecha de Nacimiento*</label>
                                            <input type="date" autocomplete="off" name="fechaNacimiento"
                                                   data-parsley-error-message="Campo requerido" id="fechaNacimiento"
                                                   class="form-control" required placeholder="Seleccione una fecha">
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>Dirección*</label>

                                            <input type="text" autocomplete="off" name="direccion"
                                                   data-parsley-error-message="Campo requerido" id="direccion"
                                                   class="form-control" required placeholder="Ingresar Dirección" >
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>Teléfono*</label>

                                            <input type="text" autocomplete="off" name="telefono"
                                                   data-parsley-error-message="Campo requerido y no ingresar letras" id="telefono"
                                                   class="form-control" pattern="[0123456789]+" maxlength="8" required placeholder="Ingresar número de teléfono" >
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>Sexo*</label>
                                            <select id="genero"  data-parsley-error-message="Campo requerido" name="genero" class="form-control" required placeholder=" "  >
                                          
                                                <option value="">Seleccione</option>
                                                <option value="M">Masculino</option>
                                                <option value="F">Femenino</option>
                                            </select>
                                            
                                        </div>

                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>Estado*</label>
                                            <select id="estado" data-parsley-error-message="Campo requerido" name="estado" class="form-control" required placeholder=" ">
                                                <option value="">Seleccione</option>
                                                <option value="true">Activo</option>
                                                <option value="false">Inactivo</option>
                                            </select>
                                        </div> 
                                    </div>  

                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>Rol*</label>
                                            <select id="idRol" data-parsley-error-message="Campo requerido" name="idRol" class="form-control" required placeholder=" ">
                                                <option value="">Seleccione</option>

                                            </select>
                                        </div> 
                                    </div> 
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>Correo Electrónico*</label>

                                            <input type="text" autocomplete="off" name="correo"
                                                   data-parsley-error-message="Campo requerido" id="correo"
                                                   class="form-control" required placeholder="Ingresar correo electrónico">
                                        </div>
                                    </div>

                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>Contraseña*</label>

                                            <input type="password" autocomplete="off" name="contraseña"
                                                   data-parsley-error-message="Campo requerido" id="contraseña"
                                                   class="form-control" required placeholder="Ingresar contraseña " >
                                        </div>
                                    </div>
                                </div>
                                <div class="modal-footer">                           

                                    <button type="button" id="btn_cerrar" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                                    <button type="submit" class="btn btn-primary">Guardar</button>
                                </div>
                            </form>
                        </div>

                    </div> 
                </div>
            </div>

        </div>
    </body>

    <script type="text/javascript" src="//cdn.datatables.net/1.10.24/js/jquery.dataTables.min.js"></script>
    <script src="../js/empleado.js"></script>
</html>
