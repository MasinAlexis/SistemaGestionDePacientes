package com.sgp.sistemaDeGestionDePacientes.acciones;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ProcesarPassword {

    public static String encriptarPassword(String passwordSinEncriptar){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pass = encoder.encode(passwordSinEncriptar);
        return pass;
    }
}
