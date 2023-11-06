package com.sgp.sistemaDeGestionDePacientes.repositorios;

import com.sgp.sistemaDeGestionDePacientes.modelos.PrecioTurno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioPrecioTurno extends JpaRepository<PrecioTurno, Integer> {
}
