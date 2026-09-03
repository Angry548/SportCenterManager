package com.esfe.sistemagimnasio.controllers;

import com.esfe.sistemagimnasio.models.Entrenador;
import com.esfe.sistemagimnasio.models.EvaluacionFisica;
import com.esfe.sistemagimnasio.services.interfaces.IClienteService;
import com.esfe.sistemagimnasio.services.interfaces.IEntrenadorService;
import com.esfe.sistemagimnasio.services.interfaces.IEvaluacionFisicaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/evaluaciones-fisicas")
public class EvaluacionFisicaController {

    @Autowired
    private IEvaluacionFisicaService evaluacionFisicaService;

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IEntrenadorService entrenadorService;


    // =====================================================
    // LISTADO
    // =====================================================

    @GetMapping
    public String index(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            Authentication authentication) {

        int currentPage =
                page.orElse(1) - 1;

        int pageSize =
                size.orElse(5);

        Pageable pageable =
                PageRequest.of(
                        currentPage,
                        pageSize
                );


        Page<EvaluacionFisica> evaluaciones;


        // ADMIN VE TODAS
        if (esAdmin(authentication)) {

            evaluaciones =
                    evaluacionFisicaService
                            .obtenerTodosPaginados(
                                    pageable
                            );

        } else {

            // ENTRENADOR SOLO VE LAS SUYAS
            evaluaciones =
                    evaluacionFisicaService
                            .obtenerPorEntrenadorEmail(
                                    authentication.getName(),
                                    pageable
                            );
        }


        model.addAttribute(
                "evaluaciones",
                evaluaciones
        );


        int totalPages =
                evaluaciones.getTotalPages();


        if (totalPages > 0) {

            List<Integer> pageNumbers =
                    IntStream
                            .rangeClosed(
                                    1,
                                    totalPages
                            )
                            .boxed()
                            .collect(
                                    Collectors.toList()
                            );

            model.addAttribute(
                    "pageNumbers",
                    pageNumbers
            );
        }


        return "evaluacionFisica/index";
    }


    // =====================================================
    // CREAR
    // =====================================================

    @GetMapping("/create")
    public String create(
            EvaluacionFisica evaluacionFisica,
            Model model,
            Authentication authentication) {

        model.addAttribute(
                "clientes",
                clienteService.obtenerTodos()
        );

        /*
         * ADMIN:
         * todos los entrenadores.
         *
         * ENTRENADOR:
         * solamente su propio perfil.
         */
        model.addAttribute(
                "entrenadores",
                obtenerEntrenadoresPermitidos(
                        authentication
                )
        );


        return "evaluacionFisica/create";
    }


    // =====================================================
    // GUARDAR
    // =====================================================

    @PostMapping("/save")
    public String save(
            @Valid EvaluacionFisica evaluacionFisica,
            BindingResult result,
            Model model,
            RedirectAttributes attributes,
            Authentication authentication) {


        // ==========================================
        // FORZAR ENTRENADOR AUTENTICADO
        // ==========================================

        if (!esAdmin(authentication)) {

            Entrenador entrenadorActual =
                    obtenerEntrenadorActual(
                            authentication
                    );

            /*
             * Aunque modifique manualmente el HTML,
             * siempre se guarda con su propio
             * entrenador.
             */
            evaluacionFisica.setEntrenador(
                    entrenadorActual
            );
        }


        // ==========================================
        // VALIDACIONES
        // ==========================================

        if (result.hasErrors()) {

            model.addAttribute(
                    "clientes",
                    clienteService.obtenerTodos()
            );

            model.addAttribute(
                    "entrenadores",
                    obtenerEntrenadoresPermitidos(
                            authentication
                    )
            );

            model.addAttribute(
                    "error",
                    "No se pudo guardar debido a un error."
            );


            if (evaluacionFisica.getId() != null) {
                return "evaluacionFisica/edit";
            }

            return "evaluacionFisica/create";
        }


        /*
         * Si estamos editando, verificamos que
         * el entrenador realmente tenga permiso
         * sobre esa evaluación.
         */
        if (evaluacionFisica.getId() != null) {

            obtenerEvaluacionPermitida(
                    evaluacionFisica.getId(),
                    authentication
            );
        }


        evaluacionFisicaService.guardar(
                evaluacionFisica
        );


        attributes.addFlashAttribute(
                "msg",
                "Evaluación física guardada correctamente"
        );


        return "redirect:/evaluaciones-fisicas";
    }


    // =====================================================
    // DETALLES
    // =====================================================

