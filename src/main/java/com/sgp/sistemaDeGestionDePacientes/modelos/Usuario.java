package com.sgp.sistemaDeGestionDePacientes.modelos;

import javax.persistence.*;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private Long idUsuario;

    @Column(name = "username", length = 20)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "emailUsuario", length = 30)
    private String emailUsuario;

    @Column(name = "rolUsuario", length = 10)
    private String rolUsuario;

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getRolUsuario() {
        return rolUsuario;
    }

    public void setRolUsuario(String rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    @PrePersist
    public void seteoDeRol(){
        this.rolUsuario = "ROLE_ADMIN";
    }

    public Usuario(){}

    public Usuario(Long idUsuario, String username, String password, String emailUsuario, String rolUsuario) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.password = password;
        this.emailUsuario = emailUsuario;
        this.rolUsuario = rolUsuario;
    }
}
