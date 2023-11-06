package com.sgp.sistemaDeGestionDePacientes.repositorios;

import com.sgp.sistemaDeGestionDePacientes.modelos.GestionDePagos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RepositorioGestionDePagos extends JpaRepository<GestionDePagos,Long> {

    @Query("SELECT gp " +
            "FROM TratamientoPaciente tp JOIN GestionDePagos gp ON tp.idTratamiento = gp.tratamiento " +
            "WHERE tp.dni = :dni AND tp.tratamientoConcluido = 0")
    List<GestionDePagos> buscarPagosAbiertos(Integer dni);

    @Query("FROM GestionDePagos gp WHERE gp.idGestionDePagos = :id")
    GestionDePagos buscarPorId(Long id);

    @Query("FROM GestionDePagos gp WHERE gp.tratamiento = :idTratamiento")
    List<GestionDePagos> buscarPorTratamiento(Long idTratamiento);

    @Query("SELECT gp " +
           "FROM TratamientoPaciente tp JOIN GestionDePagos gp ON tp.idTratamiento = gp.tratamiento " +
           "WHERE tp.dni = :dni AND tp.tratamientoConcluido = 1")
    List<GestionDePagos> buscarPagosCerrados(Integer dni);

    List<GestionDePagos> findAllByFechaPagoBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);
}
