package com.sgp.sistemaDeGestionDePacientes.modelos;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "bd_gestionDePagos")
public class GestionDePagos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGestionDePagos;

    @Column(name = "dni")
    @Size(max = 10)
    private Integer dni;

    @Column(name = "tratamiento", length = 1500)
    private Long tratamiento;

    @Column(name = "cantidadSesionesPagadas")
    @Size(max = 3)
    private Integer cantidadSesionesPagadas;

    @Column(name = "importePagado")
    private Double importePagado;

    @Column(name = "fechaPago")
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm") //Si no agrego esta linea me da un error de parseo de SpringBoot con Thymeleaf
    private LocalDateTime fechaPago;

    @Column(name = "observaciones", length = 1000)
    private String observaciones;

    //Constructores
    public GestionDePagos() {
    }

    public GestionDePagos(Long idGestionDePagos, Integer dni, Long tratamiento, Integer cantidadSesionesPagadas, Double importePagado, LocalDateTime fechaPago, String observaciones) {
        this.idGestionDePagos = idGestionDePagos;
        this.dni = dni;
        this.tratamiento = tratamiento;
        this.cantidadSesionesPagadas = cantidadSesionesPagadas;
        this.importePagado = importePagado;
        this.fechaPago = fechaPago;
        this.observaciones = observaciones;
    }

    //Getters and Setters

    public Long getIdGestionDePagos() {
        return idGestionDePagos;
    }

    public void setIdGestionDePagos(Long idGestionDePagos) {
        this.idGestionDePagos = idGestionDePagos;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public Long getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(Long tratamiento) {
        this.tratamiento = tratamiento;
    }

    public Integer getCantidadSesionesPagadas() {
        return cantidadSesionesPagadas;
    }

    public void setCantidadSesionesPagadas(Integer cantidadSesionesPagadas) {
        this.cantidadSesionesPagadas = cantidadSesionesPagadas;
    }

    public Double getImportePagado() {
        return importePagado;
    }

    public void setImportePagado(Double importePagado) {
        this.importePagado = importePagado;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
