package com.sgp.sistemaDeGestionDePacientes.repositorios;

import com.sgp.sistemaDeGestionDePacientes.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioUsuario extends JpaRepository<Usuario, Long> {

    //En automatico spring busca el usuario que se le pase en base de datos
    Usuario findByUsername(String username);

    //Todos los usuarios menos el actual
    @Query("FROM Usuario u WHERE u.username != :username")
    List<Usuario> todosExceptoActual(String username);
}
