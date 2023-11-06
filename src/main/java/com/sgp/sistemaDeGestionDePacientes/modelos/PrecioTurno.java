package com.sgp.sistemaDeGestionDePacientes.modelos;

import javax.persistence.*;

import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "bd_precioTurno")
public class PrecioTurno {
    //Esta clase sera la que almacenara el precio de la sesion, solamente contara
    //con un solo registro
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "precioSesion", precision = 8, scale = 2)
    @Digits(integer = 6, fraction = 2)
    private BigDecimal precioSesion;

    //Fecha en la que se cambio el importe
    @Column(name = "fechaDeImplementacion")
    private Date fechaDeImplementacion;

    //Constructor

    public PrecioTurno(Integer id, BigDecimal precioSesion, Date fechaDeImplementacion) {
        this.id = id;
        this.precioSesion = precioSesion;
        this.fechaDeImplementacion = fechaDeImplementacion;
    }

    public PrecioTurno() {
    }

    //Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getPrecioSesion() {
        return precioSesion;
    }

    public void setPrecioSesion(BigDecimal precioSesion) {
        this.precioSesion = precioSesion;
    }

    public Date getFechaDeImplementacion() {
        return fechaDeImplementacion;
    }

    public void setFechaDeImplementacion(Date fechaDeImplementacion) {
        this.fechaDeImplementacion = fechaDeImplementacion;
    }
}
