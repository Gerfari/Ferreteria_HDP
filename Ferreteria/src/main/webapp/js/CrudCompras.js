$(function (){
   //.parsley ES PARA VALIDAR LOS DATOS DEL FORMULARIO
   $('#formulario_registro').parsley();
    cargarTabla();
    
   //CUANDO CLICKIEMOS LAS LETRAS REGISTRAR COMPRA SE MOSTRARA EL MODAL
   $(document).on("click","#registrar_compra",function(e){
       //CODIGO PARA ENVIAR EL PROXIMO CODIGO PERO QUE SOLO LO MUESTRE
       var codEnNumero=document.getElementById('Compras_realizadas').textContent;
       //CONVERTIMOS DE TEXTO A NUMERO BASE 10
       var codSiguiente=parseInt(codEnNumero,10)+1;
       //PARA ENVIAR LA FECHA ACTUAL AL CALENDARIO
       var fecha=new Date();
       //FECHA CON FORMATO YYYY-MM-DD
       var fechaFormateada = fecha.toISOString().slice(0,10);
       
       console.log("extrayenco el elemento por id "+codEnNumero+" convirtiendo en numero "+codSiguiente);
       e.preventDefault();
       $("#formulario_registro").trigger("reset");
       $("#md_registrar_compra").modal("show");
       //PARA QUE LOS CAMPOS NO SE PUEDAN EDITAR POR QUE SON REGISTROS QUE DEPENDEN DE QUIEN HAYA INICIADO SESION  
       //PARA MOSTRA EL SIGUIENTE CODIGO A REGISTRAR
       document.querySelector('#codigocompra').value=codSiguiente;
       //PARA ENVIAR LA FECHA ACTUAL AL CALENDARIO
       document.querySelector('#fechacompra').value=fechaFormateada;
       document.querySelector('#codigocompra').readOnly=true;
       document.querySelector('#empleado').readOnly=true;
       
       cargar_combo_proveedores();
   });
   
   //METODO PARA REGISTRAR LA COMPRA
   $(document).on("submit","#formulario_registro",function(e){
      e.preventDefault();
        mostrar_cargando("Procesando Solicitud", "Espere mientras se almacenan los datos");
        var datos=$("#formulario_registro").serialize();
      
      $.ajax({
          dataType: "json",
          method: "POST",
          url:"../ControllerCompra",
          data:datos
      }).done(function(json){
          Swal.close();
            if (json[0].resultado==="exito") {
                Swal.fire('Exito','Compra realizada','success');
                $("#md_registrar_compra").modal("hide");
                cargarTabla();
                location.reload();
            }else{
                Swal.fire('Accion no completada',"No se puede realizar la compra","Error");
            }
      }).fail(function(){
          
      }).always(function(){
          
      });
      
   });
   //METODO PARA CERRA EL MODAL
   //DESDE LA X
   $(document).on("click","#cerrarmodal",function(e){
       e.preventDefault();
       Swal.fire('Cancelado','Se cancelo el registro','info');
       $("#md_registrar_compra").modal("hide");
   });
   //DESDE EL BOTON CERRAR
   $(document).on("click","#btn_cerrar",function(e){
      e.preventDefault();
      Swal.fire('Cancelado','Se cancelo el registro','info');
      $("#md_registrar_compra").modal("hide");
   });
});
//CREAMOS EL METODO PARA MOSTRAR
function cargarTabla(estado = 1) {
    mostrar_cargando("procesando solicitud", "Espere mientras se procesa la informacion");
    var datos = { "consultar_datos": "si_consulta", "estado": estado };

    $.ajax({
        dataType: "json",
        method: "POST",
        url: "../ControllerCompra",
        data: datos
    }).done(function(json) {
        Swal.close();
        if (json[0].resultado === "exito") {
            $("#aqui_tabla").empty().html(json[0].tabla);
            document.querySelector("#Compras_realizadas").textContent = json[0].cuantos;

            // Inicializar DataTables después de cargar la tabla
            $('#tabla_compras').DataTable({
                "language": {
                    "url": "//cdn.datatables.net/plug-ins/1.10.24/i18n/Spanish.json"
                },
                "searching": true // Habilitar la funcionalidad de búsqueda
            });
        } else {
            Swal.fire('Accion no completada', 'No se pudo obtener los datos', 'error');
        }
    }).fail(function() {}).always(function() {});
}

//FUNCION PARA CARGAR EL COMBOBOX DEL PROVEEDOR
function cargar_combo_proveedores(estado=1){
    var datos={"consultar_datos":"llenar_combo_proveedor","estado":estado};
    const proveedorSelec = document.getElementById("proveedorSeleccionado");
    proveedorSelec.innerHTML="<option value=''>Seleccione</option>";
    console.log("Entro a cargar combo");
    $.ajax({
       dataType:'json',
       method: "POST",
       url: "../ControllerCompra",
       data:datos
    }).done(function(json){
        Swal.close();
        console.log("datos recibidos: ",json);
        if (json[0].resultado==="exito") {
            const proveedores=json[0].proveedores;
            for (var i = 0; i < proveedores.length; i++) {
                const id_prove=proveedores[i].idProveedor;
                const nombre_prove=proveedores[i].nombreProveedor;
                //AGREGANDO EL PROVEEDOR COMO UNA OPCION DEL COMBOBOX
                //AGREGAMOS VALOR Y SU ID
                const optionProveedor = new Option(nombre_prove,id_prove);
                proveedorSelec.appendChild(optionProveedor);
            }
        }else{
            console.log("El resultado del if fue un fallo");
        }
    }).fail(function(){
        
    }).always(function(){
        
    });
    
    
    
    
}

function mostrar_cargando(titulo, mensaje = "") {
    Swal.fire({
        title: titulo,
        html: mensaje,
        timer: 2000,
        timerProgessBar: true,
        didOpen: () => {
            Swal.showLoading();
        },
        willClose: () => {
        }
    }).then((result) => {
        if (result.dismiss === Swal.DismissReason.timer) {
            console.log('I was closed by timer');
        }
    });
}
