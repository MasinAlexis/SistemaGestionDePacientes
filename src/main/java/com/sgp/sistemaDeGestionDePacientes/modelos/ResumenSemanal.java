package com.sgp.sistemaDeGestionDePacientes.modelos;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.util.Date;

@Entity
public class ResumenSemanal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idResumenSemanal")
    private Long idResumenSemanal;

    @Column(name = "diaSemanaInicio")
    private Date diaSemanaInicio;

    @Column(name = "diaSemanaFin")
    private Date diaSemanaFin;

    @Column(name = "totalSemanal")
    @Digits(integer = 7, fraction = 2)
    private Double totalSemanal;

    //Getters and setters

    public Long getIdResumenSemanal() {
        return idResumenSemanal;
    }

    public void setIdResumenSemanal(Long idResumenSemanal) {
        this.idResumenSemanal = idResumenSemanal;
    }

    public Date getDiaSemanaInicio() {
        return diaSemanaInicio;
    }

    public void setDiaSemanaInicio(Date diaSemanaInicio) {
        this.diaSemanaInicio = diaSemanaInicio;
    }

    public Date getDiaSemanaFin() {
        return diaSemanaFin;
    }

    public void setDiaSemanaFin(Date diaSemanaFin) {
        this.diaSemanaFin = diaSemanaFin;
    }

    public Double getTotalSemanal() {
        return totalSemanal;
    }

    public void setTotalSemanal(Double totalSemanal) {
        this.totalSemanal = totalSemanal;
    }

    //Constructor

    public ResumenSemanal() {}

    public ResumenSemanal(Long idResumenSemanal, Date diaSemanaInicio, Date diaSemanaFin, Double totalSemanal) {
        this.idResumenSemanal = idResumenSemanal;
        this.diaSemanaInicio = diaSemanaInicio;
        this.diaSemanaFin = diaSemanaFin;
        this.totalSemanal = totalSemanal;
    }
}
