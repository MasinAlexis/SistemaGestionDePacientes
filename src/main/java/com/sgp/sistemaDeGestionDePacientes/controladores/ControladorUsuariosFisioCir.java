package com.sgp.sistemaDeGestionDePacientes.controladores;

import com.sgp.sistemaDeGestionDePacientes.acciones.ProcesarPassword;
import com.sgp.sistemaDeGestionDePacientes.modelos.Usuario;
import com.sgp.sistemaDeGestionDePacientes.repositorios.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class ControladorUsuariosFisioCir {

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @GetMapping("/usuarioActual")
    public String obtenerUsuario(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Usuario usuario = repositorioUsuario.findByUsername(username);
        List<Usuario> usuariosDisponibles = repositorioUsuario.todosExceptoActual(username);

        model.addAttribute("username", username);
        model.addAttribute("usuariosDisponibles", usuariosDisponibles);
        model.addAttribute("usuario", usuario);
        return "usuarioActual";
    }

    @GetMapping("/editarUsuario")
    public String editarUsuario(@RequestParam String username, Model model){
        Usuario usuarioEdit = repositorioUsuario.findByUsername(username);
        usuarioEdit.setPassword("");
        model.addAttribute("usuarioEdit", usuarioEdit);
        return "usuarioEdit";
    }

    @PostMapping("/editarUsuario")
    public void postEditarUsuario(Usuario usuarioEdit, HttpServletResponse response) throws IOException {
        if (usuarioEdit.getPassword() == "" ){
            Usuario usuarioPass = repositorioUsuario.findByUsername(usuarioEdit.getUsername());
            usuarioEdit.setPassword(usuarioPass.getPassword());
        }else{
            String passEncript = ProcesarPassword.encriptarPassword(usuarioEdit.getPassword());
            usuarioEdit.setPassword(passEncript);
        }
        repositorioUsuario.save(usuarioEdit);
        response.sendRedirect("/usuarioActual");
    }

    @GetMapping("/registrarUsuario")
    public String registrarUsuario(Usuario usuarioNuevo){
        return "registrarUsuario";
    }

    @PostMapping("/registrarUsuario")
    public String postRegistrarUsuario(@ModelAttribute Usuario usuarioNuevo, Model model){
        if (repositorioUsuario.findByUsername(usuarioNuevo.getUsername()) != null){
            model.addAttribute("existe", "El nombre de usuario ya existe en la base de datos.");
            return "/registrarUsuario";
        } else {
            String claveEncriptada = ProcesarPassword.encriptarPassword(usuarioNuevo.getPassword());
            usuarioNuevo.setPassword(claveEncriptada);
            repositorioUsuario.save(usuarioNuevo);
            return "redirect:/usuarioActual";
        }
    }
}
