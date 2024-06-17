<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login</title>
        <link rel="stylesheet" href="..//css/styles_1.css">
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    </head>
    <body>

        <!-- LOGIN -->
        <div class="background"></div>
        <div class="container">
            <div class="item">
                <h2 class="logo"><i class='bx bxs-building'></i>FERRETERIA HDP175</h2>
                <div class="text-item">
                    <h2>¡Bienvenido! <br><span>
                            Estamos encantados de tenerte de nuevo.
                        </span></h2>
                    <p>Gracias a ti, estamos creciendo más allá de nuestras expectativas. 
                        Compartamos el éxito cada día.</p>
                    <div class="social-icon">
                        <a href="#"><i class='bx bxl-facebook'></i></a>
                        <a href="#"><i class='bx bxl-twitter'></i></a>
                        <a href="#"><i class='bx bxl-instagram'></i></a>
                        <a href="#"><i class='bx bxl-tiktok'></i></a>
                    </div>
                </div>
            </div>
            <div class="login-section">
                <div class="form-box login">
                    <form action="" id="formulario_login">
                        <h2>Iniciar Sesión</h2>
                        <div class="input-box">
                            <span class="icon"><i class='bx bxs-user-account'></i></span>
                            <input type="text" id="usuario" required>
                            <label>Usuario</label>
                        </div>
                        <div class="input-box">
                            <span class="icon"><i class='bx bxs-lock-alt' ></i></span>
                            <input type="password" id="contrasenia" required>
                            <label>Contraseña</label>
                        </div>
                      
                        <button class="btn" id="ingresar">Ingresar</button>
                       
                    </form>
                </div>
 

            </div>
        </div>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="..//js/Login.js"></script>
    </body>
</html>