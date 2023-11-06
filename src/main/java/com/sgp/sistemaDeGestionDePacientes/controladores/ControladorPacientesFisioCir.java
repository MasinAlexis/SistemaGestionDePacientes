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
public class ControladorPacientesFisioCir {

    @Autowired
    private RepositorioPaciente repositorioPaciente;

    @Autowired
    private RepositorioGestionDePagos repositorioGestionDePagos;

    @Autowired
    private RepositorioTratamientoPaciente repositorioTratamientoPaciente;

    //Para registrar un paciente
    @GetMapping("/registroPaciente")
    public String preGuardarPaciente(Paciente paciente){
        return "registrarPaciente";
    }

    @PostMapping("/registroPaciente")
    public void postGuardarPaciente(@RequestParam(value = "ventana") String ventana, Paciente paciente, HttpServletResponse response) throws IOException {
        Paciente verificarExistente = repositorioPaciente.findPacienteByDni(paciente.getDni());
        if (verificarExistente != null){
            Boolean existente = true;
            if (verificarExistente.getBajaLogica() == 0){
                response.sendRedirect("/listadoDePacientes?existente=" + existente);
            } else {
                response.sendRedirect("/listadoDePacientesInactivos?existente=" + existente);
            }
        } else {
            if (paciente.getHistoriaClinica().isEmpty()){
                paciente.setHistoriaClinica(" ");
            }
            repositorioPaciente.save(paciente);
            if (ventana.equals("No")){
                response.sendRedirect("/listadoDePacientes");
            }else {
                response.sendRedirect("/detalleDePaciente?idPaciente=" + repositorioPaciente.findPacienteByDni(paciente.getDni()).getIdPaciente());
            }
        }
    }

    //Restaurar paciente
    @GetMapping("/restaurarPaciente")
    public String restaurarPaciente(@RequestParam Long idPaciente,Paciente paciente) {
        Paciente pacienteBaja = repositorioPaciente.getById(idPaciente);
        pacienteBaja.setBajaLogica(0);
        repositorioPaciente.save(pacienteBaja);
        return "redirect:/listadoDePacientesInactivos";
    }

    //Para mostrar la tabla de pacientes cargados
    @GetMapping("/listadoDePacientes")
    public String listadoPacientes(@RequestParam(value = "existente", required = false) Boolean existente, Model model){
        List<Paciente> pacientes = repositorioPaciente.buscarPacientesActivos();
        model.addAttribute("pacientes", pacientes);
        if (existente != null){
            model.addAttribute("existenteAlta", existente);
        }
        return "tablaDePacientes";
    }

    //Para mostrar la tabla de pacientes cargados
    @GetMapping("/listadoDePacientesInactivos")
    public String listadoPacientesDadosDeBaja(@RequestParam(value = "existente", required = false) Boolean existente, Model model){
        List<Paciente> pacientes = repositorioPaciente.buscarPacientesInactivos();
        model.addAttribute("pacientes", pacientes);
        if (existente != null){
            model.addAttribute("existenteEnBaja", existente);
        }
        return "tablaDePacientesInactivos";
    }

    //Control de detalles de paciente
    @GetMapping("/detalleDePaciente")
    public String verDetalleDePaciente(@RequestParam Long idPaciente, Model model, TratamientoPaciente tratamientoPaciente){
        Paciente paciente = repositorioPaciente.getById(idPaciente);
        List<TratamientoPaciente> tratamientos = repositorioTratamientoPaciente.findByDni(paciente.getDni());
        List<GestionDePagos> pagos = repositorioGestionDePagos.buscarPagosAbiertos(paciente.getDni());
        model.addAttribute("tratamientos", tratamientos);
        model.addAttribute("paciente", paciente);
        model.addAttribute("pagos", pagos);
        return "detallePaciente";
    }

    //Para mostrar datos del paciente
    @GetMapping("/perfilPaciente")
    public String paciente(){
        return "perfilPaciente";
    }

    //Para actualizar informacion del paciente
    @GetMapping("/editarPaciente")
    public String editarPaciente(@RequestParam Integer dni, Paciente paciente, Model model){
        paciente = repositorioPaciente.findPacienteByDni(dni);
        model.addAttribute("paciente", paciente);
        return "editarPaciente";
    }

    @PostMapping("/editarPaciente")
    public void postEditarPaciente(Paciente paciente, Model model, HttpServletResponse response) throws IOException{
        if (paciente.getHistoriaClinica().isEmpty()){
            paciente.setHistoriaClinica(" ");
        }
        repositorioPaciente.save(paciente);
        response.sendRedirect("/detalleDePaciente?idPaciente=" + paciente.getIdPaciente());
    }

    //Baja fisica de paciente
    @GetMapping("/eliminarDefinitivamente")
    public String eliminarFisico(@RequestParam Long idPaciente,Paciente paciente) {
        Paciente pacienteBorrado = repositorioPaciente.getById(idPaciente);
        repositorioGestionDePagos.deleteAll(repositorioGestionDePagos.buscarPagosAbiertos(pacienteBorrado.getDni()));
        repositorioTratamientoPaciente.deleteAll(repositorioTratamientoPaciente.findByDni(pacienteBorrado.getDni()));
        repositorioPaciente.delete(paciente);
        return "redirect:/listadoDePacientesInactivos";
    }

    //Baja logica de paciente
    @GetMapping("/eliminar")
    public String eliminarLogico(@RequestParam Long idPaciente,Paciente paciente) {
        Paciente pacienteBaja = repositorioPaciente.getById(idPaciente);
        pacienteBaja.setBajaLogica(1);
        repositorioPaciente.save(pacienteBaja);
        return "redirect:/listadoDePacientes";
    }
}
