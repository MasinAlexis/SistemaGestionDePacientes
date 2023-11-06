package com.sgp.sistemaDeGestionDePacientes.modelos;

import javax.persistence.*;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "bd_tratamientoPaciente")
public class TratamientoPaciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTratamiento;

    @Column(name = "dni")
    @Size(max = 10)
    private Integer dni;

    @Column(name = "tratamiento", length = 1000)
    private String tratamiento;

    @Column(name = "cantidadDeTurnos")
    @Size(max = 3)
    private Integer cantidadDeTurnos;

    @Column(name = "controlDeSesionesPagas")
    @Size(max = 3)
    private Integer controlDeSesionesPagas;

    @Column(name = "observacionesDeTratamiento", length = 3000)
    private String observacionesDeTratamiento;

    @Column(name = "obraSocial", length = 100)
    private String obraSocial;

    @Column(name = "fechaTratamiento")
    private LocalDateTime fechaTratamiento;

    @Column(name = "estampillado", length = 100)
    private String estampillado;

    @Column(name = "bonos", length = 100)
    private String bonos;

    @Column(name = "tratamientoConcluido")
    @Size(max = 3)
    private Integer tratamientoConcluido;

    //Constructores

    public TratamientoPaciente() {
    }

    public TratamientoPaciente(Long idTratamiento, String tratamiento, Integer dni, Integer cantidadDeTurnos, Integer controlDeSesionesPagas, String observacionesDeTratamiento, String obraSocial, LocalDateTime fechaTratamiento, String estampillado, String bonos) {
        this.idTratamiento = idTratamiento;
        this.dni = dni;
        this.tratamiento = tratamiento;
        this.cantidadDeTurnos = cantidadDeTurnos;
        this.controlDeSesionesPagas = controlDeSesionesPagas;
        this.observacionesDeTratamiento = observacionesDeTratamiento;
        this.obraSocial = obraSocial;
        this.fechaTratamiento = fechaTratamiento;
        this.estampillado = estampillado;
        this.bonos = bonos;
    }

    //Metodos

    @PrePersist
    protected void onCreate() {
        this.fechaTratamiento = LocalDateTime.now(); // Establecer la fecha actual antes de persistir
        this.controlDeSesionesPagas = 0;
        this.tratamientoConcluido = 0;
    }

    //Getters and Setters

    public Long getIdTratamiento() {
        return idTratamiento;
    }

    public void setIdTratamiento(Long idTratamiento) {
        this.idTratamiento = idTratamiento;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public Integer getCantidadDeTurnos() {
        return cantidadDeTurnos;
    }

    public void setCantidadDeTurnos(Integer cantidadDeTurnos) {
        this.cantidadDeTurnos = cantidadDeTurnos;
    }

    public Integer getControlDeSesionesPagas() {
        return controlDeSesionesPagas;
    }

    public void setControlDeSesionesPagas(Integer controlDeSesionesPagas) {
        this.controlDeSesionesPagas = controlDeSesionesPagas;
    }

    public String getObservacionesDeTratamiento() {
        return observacionesDeTratamiento;
    }

    public void setObservacionesDeTratamiento(String observacionesDeTratamiento) {
        this.observacionesDeTratamiento = observacionesDeTratamiento;
    }

    public String getObraSocial() {
        return obraSocial;
    }

    public void setObraSocial(String obraSocial) {
        this.obraSocial = obraSocial;
    }

    public LocalDateTime getFechaTratamiento() {
        return fechaTratamiento;
    }

    public void setFechaTratamiento(LocalDateTime fechaTratamiento) {
        this.fechaTratamiento = fechaTratamiento;
    }

    public String getEstampillado() {
        return estampillado;
    }

    public void setEstampillado(String estampillado) {
        this.estampillado = estampillado;
    }

    public String getBonos() {
        return bonos;
    }

    public void setBonos(String bonos) {
        this.bonos = bonos;
    }

    public Integer getTratamientoConcluido() {
        return tratamientoConcluido;
    }

    public void setTratamientoConcluido(Integer tratamientoConcluido) {
        this.tratamientoConcluido = tratamientoConcluido;
    }
}
