package com.sgp.sistemaDeGestionDePacientes.controladores;

import com.sgp.sistemaDeGestionDePacientes.modelos.GestionDePagos;
import com.sgp.sistemaDeGestionDePacientes.modelos.Paciente;
import com.sgp.sistemaDeGestionDePacientes.modelos.TratamientoPaciente;
import com.sgp.sistemaDeGestionDePacientes.repositorios.RepositorioGestionDePagos;
import com.sgp.sistemaDeGestionDePacientes.repositorios.RepositorioPaciente;
import com.sgp.sistemaDeGestionDePacientes.repositorios.RepositorioTratamientoPaciente;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
public class ControladorTratamientosFisioCir {

    @Autowired
    private RepositorioPaciente repositorioPaciente;

    @Autowired
    private RepositorioTratamientoPaciente repositorioTratamientoPaciente;

    @Autowired
    private RepositorioGestionDePagos repositorioGestionDePagos;

    //Registro de tratamiento para paciente
    @GetMapping("/registroDeTratamiento")
    public String registroDeTratamiento(@RequestParam Integer dni, Model model){
        TratamientoPaciente tratamientoPaciente = new TratamientoPaciente();
        tratamientoPaciente.setDni(dni);
        Long idPaciente = repositorioPaciente.findPacienteByDni(dni).getIdPaciente();
        model.addAttribute("tratamientoPaciente", tratamientoPaciente);
        model.addAttribute("dni", dni);
        model.addAttribute("idPaciente", idPaciente);
        return "registrarTratamiento";
    }

    @PostMapping("/registroDeTratamiento")
    public void postRegistroDeTratamiento(Model model, TratamientoPaciente tratamientoPaciente, HttpServletResponse response) throws IOException{
        if (tratamientoPaciente.getObservacionesDeTratamiento().isEmpty()){
            tratamientoPaciente.setObservacionesDeTratamiento(" ");
        }
        repositorioTratamientoPaciente.save(tratamientoPaciente);
        Long idPaciente = repositorioPaciente.findPacienteByDni(tratamientoPaciente.getDni()).getIdPaciente();
        response.sendRedirect("/detalleDePaciente?idPaciente=" + idPaciente);
    }

    //Para actualizar informacion del tratamiento
    @GetMapping("/editarTratamiento")
    public String editarTratamiento(@RequestParam Long idTratamiento, Model model){
        TratamientoPaciente tratamientoPaciente = repositorioTratamientoPaciente.buscarPorId(idTratamiento);
        Long idPaciente = repositorioPaciente.findPacienteByDni(tratamientoPaciente.getDni()).getIdPaciente();
        model.addAttribute("tratamiento", tratamientoPaciente);
        model.addAttribute("idPaciente", idPaciente);
        return "editarTratamiento";
    }

    @PostMapping("/editarTratamiento")
    public void postEditarTratamiento(TratamientoPaciente tratamiento, Model model, HttpServletResponse response) throws IOException{
        repositorioTratamientoPaciente.save(tratamiento);
        Long idPaciente = repositorioPaciente.findPacienteByDni(tratamiento.getDni()).getIdPaciente();
        response.sendRedirect("/detalleDePaciente?idPaciente=" + idPaciente);
    }

    //Borrado de Tratamientos
    @GetMapping("/borrarTratamiento")
    public void borrarTratamiento(@RequestParam Long idTratamiento, HttpServletResponse response) throws IOException {
        Paciente pacienteTratamiento = repositorioPaciente.findPacienteByDni(repositorioTratamientoPaciente.buscarPorId(idTratamiento).getDni());
        repositorioTratamientoPaciente.deleteById(idTratamiento);
        repositorioGestionDePagos.deleteAll(repositorioGestionDePagos.buscarPorTratamiento(idTratamiento));
        response.sendRedirect("/detalleDePaciente?idPaciente=" + pacienteTratamiento.getIdPaciente());
    }

    //Control de tratamientos cerrados del paciente
    @GetMapping("/tratamientosConcluidos")
    public String verTratamientosCerrados(@RequestParam Long idPaciente, Model model, TratamientoPaciente tratamientoPaciente){
        Paciente paciente = repositorioPaciente.getById(idPaciente);
        List<TratamientoPaciente> tratamientos = repositorioTratamientoPaciente.tratamientosCerrados(paciente.getDni());
        List<GestionDePagos> pagos = repositorioGestionDePagos.buscarPagosCerrados(paciente.getDni());
        model.addAttribute("tratamientos", tratamientos);
        model.addAttribute("paciente", paciente);
        model.addAttribute("pagos", pagos);
        return "tratamientosConcluidos";
    }

    @GetMapping("/restaurarTratamiento")
    public void restaurarTratamientosCerrados(@RequestParam Long idTratamiento, Model model, TratamientoPaciente tratamientoPaciente, HttpServletResponse response) throws IOException{
        TratamientoPaciente tratamientoARestaurar = repositorioTratamientoPaciente.buscarPorId(idTratamiento);
        tratamientoARestaurar.setTratamientoConcluido(0);
        repositorioTratamientoPaciente.save(tratamientoARestaurar);
        Long idPaciente = repositorioPaciente.findPacienteByDni(tratamientoARestaurar.getDni()).getIdPaciente();
        response.sendRedirect("/detalleDePaciente?idPaciente=" + idPaciente);
    }

    @GetMapping("/cerrarTratamiento")
    public void cerrarTratamientos(@RequestParam Long idTratamiento, TratamientoPaciente tratamientoPaciente, HttpServletResponse response) throws IOException{
        TratamientoPaciente tratamientoACerrar = repositorioTratamientoPaciente.buscarPorId(idTratamiento);
        tratamientoACerrar.setTratamientoConcluido(1);
        repositorioTratamientoPaciente.save(tratamientoACerrar);
        Long idPaciente = repositorioPaciente.findPacienteByDni(tratamientoACerrar.getDni()).getIdPaciente();
        response.sendRedirect("/detalleDePaciente?idPaciente=" + idPaciente);
    }

}
