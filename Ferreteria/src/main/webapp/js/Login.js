document.addEventListener('DOMContentLoaded', function() {
   // const loginsec = document.querySelector('.login-section');
   // const loginlink = document.querySelector('.login-link');
  //  const registerlink = document.querySelector('.register-link');

   /* registerlink.addEventListener('click', () => {
        loginsec.classList.add('active');
    });*/

    /*loginlink.addEventListener('click', () => {
        loginsec.classList.remove('active');
    });*/



    
    $('#formulario_login').on('submit', function(e) {
        e.preventDefault(); 

       
        const usuario = $('#usuario').val();
        const contrasenia = $('#contrasenia').val();

       
        const formData = {
            usuario: usuario,
            contrasenia: contrasenia
        };
  
        console.log('Datos enviados al servidor:', formData);

       
        $.ajax({
            type: 'POST',
            url: '..//LoginServlet',
            data: formData, 
            dataType: 'json', 
            success: function(response) {
                
                if (response.autenticado) {
                   Swal.fire('Exito', 'Bienvenido', 'info').then(() => {
                       console.log("No dio problemas el json"+response);
                       console.log("Esto devuelve el json: "+response.autenticado);
                       console.log("Esto devuelve el json: "+response.empleado.nombreEmpleado);
                       localStorage.setItem('empleado',JSON.stringify(response.empleado ));
                    window.location.href = '../Menu/Menu.jsp';
                });
                } else {
                   Swal.fire('Error', 'User o password incorrectos. Por favor, intentelo de nuevo.', 'error');
                }
            },
            error: function(xhr, status, error) {
               
                console.error('Error en la solicitud AJAX:', error);
                Swal.fire('Error','El correo o contraseña no es correctos')
            }
        });
    });
});
