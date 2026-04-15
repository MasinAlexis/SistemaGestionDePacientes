package com.sgp.sistemaDeGestionDePacientes.controladores;

import com.sgp.sistemaDeGestionDePacientes.modelos.PrecioTurno;
import com.sgp.sistemaDeGestionDePacientes.modelos.ResumenSemanal;
import com.sgp.sistemaDeGestionDePacientes.servicios.ServiceInicioFisioCir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class ControladorInicioFisioCir {

    @Autowired
    private ServiceInicioFisioCir serviceInicioFisioCir;

    //Pantalla de inicio de sesion
    @GetMapping("/inicioSesion")
    public String login(@RequestParam(required = false) String error, ModelMap modelMap){
        if (error != null){
            modelMap.put("error", "Usuario o Clave Incorrecta");
        }
        return "loginFisioCir";
    }

    //Pantalla de inicio
    @GetMapping("/inicio")
    public String inicio(@RequestParam(value = "exito", required = false) Boolean exito, Model model){
        // Obtiene los pagos semanales usando el servicio
        ServiceInicioFisioCir.PagosSemanalesDTO pagosSemanales = serviceInicioFisioCir.obtenerPagosSemanales();
        
        model.addAttribute("pagosDelDia", pagosSemanales.getPagos());
        model.addAttribute("importeTotal", pagosSemanales.getImporteTotal());
        if (exito != null){
            model.addAttribute("exito", exito);
        }

        return "index";
    }

    @GetMapping("/consultaDeBalances")
    public String consultaDeBalances(Model model) throws IOException {
        // Obtiene todos los balances usando el servicio
        List<ResumenSemanal> cierresSemanales = serviceInicioFisioCir.obtenerTodosLosBalances();
        model.addAttribute("balancesSemanales", cierresSemanales);
        return "cierresSemanales";
    }

    //Control de precios de sesion
    @GetMapping("/precioDeSesion")
    public String obtenerPrecio(Model model, PrecioTurno precioTurno){
        // Obtiene el precio actual usando el servicio
        PrecioTurno precioActual = serviceInicioFisioCir.obtenerPrecioActual();
        model.addAttribute("precioDeTurno", precioActual.getPrecioSesion());
        model.addAttribute("fechaImplementada", precioActual.getFechaDeImplementacion());
        return "configurarPrecioTurno";
    }

    @PostMapping("/precioDeSesion")
    public void guardarPrecio(PrecioTurno precioTurno, HttpServletResponse response) throws IOException {
        // Guarda el precio usando el servicio
        serviceInicioFisioCir.guardarPrecioSesion(precioTurno);
        response.sendRedirect("/precioDeSesion");
    }

    @PostMapping("/registrarBalanceSemanal")
    public void registrarBalanceSemanal(Model model, HttpServletResponse response) throws IOException {
        // Registra el balance semanal usando el servicio
        Boolean exito = serviceInicioFisioCir.registrarBalanceSemanal();
        response.sendRedirect("/inicio?exito=" + exito);
    }
}