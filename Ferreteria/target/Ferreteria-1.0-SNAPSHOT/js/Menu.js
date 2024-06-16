verificarSesion();
document.addEventListener('DOMContentLoaded',function(){
    console.log("Entro al menu");
    let usuario=JSON.parse(localStorage.getItem("empleado"));
            console.log(usuario);
            //document.querySelector("#nombreUsu").content=usuario.nombreEmpleado;
            //CREAR UN ARCHIVO js PARA MANEJAR EL MENU
            console.log(usuario.nombreEmpleado);
            
            //MUESTRO LA INFO
            document.querySelector("#nombreUsu").textContent=usuario.nombreEmpleado+" "+usuario.apellidoEmpleado;
            document.querySelector("#rolEmpleado").textContent=usuario.rol.rol;
    $(document).on("click","#menuInicio",function(e){
        e.preventDefault();
        location.reload();
        
    });
    
});
$(function(){
    $(document).on("click","#cerrar_sesion",function(e){
        e.preventDefault();
        Swal.fire('Vuelva pronto','Se ha cerrado la sesion','info');
        Swal.fire('Accion no completada','No se pudo obtener los datos','error');
        localStorage.clear();
        window.location.href='../Cruds/Login.jsp';
    });
});
function verificarSesion(){
    let empleado=JSON.parse(localStorage.getItem('empleado'));
    if(empleado==null){
        console.log("Deberia mostrar mensaje de error");
        window.top.location.href='../Utilidades/RestringirAcceso.jsp';
        
         
    }
    console.log("Paso el if de verificarSesion");
        
}