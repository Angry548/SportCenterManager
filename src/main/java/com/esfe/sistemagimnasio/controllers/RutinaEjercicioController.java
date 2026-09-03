package com.esfe.sistemagimnasio.controllers;

import com.esfe.sistemagimnasio.models.Rutina;
import com.esfe.sistemagimnasio.models.RutinaEjercicio;
import com.esfe.sistemagimnasio.services.interfaces.IEjercicioService;
import com.esfe.sistemagimnasio.services.interfaces.IRutinaEjercicioService;
import com.esfe.sistemagimnasio.services.interfaces.IRutinaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/rutina-ejercicios")
public class RutinaEjercicioController {

    @Autowired
    private IRutinaEjercicioService rutinaEjercicioService;

    @Autowired
    private IRutinaService rutinaService;

    @Autowired
    private IEjercicioService ejercicioService;


    // =====================================================
    // LISTADO AGRUPADO
    // =====================================================

    @GetMapping
    public String index(
            Model model,
            Authentication authentication) {

        List<RutinaEjercicio> todos =
                rutinaEjercicioService.obtenerTodos();


        // ==========================================
        // ENTRENADOR SOLO VE SUS RUTINAS
        // ==========================================

        if (!esAdmin(authentication)) {

            String email =
                    authentication.getName();

            todos =
                    todos.stream()
                            .filter(item ->
                                    item.getRutina() != null
                                            && item.getRutina().getEntrenador() != null
                                            && item.getRutina()
                                            .getEntrenador()
                                            .getUsuario() != null
                                            && email.equals(
                                            item.getRutina()
                                                    .getEntrenador()
                                                    .getUsuario()
                                                    .getEmail()
                                    )
                            )
                            .toList();
        }


        // ==========================================
        // AGRUPAR POR RUTINA
        // ==========================================

        Map<Integer, List<RutinaEjercicio>> rutinasAgrupadas =
                todos.stream()
                        .filter(item ->
                                item.getRutina() != null
                                        && item.getRutina().getId() != null
                        )
                        .collect(
                                Collectors.groupingBy(
                                        item -> item.getRutina().getId(),
                                        LinkedHashMap::new,
                                        Collectors.toList()
                                )
                        );


        model.addAttribute(
                "rutinasAgrupadas",
                rutinasAgrupadas
        );

        model.addAttribute(
                "totalRutinas",
                rutinasAgrupadas.size()
        );


        return "rutinaEjercicio/index";
    }


    // =====================================================
    // CREAR
    // =====================================================

    @GetMapping("/create")
    public String create(
            RutinaEjercicio rutinaEjercicio,
            @RequestParam(
                    value = "rutinaId",
                    required = false
            ) Integer rutinaId,
            Model model,
            Authentication authentication) {


        if (rutinaId != null) {

            Rutina rutina =
                    obtenerRutinaPermitida(
                            rutinaId,
                            authentication
                    );

            rutinaEjercicio.setRutina(
                    rutina
            );
        }


        model.addAttribute(
                "rutinas",
                obtenerRutinasPermitidas(authentication)
        );

        model.addAttribute(
                "ejercicios",
                ejercicioService.obtenerTodos()
        );


        return "rutinaEjercicio/create";
    }


    // =====================================================
    // GUARDAR
    // =====================================================

    @PostMapping("/save")
    public String save(
            @Valid RutinaEjercicio rutinaEjercicio,
            BindingResult result,
            Model model,
            RedirectAttributes attributes,
            Authentication authentication) {


        // ==========================================
        // VALIDAR QUE LA RUTINA LE PERTENEZCA
        // ==========================================

        if (rutinaEjercicio.getRutina() != null
                && rutinaEjercicio.getRutina().getId() != null) {

            Integer rutinaId =
                    rutinaEjercicio
                            .getRutina()
                            .getId();


            Rutina rutinaPermitida =
                    buscarRutinaPermitida(
                            rutinaId,
                            authentication
                    );


            if (rutinaPermitida == null) {

                result.rejectValue(
                        "rutina.id",
                        "rutina.noPermitida",
                        "No tienes permiso para utilizar esta rutina"
                );

            } else {

                /*
                 * Usamos la entidad Rutina real
                 * obtenida desde la BD.
                 */
                rutinaEjercicio.setRutina(
                        rutinaPermitida
                );
            }
        }


        // ==========================================
        // ERRORES
        // ==========================================

        if (result.hasErrors()) {

            model.addAttribute(
                    "rutinas",
                    obtenerRutinasPermitidas(authentication)
            );

            model.addAttribute(
                    "ejercicios",
                    ejercicioService.obtenerTodos()
            );

            model.addAttribute(
                    "error",
                    "No se pudo guardar debido a un error de validación."
            );


            if (rutinaEjercicio.getId() != null) {
                return "rutinaEjercicio/edit";
            }

            return "rutinaEjercicio/create";
        }


        Integer rutinaId =
                rutinaEjercicio
                        .getRutina()
                        .getId();


        rutinaEjercicioService
                .guardar(rutinaEjercicio);


        attributes.addFlashAttribute(
                "msg",
                "Ejercicio guardado en la rutina correctamente"
        );


        return "redirect:/rutina-ejercicios/details/"
                + rutinaId;
    }


