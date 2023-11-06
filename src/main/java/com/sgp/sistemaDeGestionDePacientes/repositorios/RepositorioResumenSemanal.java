package com.sgp.sistemaDeGestionDePacientes.repositorios;

import com.sgp.sistemaDeGestionDePacientes.modelos.ResumenSemanal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RepositorioResumenSemanal extends JpaRepository<ResumenSemanal, Long> {

    @Query("FROM ResumenSemanal r ORDER BY r.diaSemanaInicio DESC")
    List<ResumenSemanal> buscarTodosPorFechaDesc();

    @Query("FROM ResumenSemanal r WHERE r.diaSemanaInicio = :diaInicio AND r.diaSemanaFin = :diaFin")
    ResumenSemanal buscarPeriodoSemanal(Date diaInicio, Date diaFin);
}
