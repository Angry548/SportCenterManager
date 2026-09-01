package com.esfe.sistemagimnasio.controllers;

import com.esfe.sistemagimnasio.services.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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


    /*
     * Entrada principal de la aplicación.
     */
    @GetMapping("/")
    public String root() {
        return "redirect:/home";
    }


    /*
     * Dashboard principal.
     */
    @GetMapping("/home")
    public String home(Model model) {

        Pageable contador =
                PageRequest.of(0, 1);


        // ==========================================
        // GESTIÓN
        // ==========================================

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


        // ==========================================
        // ENTRENAMIENTO
        // ==========================================

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


        // ==========================================
        // MEMBRESÍAS
        // ==========================================

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


        // ==========================================
        // SEGUIMIENTO
        // ==========================================

        model.addAttribute(
                "totalEvaluaciones",
                evaluacionFisicaService
                        .obtenerTodosPaginados(contador)
                        .getTotalElements()
        );


        return "home/index";
    }
}