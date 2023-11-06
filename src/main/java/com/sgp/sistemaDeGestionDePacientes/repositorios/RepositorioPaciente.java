package com.sgp.sistemaDeGestionDePacientes.repositorios;

import com.sgp.sistemaDeGestionDePacientes.modelos.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioPaciente extends JpaRepository<Paciente, Long> {

    @Query("FROM Paciente p WHERE p.dni = :dni")
    public Paciente findPacienteByDni(Integer dni);

    @Query("FROM Paciente p WHERE p.idPaciente = :idPaciente")
    public Paciente buscarPorId(Long idPaciente);

    @Query("FROM Paciente p WHERE p.bajaLogica = 0")
    public List<Paciente> buscarPacientesActivos();

    @Query("FROM Paciente p WHERE p.bajaLogica = 1")
    public List<Paciente> buscarPacientesInactivos();
}
