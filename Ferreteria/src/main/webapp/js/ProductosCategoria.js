$(function(){
    console.log("Entro al javascript");
    cargarTablaProductos();
});





function cargarTablaProductos(estado=1){
    var datos={"consultar_datos":"si_consulta","estado":estado};//CREAMOS UN ARREGLO
    $.ajax({
        dataType:"json",
        method: "POST",
        url: "../ControllerCategoria",
        data:datos
    }).done(function (json){
       Swal.close();
        if (json[0].resultado==="exito") {
            $("#aqui_tabla").empty().html(json[0].tabla);
            console.log("Entro al  done");
            //LE CAMBIE EL NOMBRE AL SPAN QUE CONTIENE EL NUMERO DE REGISTROS
            document.querySelector("#Cantidad_productos").textContent=json[0].cuantos;
            //AQUI LE CAMBIE EL NOMBRE A LA TABLA
            $("#tabla_producto").DataTable({
                "language": {
                    "url": "//cdn.datatables.net/plug-ins/1.10.15/i18n/Spanish.json"
                }
                
            });
            console.log("Paso el #tabla_producto");
        }else{
            Swal.fire('Accion no completada','No se pudo obtener los datos','error');
        }
    }).fail(function (){
        
    }).always(function(){
        
    });
    
    
}