    @GetMapping("/details/{id}")
    public String details(
            @PathVariable("id") Integer id,
            Model model,
            Authentication authentication) {


        EvaluacionFisica evaluacion =
                obtenerEvaluacionPermitida(
                        id,
                        authentication
                );


        model.addAttribute(
                "evaluacionFisica",
                evaluacion
        );


        model.addAttribute(
                "imc",
                evaluacionFisicaService
                        .calcularIMC(id)
        );


        return "evaluacionFisica/details";
    }


    // =====================================================
    // EDITAR
    // =====================================================

    @GetMapping("/edit/{id}")
    public String edit(
            @PathVariable("id") Integer id,
            Model model,
            Authentication authentication) {


        EvaluacionFisica evaluacion =
                obtenerEvaluacionPermitida(
                        id,
                        authentication
                );


        model.addAttribute(
                "evaluacionFisica",
                evaluacion
        );


        model.addAttribute(
                "clientes",
                clienteService.obtenerTodos()
        );


        model.addAttribute(
                "entrenadores",
                obtenerEntrenadoresPermitidos(
                        authentication
                )
        );


        return "evaluacionFisica/edit";
    }


    // =====================================================
    // CONFIRMAR ELIMINACIÓN
    // =====================================================

    @GetMapping("/remove/{id}")
    public String remove(
            @PathVariable("id") Integer id,
            Model model,
            Authentication authentication) {


        EvaluacionFisica evaluacion =
                obtenerEvaluacionPermitida(
                        id,
                        authentication
                );


        model.addAttribute(
                "evaluacionFisica",
                evaluacion
        );


        return "evaluacionFisica/delete";
    }


    // =====================================================
    // ELIMINAR
    // =====================================================

    @PostMapping("/delete")
    public String delete(
            EvaluacionFisica evaluacionFisica,
            RedirectAttributes attributes,
            Authentication authentication) {


        /*
         * Nunca confiamos solamente en el ID
         * enviado desde el formulario.
         */
        EvaluacionFisica evaluacionPermitida =
                obtenerEvaluacionPermitida(
                        evaluacionFisica.getId(),
                        authentication
                );


        evaluacionFisicaService.eliminar(
                evaluacionPermitida.getId()
        );


        attributes.addFlashAttribute(
                "msg",
                "Evaluación física eliminada correctamente"
        );


        return "redirect:/evaluaciones-fisicas";
    }


    // =====================================================
    // SEGURIDAD
    // =====================================================

    private boolean esAdmin(
            Authentication authentication) {

        return authentication
                .getAuthorities()
                .stream()
                .anyMatch(authority ->
                        authority
                                .getAuthority()
                                .equals("ADMIN")
                );
    }


    // =====================================================
    // OBTENER EVALUACIÓN PERMITIDA
    // =====================================================

    private EvaluacionFisica obtenerEvaluacionPermitida(
            Integer id,
            Authentication authentication) {


        // ADMIN PUEDE VER CUALQUIERA
        if (esAdmin(authentication)) {

            return evaluacionFisicaService
                    .obtenerPorId(id)
                    .orElseThrow();
        }


        /*
         * ENTRENADOR únicamente puede encontrar
         * evaluaciones asociadas a su email.
         */
        return evaluacionFisicaService
                .obtenerPorIdYEntrenadorEmail(
                        id,
                        authentication.getName()
                )
                .orElseThrow(() ->
                        new AccessDeniedException(
                                "No tienes permiso para acceder a esta evaluación"
                        )
                );
    }


    // =====================================================
    // ENTRENADORES PERMITIDOS
    // =====================================================

    private List<Entrenador> obtenerEntrenadoresPermitidos(
            Authentication authentication) {


        // ADMIN PUEDE VER TODOS
        if (esAdmin(authentication)) {

            return entrenadorService.obtenerTodos();
        }


        String email =
                authentication.getName();


        /*
         * ENTRENADOR únicamente recibe
         * su propio perfil en el select.
         */
        return entrenadorService
                .obtenerTodos()
                .stream()
                .filter(entrenador ->

                        entrenador.getUsuario() != null

                                && email.equals(
                                entrenador
                                        .getUsuario()
                                        .getEmail()
                        )
                )
                .toList();
    }


    // =====================================================
    // ENTRENADOR AUTENTICADO
    // =====================================================

    private Entrenador obtenerEntrenadorActual(
            Authentication authentication) {


        String email =
                authentication.getName();


        return entrenadorService
                .obtenerTodos()
                .stream()
                .filter(entrenador ->

                        entrenador.getUsuario() != null

                                && email.equals(
                                entrenador
                                        .getUsuario()
                                        .getEmail()
                        )
                )
                .findFirst()
                .orElseThrow(() ->
                        new AccessDeniedException(
                                "El usuario autenticado no tiene un perfil de entrenador"
                        )
                );
    }
}