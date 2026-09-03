package com.esfe.sistemagimnasio.controllers;

import com.esfe.sistemagimnasio.models.Entrenador;
import com.esfe.sistemagimnasio.models.Rutina;
import com.esfe.sistemagimnasio.models.RutinaEjercicio;
import com.esfe.sistemagimnasio.services.interfaces.IClienteService;
import com.esfe.sistemagimnasio.services.interfaces.IEntrenadorService;
import com.esfe.sistemagimnasio.services.interfaces.IEjercicioService;
import com.esfe.sistemagimnasio.services.interfaces.IRutinaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/rutinas")
public class RutinaController {

    @Autowired
    private IRutinaService rutinaService;

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IEntrenadorService entrenadorService;

    @Autowired
    private IEjercicioService ejercicioService;


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


        Page<Rutina> rutinas;


        // ADMIN VE TODAS
        if (esAdmin(authentication)) {

            rutinas =
                    rutinaService
                            .obtenerTodosPaginados(pageable);

        } else {

            // ENTRENADOR SOLO VE LAS SUYAS
            rutinas =
                    rutinaService
                            .obtenerPorEntrenadorEmail(
                                    authentication.getName(),
                                    pageable
                            );
        }


        model.addAttribute(
                "rutinas",
                rutinas
        );


        Map<Integer, List<RutinaEjercicio>>
                ejerciciosPorRutina =
                new HashMap<>();


        for (Rutina rutina : rutinas) {

            ejerciciosPorRutina.put(
                    rutina.getId(),
                    rutinaService.listarEjercicios(
                            rutina.getId()
                    )
            );
        }


        model.addAttribute(
                "ejerciciosPorRutina",
                ejerciciosPorRutina
        );

        model.addAttribute(
                "ejercicios",
                ejercicioService.obtenerTodos()
        );

        model.addAttribute(
                "rutinaEjercicio",
                new RutinaEjercicio()
        );


        int totalPages =
                rutinas.getTotalPages();

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


