package com.esfe.sistemagimnasio.controllers;

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

    @GetMapping
    public String index(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);

        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Rutina> rutinas =
                rutinaService.obtenerTodosPaginados(pageable);

        model.addAttribute("rutinas", rutinas);

        Map<Integer, List<RutinaEjercicio>> ejerciciosPorRutina =
                new HashMap<>();

        for (Rutina rutina : rutinas) {
            ejerciciosPorRutina.put(
                    rutina.getId(),
                    rutinaService.listarEjercicios(rutina.getId())
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

        int totalPages = rutinas.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream
                    .rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());

            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "rutina/index";
    }

    @GetMapping("/create")
    public String create(
            Rutina rutina,
            Model model) {

        model.addAttribute(
                "clientes",
                clienteService.obtenerTodos()
        );

        model.addAttribute(
                "entrenadores",
                entrenadorService.obtenerTodos()
        );

        return "rutina/create";
    }

    @PostMapping("/save")
    public String save(
            @Valid Rutina rutina,
            BindingResult result,
            Model model,
            RedirectAttributes attributes) {

        if (result.hasErrors()) {

            model.addAttribute(
                    "clientes",
                    clienteService.obtenerTodos()
            );

            model.addAttribute(
                    "entrenadores",
                    entrenadorService.obtenerTodos()
            );

            attributes.addFlashAttribute(
                    "error",
                    "No se pudo guardar debido a un error."
            );

            if (rutina.getId() != null) {
                return "rutina/edit";
            }

            return "rutina/create";
        }

        if (rutina.getId() == null) {
            rutina.setFechaCreacion(LocalDate.now());
        } else {
            rutina.setFechaModificacion(LocalDate.now());
        }

        rutinaService.guardar(rutina);

        attributes.addFlashAttribute(
                "msg",
                "Rutina guardada correctamente"
        );

        return "redirect:/rutinas";
    }

    @GetMapping("/details/{id}")
    public String details(
            @PathVariable("id") Integer id,
            Model model) {

        Rutina rutina =
                rutinaService
                        .obtenerPorId(id)
                        .orElseThrow();

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

    @GetMapping("/edit/{id}")
    public String edit(
            @PathVariable("id") Integer id,
            Model model) {

        Rutina rutina =
                rutinaService
                        .obtenerPorId(id)
                        .orElseThrow();

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
                entrenadorService.obtenerTodos()
        );

        return "rutina/edit";
    }

    @GetMapping("/remove/{id}")
    public String remove(
            @PathVariable("id") Integer id,
            Model model) {

        Rutina rutina =
                rutinaService
                        .obtenerPorId(id)
                        .orElseThrow();

        model.addAttribute(
                "rutina",
                rutina
        );

        return "rutina/delete";
    }

    @PostMapping("/delete")
    public String delete(
            Rutina rutina,
            RedirectAttributes attributes) {

        rutinaService.eliminar(rutina.getId());

        attributes.addFlashAttribute(
                "msg",
                "Rutina eliminada correctamente"
        );

        return "redirect:/rutinas";
    }

    @PostMapping("/{id}/ejercicios/agregar")
    public String agregarEjercicio(
            @PathVariable("id") Integer rutinaId,
            RutinaEjercicio rutinaEjercicio,
            RedirectAttributes attributes) {

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

    @GetMapping("/{rutinaId}/ejercicios/quitar/{rutinaEjercicioId}")
    public String quitarEjercicio(
            @PathVariable Integer rutinaId,
            @PathVariable Integer rutinaEjercicioId,
            RedirectAttributes attributes) {

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
}