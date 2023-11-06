package com.sgp.sistemaDeGestionDePacientes.repositorios;

import com.sgp.sistemaDeGestionDePacientes.modelos.TratamientoPaciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioTratamientoPaciente extends JpaRepository<TratamientoPaciente, Long> {

    @Query("FROM TratamientoPaciente tp WHERE tp.dni = :dni AND tp.tratamientoConcluido = 0")
    List<TratamientoPaciente> findByDni(Integer dni);

    @Query("FROM TratamientoPaciente tp WHERE tp.dni = :dni AND tp.tratamientoConcluido = 1")
    List<TratamientoPaciente> tratamientosCerrados(Integer dni);

    @Query("FROM TratamientoPaciente tp WHERE tp.idTratamiento = :id")
    TratamientoPaciente buscarPorId(Long id);
}
