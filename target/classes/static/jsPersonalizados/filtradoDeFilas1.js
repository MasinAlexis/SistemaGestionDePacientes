// Función para mostrar u ocultar filas según la selección del menú desplegable
        function mostrarFilas1() {
            var cantidadFilas = parseInt(document.getElementById('cantidadFilas1').value);
            var filas = document.querySelectorAll('.fila1');
            for (var i = 0; i < filas.length; i++) {
                if (i < cantidadFilas) {
                    filas[i].style.display = 'table-row';
                } else {
                    filas[i].style.display = 'none';
                }
            }
        }
        // Escucha el evento "DOMContentLoaded" para ejecutar la función al cargar la página
        document.addEventListener('DOMContentLoaded', mostrarFilas1);
        // Escucha el evento "change" del menú desplegable para actualizar las filas al cambiar la selección
        document.getElementById('cantidadFilas1').addEventListener('change', mostrarFilas1);