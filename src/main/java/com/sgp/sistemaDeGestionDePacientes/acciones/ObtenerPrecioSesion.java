package com.sgp.sistemaDeGestionDePacientes.acciones;

import com.sgp.sistemaDeGestionDePacientes.modelos.PrecioTurno;
import com.sgp.sistemaDeGestionDePacientes.repositorios.RepositorioPrecioTurno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;

@Repository
public class ObtenerPrecioSesion {

    @Autowired
    private RepositorioPrecioTurno repositorioPrecioTurno;

    public PrecioTurno obtenerPrecioSesion(){
        Collection<PrecioTurno> precioTurno = repositorioPrecioTurno.findAll();
        PrecioTurno resguardoPrecioTurno = new PrecioTurno();
        Date fechaReciente = null;
        for (PrecioTurno precio: precioTurno) {
            if (fechaReciente == null){
                resguardoPrecioTurno = precio;
            } else if (precio.getFechaDeImplementacion().after(fechaReciente)){
                resguardoPrecioTurno = precio;
            }
        }
        return resguardoPrecioTurno;
    }
}
