package com.esfe.sistemagimnasio.controllers;

import com.esfe.sistemagimnasio.services.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.esfe.sistemagimnasio.models.Cliente;
import com.esfe.sistemagimnasio.models.MembresiaCliente;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IEntrenadorService entrenadorService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IRutinaService rutinaService;

    @Autowired
    private IEjercicioService ejercicioService;

    @Autowired
    private IGrupoMuscularService grupoMuscularService;

    @Autowired
    private IRutinaEjercicioService rutinaEjercicioService;

    @Autowired
    private IAsignacionEntrenadorService asignacionEntrenadorService;

    @Autowired
    private IMembresiaClienteService membresiaClienteService;

    @Autowired
    private ITipoMembresiaService tipoMembresiaService;

    @Autowired
    private IEvaluacionFisicaService evaluacionFisicaService;

    @Autowired
    private IAsistenciaService asistenciaService;


    /*
     * Entrada principal de la aplicación.
     */
    @GetMapping("/")
    public String root() {
        return "redirect:/home";
    }

    @GetMapping("/acceso-denegado")
    public String accesoDenegado() {
        return "error/403";
    }


    /*
     * Dashboard principal.
     */
    @GetMapping("/home")
    public String home(
            Model model,
            Authentication authentication) {

        Pageable contador =
                PageRequest.of(0, 1);

        boolean esAdmin =
                authentication
                        .getAuthorities()
                        .stream()
                        .anyMatch(authority ->
                                authority
                                        .getAuthority()
                                        .equals("ADMIN")
                        );


        // =====================================================
        // ADMIN
        // =====================================================

        if (esAdmin) {

            model.addAttribute(
                    "totalClientes",
                    clienteService
                            .obtenerTodosPaginados(contador)
                            .getTotalElements()
            );

            model.addAttribute(
                    "totalEntrenadores",
                    entrenadorService
                            .obtenerTodosPaginados(contador)
                            .getTotalElements()
            );

            model.addAttribute(
                    "totalUsuarios",
                    usuarioService
                            .obtenerTodosPaginados(contador)
                            .getTotalElements()
            );

            model.addAttribute(
                    "totalRutinas",
                    rutinaService
                            .obtenerTodosPaginados(contador)
                            .getTotalElements()
            );

            model.addAttribute(
                    "totalEjercicios",
                    ejercicioService
                            .obtenerTodosPaginados(contador)
                            .getTotalElements()
            );

            model.addAttribute(
                    "totalGruposMusculares",
                    grupoMuscularService
                            .obtenerTodosPaginados(contador)
                            .getTotalElements()
            );

            model.addAttribute(
                    "totalRutinaEjercicios",
                    rutinaEjercicioService
                            .obtenerTodosPaginados(contador)
                            .getTotalElements()
            );

            model.addAttribute(
                    "totalAsignaciones",
                    asignacionEntrenadorService
                            .obtenerTodosPaginados(contador)
                            .getTotalElements()
            );

            model.addAttribute(
                    "totalMembresias",
                    membresiaClienteService
                            .obtenerTodosPaginados(contador)
                            .getTotalElements()
            );

            model.addAttribute(
                    "totalTiposMembresia",
                    tipoMembresiaService
                            .obtenerTodosPaginados(contador)
                            .getTotalElements()
            );

            model.addAttribute(
                    "totalEvaluaciones",
                    evaluacionFisicaService
                            .obtenerTodosPaginados(contador)
                            .getTotalElements()
            );
        }


        // =====================================================
        // ENTRENADOR
        // =====================================================

        else if (authentication
                .getAuthorities()
                .stream()
                .anyMatch(authority ->
                        authority
                                .getAuthority()
                                .equals("ENTRENADOR")
                )) {

            String email =
                    authentication.getName();


            // SOLO SUS RUTINAS

            model.addAttribute(
                    "totalRutinas",
                    rutinaService
                            .obtenerPorEntrenadorEmail(
                                    email,
                                    contador
                            )
                            .getTotalElements()
            );


            // SOLO SUS EVALUACIONES

            model.addAttribute(
                    "totalEvaluaciones",
                    evaluacionFisicaService
                            .obtenerPorEntrenadorEmail(
                                    email,
                                    contador
                            )
                            .getTotalElements()
            );


            /*
             * Estos son catálogos generales,
             * así que no necesitan filtrarse.
             */

            model.addAttribute(
                    "totalEjercicios",
                    ejercicioService
                            .obtenerTodosPaginados(contador)
                            .getTotalElements()
            );

            model.addAttribute(
                    "totalGruposMusculares",
                    grupoMuscularService
                            .obtenerTodosPaginados(contador)
                            .getTotalElements()
            );
        }

        // =====================================================
// CLIENTE
// =====================================================

        else if (authentication
                .getAuthorities()
                .stream()
                .anyMatch(authority ->
                        authority
                                .getAuthority()
                                .equals("CLIENTE")
                )) {

            String email =
                    authentication.getName();


            // ==========================================
            // PERFIL DEL CLIENTE AUTENTICADO
            // ==========================================

            Cliente cliente =
                    clienteService
                            .obtenerPorUsuarioEmail(email)
                            .orElseThrow();


            model.addAttribute(
                    "clienteActual",
                    cliente
            );


            // ==========================================
            // MEMBRESÍA VIGENTE
            // ==========================================

            Optional<MembresiaCliente> membresia =
                    membresiaClienteService
                            .obtenerMembresiaVigenteCliente(
                                    cliente.getId()
                            );


            model.addAttribute(
                    "membresiaActual",
                    membresia.orElse(null)
            );


            model.addAttribute(
                    "tieneMembresia",
                    membresia.isPresent()
            );


            if (membresia.isPresent()) {

                long diasRestantes =
                        ChronoUnit.DAYS.between(
                                LocalDate.now(),
                                membresia
                                        .get()
                                        .getFechaVencimiento()
                        );

                model.addAttribute(
                        "diasRestantes",
                        Math.max(diasRestantes, 0)
                );
            }


            // ==========================================
            // ASISTENCIAS DEL CLIENTE
            // ==========================================

            model.addAttribute(
                    "ultimasAsistencias",
                    asistenciaService
                            .obtenerUltimasPorCliente(
                                    cliente.getId()
                            )
            );


            model.addAttribute(
                    "totalAsistenciasCliente",
                    asistenciaService
                            .contarPorCliente(
                                    cliente.getId()
                            )
            );
        }


        return "home/index";
    }
}