package com.sgp.sistemaDeGestionDePacientes.modelos;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "bd_paciente")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPaciente")
    private Long idPaciente;

    @Column(name = "dni")
    @Size(max = 10)
    private Integer dni;

    @Column(name = "nombreApellido", length = 50)
    private String nombreApellido;

    @Column(name = "telefono", length = 15)
    private String telefono;

    @Column(name = "historiaClinica", length = 5000)
    private String historiaClinica;

    //1 dado de baja
    //0 no dado de baja
    @Column(name = "bajaLogica")
    private Integer bajaLogica;

    //Seteo del estado del paciente
    @PrePersist
    public void prePersist() {
        this.bajaLogica = 0;
    }

    //Constructor
    public Paciente() {
    }

    public Paciente(Long idPaciente, String nombreApellido, Integer dni, String telefono, String historiaClinica, Integer bajaLogica) {
        this.idPaciente = idPaciente;
        this.nombreApellido = nombreApellido;
        this.dni = dni;
        this.telefono = telefono;
        this.historiaClinica = historiaClinica;
        this.bajaLogica = bajaLogica;
    }

    //Getters and Setters

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getNombreApellido() {
        return nombreApellido;
    }

    public void setNombreApellido(String nombreApellido) {
        this.nombreApellido = nombreApellido;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getHistoriaClinica() {
        return historiaClinica;
    }

    public void setHistoriaClinica(String historiaClinica) {
        this.historiaClinica = historiaClinica;
    }

    public Integer getBajaLogica() {
        return bajaLogica;
    }

    public void setBajaLogica(Integer bajaLogica) {
        this.bajaLogica = bajaLogica;
    }
}
