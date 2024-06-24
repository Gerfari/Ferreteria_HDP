    verificarSesion();
$(function(){

    console.log("Entro al javascript");
    cargarTablaProductos();
});


function cargarTablaProductos(estado=1){
    var datos={"consultar_dato":"si_consulta","estado":estado};//CREAMOS UN ARREGLO
    $.ajax({
        dataType:"json",
        method: "POST",
        url: "../ControllerProveedorDemandado",
        data:datos
    }).done(function (json){
       Swal.close();
        if (json[0].resultado==="exito") {
            $("#aqui_tabla").empty().html(json[0].tabla);
            console.log("Entro al  done");
            //LE CAMBIE EL NOMBRE AL SPAN QUE CONTIENE EL NUMERO DE REGISTROS
            document.querySelector("#Cantidad_prov").textContent=json[0].cuantos;
            //AQUI LE CAMBIE EL NOMBRE A LA TABLA
            $("#tabla_demanda").DataTable({
                "language": {
                    "url": "//cdn.datatables.net/plug-ins/1.10.15/i18n/Spanish.json"
                }
                
            });
            console.log("Paso el #tabla_demandado");
        }else{
            Swal.fire('Accion no completada','No se pudo obtener los datos','error');
        }
    }).fail(function (){
        
    }).always(function(){
        
    });
    
 
    
    
}
   function verificarSesion(){
    let empleado = JSON.parse(localStorage.getItem('empleado'));
    if(empleado == null) {
        console.log("Deberia mostrar mensaje de error");
        window.top.location.href = '../Utilidades/RestringirAcceso.jsp';
    }
    console.log("Paso el if de verificarSesion");
}