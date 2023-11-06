// Función para mostrar u ocultar filas según la selección del menú desplegable
        function mostrarFilas() {
            var cantidadFilas = parseInt(document.getElementById('cantidadFilas').value);
            var filas = document.querySelectorAll('.fila');
            for (var i = 0; i < filas.length; i++) {
                if (i < cantidadFilas) {
                    filas[i].style.display = 'table-row';
                } else {
                    filas[i].style.display = 'none';
                }
            }
        }
        // Escucha el evento "DOMContentLoaded" para ejecutar la función al cargar la página
        document.addEventListener('DOMContentLoaded', mostrarFilas);
        // Escucha el evento "change" del menú desplegable para actualizar las filas al cambiar la selección
        document.getElementById('cantidadFilas').addEventListener('change', mostrarFilas);