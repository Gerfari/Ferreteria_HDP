<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Inicio</title>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <link href="../css/styles.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="http://parsleyjs.org/dist/parsley.js"></script>
        
    </head>

    <body class="sb-nav-fixed" style="background-image: url('../img/fondoferre.jpg'); background-size: cover; background-repeat: no-repeat; background-attachment: fixed; height: 100vh; margin: 0;">


        <nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
            <!-- Navbar Brand-->
            <a class="navbar-brand ps-3"><i class="fa-solid fa-hammer"></i> Ferretería<br>El Martillazo</a>
            
            <!-- Sidebar Toggle-->
            <button class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0" id="sidebarToggle" href="#!"><i class="fas fa-bars"></i></button>
            <!-- Elimine Navbar Search-->
            <form class="d-none d-md-inline-block form-inline ms-auto me-0 me-md-3 my-2 my-md-0"></form>
            <!-- Navbar-->
            <span id="nombreUsu" style="color: #ffffff"></span>
            <ul class="navbar-nav ms-auto ms-md-0 me-3 me-lg-4">
                
                <li class="nav-item dropdown">
                    
                    <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false"><i class="fas fa-user fa-fw"></i></a>
                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                        
                        <!-- AQUI SE PUEDEN AGREGAR LAS DIFERENTES OPCIONES -->
                        <li><a class="dropdown-item" href="#!"></a></li>
                        <li><a class="dropdown-item" href="#!"></a></li>
                                    <li><hr class="dropdown-divider" /></li>
                                    <li id="cerrar_sesion"><a class="dropdown-item"  style="cursor: pointer"> <i class="fa-solid fa-right-from-bracket"></i>Cerrar Sesión</a></li>
                    </ul>
                </li>
            </ul>
        </nav>
        <!-- inicio menu -->

        <div id="layoutSidenav">
            <div id="layoutSidenav_nav">
                <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
                    <div class="sb-sidenav-menu">
                        <div class="nav">
                            <div class="sb-sidenav-menu-heading">Inicio</div>
                            <a class="nav-link" id="menuInicio" href="#">
                                <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                                Menu
                            </a>
                            <div class="sb-sidenav-menu-heading">Mantenimientos</div>
                            <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapseLayoutsIndependientes" aria-expanded="false" aria-controls="collapseLayoutsIndependientes">
                                <div class="sb-nav-link-icon"><i class="fas fa-columns"></i></div>
                                Independientes
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                            </a>
                            <div class="collapse" id="collapseLayoutsIndependientes" aria-labelledby="headingOne" data-bs-parent="#sidenavAccordion">
                                <nav class="sb-sidenav-menu-nested nav">
                                    <a class="nav-link" href="../Cruds/CRUD_Clientes.jsp" target="myFrame">Clientes</a>
                                    
                                </nav>
                            </div>

                            <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapseLayoutsDependientes" aria-expanded="false" aria-controls="collapseLayoutsDependientes">
                                <div class="sb-nav-link-icon"><i class="fas fa-columns"></i></div>
                                Dependientes
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                            </a>
                            <div class="collapse" id="collapseLayoutsDependientes" aria-labelledby="headingOne" data-bs-parent="#sidenavAccordion">
                                <nav class="sb-sidenav-menu-nested nav">
                                    <a class="nav-link" href="../Cruds/CRUD_Productos.jsp" target="myFrame">Productos</a>
                                    <a class="nav-link" href="../Cruds/Compras.jsp" target="myFrame">Realizar compras</a>
                                    <a class="nav-link" href="../Cruds/Ventas.jsp" target="myFrame">Realizar ventas</a>
                                </nav>
                            </div>
                            <div class="sb-sidenav-menu-heading">Consultas</div>
                            <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapsePages" aria-expanded="false" aria-controls="collapsePages">
                                <div class="sb-nav-link-icon"><i class="fas fa-book-open"></i></div>
                                Consultas
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                            </a>
                            <div class="collapse" id="collapsePages" aria-labelledby="headingTwo" data-bs-parent="#sidenavAccordion">
                                <nav class="sb-sidenav-menu-nested nav accordion" id="sidenavAccordionPages">
                                    <a class="nav-link" href="" target="myFrame">Consulta 1 </a>
                                    <a class="nav-link" href="../Consultas/ReporteVentas.jsp" target="myFrame">Reporte De Ventas</a>
                                    <a class="nav-link" href="../Consultas/HistorialCompras.jsp" target="myFrame">Compras Por Cliente</a>
                                    <a class="nav-link" href="../Cruds/CRUD_Compras.jsp" target="myFrame">Detalles De Compras </a>
                                    <a class="nav-link" href="../Consultas/InformeComprasProductos.jsp" target="myFrame">Abastecimiento</a>
                                    <a class="nav-link" href="../Consultas/ProductosCategoria.jsp" target="myFrame">Productos Categoria</a>
                                    <a class="nav-link" href="../Consultas/ProductosDisponible.jsp" target="myFrame">Productos Disponible </a>
                                    <a class="nav-link" href="../Consultas/VentasEnIntervaloDeFechas.jsp" target="myFrame">Ventas Entre Fechas</a>
                                    <a class="nav-link" href="../Consultas/VentasYDetalle.jsp" target="myFrame">Ventas y Detalle de venta </a>
                                    <a class="nav-link" href="" target="myFrame">Consulta 10 </a>
                                </nav>
                            </div>
                            <div class="sb-sidenav-menu-heading">Otra Opción</div>
                            <a class="nav-link" href="charts.html">
                                <div class="sb-nav-link-icon"><i class="fas fa-chart-area"></i></div>
                                Opcion 1
                            </a>
                            <a class="nav-link" href="tables.html">
                                <div class="sb-nav-link-icon"><i class="fas fa-table"></i></div>
                                Opcion 2
                            </a>
                        </div>
                    </div>
                    <div class="sb-sidenav-footer">
                        <div class="small">Iniciado sesión como: </div>
                        <!-- Agregar usuario logueado -->
                        <span id="rolEmpleado"></span>
                        <!-- Agregar usuario logueado -->
                    </div>
                </nav>
            </div>

            <!-- pie de pagina -->
            <div id="layoutSidenav_content">
                <!-- ES IMPORTANTE NO TOCAR -->
                <iframe name="myFrame" class="embed-responsive-item" style="height: 100%; width: 100%; border: none" ></iframe>
                <!-- ES IMPORTANTE NO TOCAR -->
                <!-- pie de pagina -->
                <!-- Color anterior bg-light mt-auto-->

                <footer class="py-2" style="background-color: #212529;">
                    <div class="d-flex align-items-center justify-content-between small">
                        <div class="text-white"></div>
                        <div class="text-white" style="font-size: 14px;">© 2024 Universidad de El Salvador. Ferretería El Martillazo. Todos los derechos reservados.</div>
                        <div class="text-white"></div>
                    </div>
                </footer>
            </div><!-- pie de pagina -->
        </div> <!-- fin menu -->

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="../js/scripts.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
        <script src="assets/demo/chart-area-demo.js"></script>
        <script src="assets/demo/chart-bar-demo.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
        <script src="../js/datatables-simple-demo.js"></script>
        <script src="../js/Menu.js"></script>
        
    </body>
</html>

