package com.sgp.sistemaDeGestionDePacientes.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicePrecioTurno {

    @Autowired
    private RepositorioPrecioTurno repositorioPrecioTurno;

    public double obtenerPrecioActual() {
        // Logic to retrieve the current price
        return repositorioPrecioTurno.findCurrentPrice();
    }

    public void guardarNuevoPrecio(double nuevoPrecio) {
        // Logic to save new price
        repositorioPrecioTurno.save(nuevoPrecio);
    }
}