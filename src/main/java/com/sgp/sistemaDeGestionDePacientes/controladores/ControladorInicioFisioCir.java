package com.sgp.sistemaDeGestionDePacientes.controladores;

import com.sgp.sistemaDeGestionDePacientes.acciones.ObtenerPrecioSesion;
import com.sgp.sistemaDeGestionDePacientes.modelos.GestionDePagos;
import com.sgp.sistemaDeGestionDePacientes.modelos.PrecioTurno;
import com.sgp.sistemaDeGestionDePacientes.modelos.ResumenSemanal;
import com.sgp.sistemaDeGestionDePacientes.repositorios.RepositorioGestionDePagos;
import com.sgp.sistemaDeGestionDePacientes.repositorios.RepositorioPrecioTurno;

import com.sgp.sistemaDeGestionDePacientes.repositorios.RepositorioResumenSemanal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
public class ControladorInicioFisioCir {

    @Autowired
    private RepositorioPrecioTurno repositorioPrecioTurno;

    @Autowired
    private RepositorioGestionDePagos repositorioGestionDePagos;

    @Autowired
    private RepositorioResumenSemanal repositorioResumenSemanal;

    @Autowired
    private ObtenerPrecioSesion precioSesion;

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

        /*
        //Para Probar, con esto fijamos la fecha que queramos
        LocalDate fecha = LocalDate.of(2023, 10, 31); // Cambia la fecha a la que desees
        LocalTime hora = LocalTime.of(14, 30); // Cambia la hora a la que desees
        LocalDateTime fechaActual = LocalDateTime.of(fecha, hora);
        System.out.println("Fecha fijada manualmente: " + fechaActual);
        */

        LocalDateTime fechaActual = LocalDateTime.now();
        LocalDateTime startOfWeek = fechaActual.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).with(LocalTime.MIN);
        LocalDateTime endOfWeek = fechaActual.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).with(LocalTime.MAX);
        Double importeAcumulado = 0.0;

        List<GestionDePagos> pagosDelDia = repositorioGestionDePagos.findAllByFechaPagoBetween(startOfWeek, endOfWeek);
        for (GestionDePagos pagos: pagosDelDia) {
            importeAcumulado = importeAcumulado + pagos.getImportePagado();
        }
        model.addAttribute("pagosDelDia", pagosDelDia);
        model.addAttribute("importeTotal", importeAcumulado);
        if (exito != null){
            model.addAttribute("exito", exito);
        }

        return "index";
    }

    @GetMapping("/consultaDeBalances")
    public String consultaDeBalances(Model model) throws IOException {
        List<ResumenSemanal> cierresSemanales= repositorioResumenSemanal.buscarTodosPorFechaDesc();
        model.addAttribute("balancesSemanales", cierresSemanales);
        return "cierresSemanales";
    }

    //Control de precios de sesion
    @GetMapping("/precioDeSesion")
    public String obtenerPrecio(Model model, PrecioTurno precioTurno){
        model.addAttribute("precioDeTurno", precioSesion.obtenerPrecioSesion().getPrecioSesion());
        model.addAttribute("fechaImplementada", precioSesion.obtenerPrecioSesion().getFechaDeImplementacion());
        return "configurarPrecioTurno";
    }

    @PostMapping("/precioDeSesion")
    public void guardarPrecio(PrecioTurno precioTurno, HttpServletResponse response) throws IOException {
        precioTurno.setFechaDeImplementacion(new Date());
        repositorioPrecioTurno.save(precioTurno);
        response.sendRedirect("/precioDeSesion");
    }

    @PostMapping("/registrarBalanceSemanal")
    public void registrarBalanceSemanal(Model model, HttpServletResponse response) throws IOException {

        /*
        //Para Probar, con esto fijamos la fecha que queramos
        LocalDate fecha = LocalDate.of(2023, 10, 31); // Cambia la fecha a la que desees
        LocalTime hora = LocalTime.of(14, 30); // Cambia la hora a la que desees
        LocalDateTime fechaActual = LocalDateTime.of(fecha, hora);
        System.out.println("Fecha fijada manualmente: " + fechaActual);
        */
        //Parametros para funcion
        LocalDateTime fechaActual = LocalDateTime.now();
        LocalDateTime startOfWeek = fechaActual.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).with(LocalTime.MIN);
        LocalDateTime endOfWeek = fechaActual.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).with(LocalTime.MAX);

        //Datos para guardar en BD
        Date inicioDeSemana = Date.from(startOfWeek.atZone(ZoneId.systemDefault()).toInstant());
        Date finDeSemana = Date.from(endOfWeek.atZone(ZoneId.systemDefault()).toInstant());
        Double importeAcumulado = 0.0;
        List<GestionDePagos> pagosDelaSemana = repositorioGestionDePagos.findAllByFechaPagoBetween(startOfWeek, endOfWeek);

        for (GestionDePagos pagos: pagosDelaSemana) {
            importeAcumulado = importeAcumulado + pagos.getImportePagado();
        }

        ResumenSemanal verifPeriodo = repositorioResumenSemanal.buscarPeriodoSemanal(inicioDeSemana,finDeSemana);
        ResumenSemanal cierreGuardado = new ResumenSemanal();
        if (verifPeriodo == null){
            ResumenSemanal resumenSemanal = new ResumenSemanal();
            resumenSemanal.setDiaSemanaInicio(inicioDeSemana);
            resumenSemanal.setDiaSemanaFin(finDeSemana);
            resumenSemanal.setTotalSemanal(importeAcumulado);
            cierreGuardado = repositorioResumenSemanal.save(resumenSemanal);
        } else {
            verifPeriodo.setTotalSemanal(importeAcumulado);
            cierreGuardado = repositorioResumenSemanal.save(verifPeriodo);
        }

        Boolean exito = true;
        if (cierreGuardado == null){
            exito = false;
        }
        response.sendRedirect("/inicio?exito=" + exito);
    }
}
