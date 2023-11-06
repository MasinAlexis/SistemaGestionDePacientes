package com.sgp.sistemaDeGestionDePacientes.servicio;

import com.sgp.sistemaDeGestionDePacientes.modelos.Usuario;
import com.sgp.sistemaDeGestionDePacientes.repositorios.RepositorioUsuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service("userDetailsService")
@Slf4j
public class UsuarioService implements UserDetailsService {

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = repositorioUsuario.findByUsername(username);
        if (usuario == null){
            throw new UsernameNotFoundException(username);
        }
        var role = new ArrayList<GrantedAuthority>();
        role.add(new SimpleGrantedAuthority(usuario.getRolUsuario()));

        return new User(usuario.getUsername(), usuario.getPassword(), role);
    }
}
