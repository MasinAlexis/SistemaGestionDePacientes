function aplicarBuscador(idBuscador, idTabla) {
        const buscador = document.getElementById(idBuscador);
        const tabla = document.getElementById(idTabla);
        const filas = tabla.getElementsByTagName('tr');

        buscador.addEventListener('keyup', function() {
            const textoBusqueda = buscador.value.toLowerCase();

            for (let i = 1; i < filas.length; i++) {
                const fila = filas[i];
                const celdas = fila.getElementsByTagName('td');
                let mostrarFila = false;

                for (let j = 0; j < 2; j++) {
                    const contenido = celdas[j].textContent.toLowerCase();

                    if (contenido.includes(textoBusqueda)) {
                        mostrarFila = true;
                        break;
                    }
                }

                if (mostrarFila) {
                    fila.style.display = '';
                } else {
                    fila.style.display = 'none';
                }
            }
        });
    }

    aplicarBuscador('buscador1', 'tabla1');
    aplicarBuscador('buscador2', 'tabla2');