        return "rutina/index";
    }


    // =====================================================
    // CREAR
    // =====================================================

    @GetMapping("/create")
    public String create(
            Rutina rutina,
            Model model,
            Authentication authentication) {

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

        return "rutina/create";
    }


    // =====================================================
    // GUARDAR
    // =====================================================

    @PostMapping("/save")
    public String save(
            @Valid Rutina rutina,
            BindingResult result,
            Model model,
            RedirectAttributes attributes,
            Authentication authentication) {


        // ==========================================
        // VALIDAR ENTRENADOR
        // ==========================================

        if (!esAdmin(authentication)) {

            Entrenador entrenadorActual =
                    obtenerEntrenadorActual(
                            authentication
                    );


            /*
             * Un entrenador no puede enviar manualmente
             * el ID de otro entrenador.
             *
             * Siempre forzamos su propio perfil.
             */
            rutina.setEntrenador(
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

            if (rutina.getId() != null) {

                return "rutina/edit";
            }

            return "rutina/create";
        }


        // ==========================================
        // FECHAS
        // ==========================================

        if (rutina.getId() == null) {

            rutina.setFechaCreacion(
                    LocalDate.now()
            );

        } else {

            /*
             * Antes de editar comprobamos nuevamente
             * que tenga permiso sobre la rutina.
             */
            obtenerRutinaPermitida(
                    rutina.getId(),
                    authentication
            );

            rutina.setFechaModificacion(
                    LocalDate.now()
            );
        }


        rutinaService.guardar(rutina);


        attributes.addFlashAttribute(
                "msg",
                "Rutina guardada correctamente"
        );


        return "redirect:/rutinas";
    }


    // =====================================================
    // DETALLES
    // =====================================================

    @GetMapping("/details/{id}")
    public String details(
            @PathVariable("id") Integer id,
            Model model,
            Authentication authentication) {


        Rutina rutina =
                obtenerRutinaPermitida(
                        id,
                        authentication
                );


        model.addAttribute(
                "rutina",
                rutina
        );

        model.addAttribute(
                "rutinaEjercicios",
                rutinaService.listarEjercicios(id)
        );


        return "rutina/details";
    }


    // =====================================================
    // EDITAR
    // =====================================================

    @GetMapping("/edit/{id}")
    public String edit(
            @PathVariable("id") Integer id,
            Model model,
            Authentication authentication) {


        Rutina rutina =
                obtenerRutinaPermitida(
                        id,
                        authentication
                );


        model.addAttribute(
                "rutina",
                rutina
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


        return "rutina/edit";
    }


    // =====================================================
    // ELIMINAR
    // =====================================================

    @GetMapping("/remove/{id}")
    public String remove(
            @PathVariable("id") Integer id,
            Model model,
            Authentication authentication) {


        Rutina rutina =
                obtenerRutinaPermitida(
                        id,
                        authentication
                );


        model.addAttribute(
                "rutina",
                rutina
        );


        return "rutina/delete";
    }


    @PostMapping("/delete")
    public String delete(
            Rutina rutina,
            RedirectAttributes attributes,
            Authentication authentication) {


        /*
         * Nunca confiamos solamente en el ID
         * enviado por el formulario.
         */
        obtenerRutinaPermitida(
                rutina.getId(),
                authentication
        );


        rutinaService.eliminar(
                rutina.getId()
        );


        attributes.addFlashAttribute(
                "msg",
                "Rutina eliminada correctamente"
        );


        return "redirect:/rutinas";
    }


    // =====================================================
    // AGREGAR EJERCICIO
    // =====================================================

    @PostMapping("/{id}/ejercicios/agregar")
    public String agregarEjercicio(
            @PathVariable("id") Integer rutinaId,
            RutinaEjercicio rutinaEjercicio,
            RedirectAttributes attributes,
            Authentication authentication) {


        /*
         * Verificamos propiedad antes de modificar
         * la rutina.
         */
        obtenerRutinaPermitida(
                rutinaId,
                authentication
        );


        rutinaService.agregarEjercicio(
                rutinaId,
                rutinaEjercicio
        );


        attributes.addFlashAttribute(
                "msg",
                "Ejercicio agregado correctamente"
        );


        return "redirect:/rutinas";
    }


    // =====================================================
    // QUITAR EJERCICIO
    // =====================================================

    @GetMapping("/{rutinaId}/ejercicios/quitar/{rutinaEjercicioId}")
    public String quitarEjercicio(
            @PathVariable Integer rutinaId,
            @PathVariable Integer rutinaEjercicioId,
            RedirectAttributes attributes,
            Authentication authentication) {


        obtenerRutinaPermitida(
                rutinaId,
                authentication
        );


        rutinaService.quitarEjercicio(
                rutinaId,
                rutinaEjercicioId
        );


        attributes.addFlashAttribute(
                "msg",
                "Ejercicio quitado correctamente"
        );


        return "redirect:/rutinas";
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


    /*
     * ADMIN:
     * obtiene cualquier rutina.
     *
     * ENTRENADOR:
     * únicamente obtiene una rutina si
     * está asignada a su usuario.
     */
    private Rutina obtenerRutinaPermitida(
            Integer id,
            Authentication authentication) {

        if (esAdmin(authentication)) {

            return rutinaService
                    .obtenerPorId(id)
                    .orElseThrow();
        }


        return rutinaService
                .obtenerPorIdYEntrenadorEmail(
                        id,
                        authentication.getName()
                )
                .orElseThrow();
    }


    /*
     * ADMIN:
     * puede seleccionar cualquier entrenador.
     *
     * ENTRENADOR:
     * únicamente puede seleccionarse a sí mismo.
     */
    private List<Entrenador> obtenerEntrenadoresPermitidos(
            Authentication authentication) {

        if (esAdmin(authentication)) {

            return entrenadorService
                    .obtenerTodos();
        }


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
                .toList();
    }


    /*
     * Obtiene el perfil Entrenador asociado
     * al usuario que inició sesión.
     */
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
                .orElseThrow();
    }
}