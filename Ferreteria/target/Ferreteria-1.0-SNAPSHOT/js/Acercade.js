verificarSesion();

function verificarSesion(){
    let empleado = JSON.parse(localStorage.getItem('empleado'));
    if(empleado == null) {
        console.log("Deberia mostrar mensaje de error");
        window.top.location.href = '../Utilidades/RestringirAcceso.jsp';
    }
    console.log("Paso el if de verificarSesion");
}

