package com.sgp.sistemaDeGestionDePacientes.controladores;

import com.sgp.sistemaDeGestionDePacientes.modelos.GestionDePagos;
import com.sgp.sistemaDeGestionDePacientes.modelos.Paciente;
import com.sgp.sistemaDeGestionDePacientes.modelos.TratamientoPaciente;
import com.sgp.sistemaDeGestionDePacientes.repositorios.RepositorioGestionDePagos;
import com.sgp.sistemaDeGestionDePacientes.repositorios.RepositorioPaciente;
import com.sgp.sistemaDeGestionDePacientes.repositorios.RepositorioTratamientoPaciente;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
public class ControladorPagosFisioCir {

    @Autowired
    private RepositorioPaciente repositorioPaciente;

    @Autowired
    private RepositorioTratamientoPaciente repositorioTratamientoPaciente;

    @Autowired
    private RepositorioGestionDePagos repositorioGestionDePagos;

    //Control de pagos de paciente
    @GetMapping("/registroDePagos")
    public String registroDePago(@RequestParam Integer dni, Model model){
        GestionDePagos gestionDePagos = new GestionDePagos();
        gestionDePagos.setDni(dni);
        Long idPaciente = repositorioPaciente.findPacienteByDni(dni).getIdPaciente();
        List<TratamientoPaciente> tratamientoPacientes = repositorioTratamientoPaciente.findByDni(dni);
        model.addAttribute("gestionDePagos", gestionDePagos);
        model.addAttribute("tratamientoPacientes", tratamientoPacientes);
        model.addAttribute("dni", dni);
        model.addAttribute("idPaciente", idPaciente);
        return "registrarPago";
    }

    @PostMapping("/registroDePagos")
    public void postRegistroDePago(Model model, GestionDePagos gestionDePagos, HttpServletResponse response) throws IOException {
        System.out.println("Fecha de pago: " + gestionDePagos.getFechaPago());
        TratamientoPaciente actualizarSesiones = repositorioTratamientoPaciente.buscarPorId(gestionDePagos.getTratamiento());
        if ((actualizarSesiones.getControlDeSesionesPagas() - (actualizarSesiones.getControlDeSesionesPagas() - gestionDePagos.getCantidadSesionesPagadas())) < 0 ){
            System.out.println("La cantidad por la que se quiere actualizar no se puede hacer");
        }else{
            actualizarSesiones.setControlDeSesionesPagas(actualizarSesiones.getControlDeSesionesPagas() + gestionDePagos.getCantidadSesionesPagadas());
        }
        repositorioTratamientoPaciente.save(actualizarSesiones);
        repositorioGestionDePagos.save(gestionDePagos);

        Long idPaciente = repositorioPaciente.findPacienteByDni(gestionDePagos.getDni()).getIdPaciente();
        response.sendRedirect("/detalleDePaciente?idPaciente=" + idPaciente);
    }

    //Para actualizar informacion del tratamiento
    @GetMapping("/editarPagos")
    public String editarPagos(@RequestParam Long idGestionDePagos, GestionDePagos gestionDePagos,TratamientoPaciente tratamientoPaciente, Model model){
        gestionDePagos = repositorioGestionDePagos.buscarPorId(idGestionDePagos);
        List<TratamientoPaciente> tratamientoPacientes = repositorioTratamientoPaciente.findByDni(gestionDePagos.getDni());
        Long idPaciente = repositorioPaciente.findPacienteByDni(gestionDePagos.getDni()).getIdPaciente();
        model.addAttribute("tratamientoPacientes", tratamientoPacientes);
        model.addAttribute("gestionDePagos", gestionDePagos);
        model.addAttribute("idPaciente", idPaciente);
        return "editarPago";
    }

    @PostMapping("/editarPagos")
    public void postEditarPagos(GestionDePagos gestionDePagos, Model model, HttpServletResponse response) throws IOException{
        if (gestionDePagos.getObservaciones().isEmpty()){
            gestionDePagos.setObservaciones(" ");
        }

        TratamientoPaciente actualizar = repositorioTratamientoPaciente.buscarPorId(gestionDePagos.getTratamiento());
        if ((repositorioGestionDePagos.buscarPorId(gestionDePagos.getIdGestionDePagos()).getCantidadSesionesPagadas()) > gestionDePagos.getCantidadSesionesPagadas()){
            int diferenciaNeg = repositorioGestionDePagos.buscarPorId(gestionDePagos.getIdGestionDePagos()).getCantidadSesionesPagadas() - gestionDePagos.getCantidadSesionesPagadas();
            actualizar.setControlDeSesionesPagas(actualizar.getControlDeSesionesPagas() - diferenciaNeg);
        } else if ((repositorioGestionDePagos.buscarPorId(gestionDePagos.getIdGestionDePagos()).getCantidadSesionesPagadas()) < gestionDePagos.getCantidadSesionesPagadas()) {
            int diferenciaPos = gestionDePagos.getCantidadSesionesPagadas() - repositorioGestionDePagos.buscarPorId(gestionDePagos.getIdGestionDePagos()).getCantidadSesionesPagadas();
            actualizar.setControlDeSesionesPagas(actualizar.getControlDeSesionesPagas() + diferenciaPos);
        }
        repositorioTratamientoPaciente.save(actualizar);
        repositorioGestionDePagos.save(gestionDePagos);
        Long idPaciente = repositorioPaciente.findPacienteByDni(gestionDePagos.getDni()).getIdPaciente();
        response.sendRedirect("/detalleDePaciente?idPaciente=" + idPaciente);
    }

    //Borrado de Pagos
    @GetMapping("/borrarPago")
    public void borrarPago(@RequestParam Long idGestionDePagos, HttpServletResponse response) throws IOException{
        Paciente pacienteTratamiento = repositorioPaciente.findPacienteByDni(repositorioGestionDePagos.buscarPorId(idGestionDePagos).getDni());
        GestionDePagos pagoAEliminar = repositorioGestionDePagos.buscarPorId(idGestionDePagos);
        TratamientoPaciente actualizarSesiones = repositorioTratamientoPaciente.buscarPorId(pagoAEliminar.getTratamiento());
        actualizarSesiones.setControlDeSesionesPagas(actualizarSesiones.getControlDeSesionesPagas()-pagoAEliminar.getCantidadSesionesPagadas());
        repositorioTratamientoPaciente.save(actualizarSesiones);
        repositorioGestionDePagos.deleteById(idGestionDePagos);
        response.sendRedirect("/detalleDePaciente?idPaciente=" + pacienteTratamiento.getIdPaciente());
    }

}
