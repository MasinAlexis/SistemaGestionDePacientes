$(document).ready(function() {
            $('[data-toggle="modal"]').click(function() {
                var idTratamiento = $(this).attr('data-id');
                $('#confirmModal').find('.btn-danger').attr('href', '/borrarTratamiento?idTratamiento=' + idTratamiento);
            });
        });