    // =====================================================
    // DETALLE DE RUTINA
    // =====================================================

    @GetMapping("/details/{rutinaId}")
    public String details(
            @PathVariable("rutinaId") Integer rutinaId,
            Model model,
            Authentication authentication) {


        Rutina rutina =
                obtenerRutinaPermitida(
                        rutinaId,
                        authentication
                );


        List<RutinaEjercicio> ejercicios =
                rutinaEjercicioService
                        .obtenerTodos()
                        .stream()
                        .filter(item ->
                                item.getRutina() != null

                                        && item.getRutina()
                                        .getId()
                                        .equals(rutinaId)
                        )
                        .toList();


        model.addAttribute(
                "rutina",
                rutina
        );

        model.addAttribute(
                "rutinaEjercicios",
                ejercicios
        );


        return "rutinaEjercicio/details";
    }


    // =====================================================
    // EDITAR
    // =====================================================

    @GetMapping("/edit/{id}")
    public String edit(
            @PathVariable("id") Integer id,
            Model model,
            Authentication authentication) {


        RutinaEjercicio rutinaEjercicio =
                obtenerRutinaEjercicioPermitido(
                        id,
                        authentication
                );


        model.addAttribute(
                "rutinaEjercicio",
                rutinaEjercicio
        );

        model.addAttribute(
                "rutinas",
                obtenerRutinasPermitidas(authentication)
        );

        model.addAttribute(
                "ejercicios",
                ejercicioService.obtenerTodos()
        );


        return "rutinaEjercicio/edit";
    }


    // =====================================================
    // CONFIRMAR ELIMINACIÓN
    // =====================================================

    @GetMapping("/remove/{id}")
    public String remove(
            @PathVariable("id") Integer id,
            Model model,
            Authentication authentication) {


        RutinaEjercicio rutinaEjercicio =
                obtenerRutinaEjercicioPermitido(
                        id,
                        authentication
                );


        model.addAttribute(
                "rutinaEjercicio",
                rutinaEjercicio
        );


        return "rutinaEjercicio/delete";
    }


    // =====================================================
    // ELIMINAR
    // =====================================================

    @PostMapping("/delete")
    public String delete(
            RutinaEjercicio rutinaEjercicio,
            RedirectAttributes attributes,
            Authentication authentication) {


        RutinaEjercicio existente =
                obtenerRutinaEjercicioPermitido(
                        rutinaEjercicio.getId(),
                        authentication
                );


        Integer rutinaId =
                existente
                        .getRutina()
                        .getId();


        rutinaEjercicioService
                .eliminar(
                        existente.getId()
                );


        attributes.addFlashAttribute(
                "msg",
                "Ejercicio eliminado de la rutina correctamente"
        );


        return "redirect:/rutina-ejercicios/details/"
                + rutinaId;
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
    // BUSCAR RUTINA PERMITIDA
    // =====================================================

    private Rutina buscarRutinaPermitida(
            Integer rutinaId,
            Authentication authentication) {


        if (esAdmin(authentication)) {

            return rutinaService
                    .obtenerPorId(rutinaId)
                    .orElse(null);
        }


        return rutinaService
                .obtenerPorIdYEntrenadorEmail(
                        rutinaId,
                        authentication.getName()
                )
                .orElse(null);
    }


    // =====================================================
    // OBTENER RUTINA O DENEGAR ACCESO
    // =====================================================

    private Rutina obtenerRutinaPermitida(
            Integer rutinaId,
            Authentication authentication) {


        Rutina rutina =
                buscarRutinaPermitida(
                        rutinaId,
                        authentication
                );


        if (rutina == null) {

            throw new AccessDeniedException(
                    "No tienes permiso para acceder a esta rutina"
            );
        }


        return rutina;
    }


    // =====================================================
    // RUTINAS VISIBLES EN LOS SELECT
    // =====================================================

    private List<Rutina> obtenerRutinasPermitidas(
            Authentication authentication) {


        if (esAdmin(authentication)) {

            return rutinaService
                    .obtenerTodos();
        }


        String email =
                authentication.getName();


        return rutinaService
                .obtenerTodos()
                .stream()
                .filter(rutina ->

                        rutina.getEntrenador() != null

                                && rutina
                                .getEntrenador()
                                .getUsuario() != null

                                && email.equals(
                                rutina
                                        .getEntrenador()
                                        .getUsuario()
                                        .getEmail()
                        )
                )
                .toList();
    }


    // =====================================================
    // VALIDAR UN RUTINA-EJERCICIO INDIVIDUAL
    // =====================================================

    private RutinaEjercicio obtenerRutinaEjercicioPermitido(
            Integer id,
            Authentication authentication) {


        RutinaEjercicio rutinaEjercicio =
                rutinaEjercicioService
                        .obtenerPorId(id)
                        .orElseThrow();


        /*
         * Al validar la rutina asociada también
         * estamos validando el RutinaEjercicio.
         */
        obtenerRutinaPermitida(
                rutinaEjercicio
                        .getRutina()
                        .getId(),
                authentication
        );


        return rutinaEjercicio;
    }
}