package com.esfe.sistemagimnasio.controllers;

import com.esfe.sistemagimnasio.enums.Dificultad;
import com.esfe.sistemagimnasio.models.Ejercicio;
import com.esfe.sistemagimnasio.services.interfaces.IEjercicioService;
import com.esfe.sistemagimnasio.services.interfaces.IGrupoMuscularService;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/ejercicios")
public class EjercicioController {

    @Autowired
    private IEjercicioService ejercicioService;

    @Autowired
    private IGrupoMuscularService grupoMuscularService;

    @GetMapping
    public String index(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);

        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Ejercicio> ejercicios =
                ejercicioService.obtenerTodosPaginados(pageable);

        model.addAttribute("ejercicios", ejercicios);

        int totalPages = ejercicios.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream
                    .rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());

            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "ejercicio/index";
    }

    @GetMapping("/create")
    public String create(
            Ejercicio ejercicio,
            Model model) {

        model.addAttribute(
                "gruposMusculares",
                grupoMuscularService.obtenerTodos()
        );

        model.addAttribute(
                "dificultades",
                Dificultad.values()
        );

        return "ejercicio/create";
    }

    @PostMapping("/save")
    public String save(
            @Valid Ejercicio ejercicio,
            BindingResult result,
            Model model,
            RedirectAttributes attributes) {

        if (result.hasErrors()) {

            model.addAttribute(
                    "gruposMusculares",
                    grupoMuscularService.obtenerTodos()
            );

            model.addAttribute(
                    "dificultades",
                    Dificultad.values()
            );

            attributes.addFlashAttribute(
                    "error",
                    "No se pudo guardar debido a un error de validación."
            );

            return "ejercicio/create";
        }

        ejercicioService.guardar(ejercicio);

        attributes.addFlashAttribute(
                "msg",
                "Ejercicio guardado correctamente"
        );

        return "redirect:/ejercicios";
    }

    @GetMapping("/details/{id}")
    public String details(
            @PathVariable("id") Integer id,
            Model model) {

        Ejercicio ejercicio =
                ejercicioService
                        .obtenerPorId(id)
                        .orElseThrow();

        model.addAttribute(
                "ejercicio",
                ejercicio
        );

        return "ejercicio/details";
    }

    @GetMapping("/edit/{id}")
    public String edit(
            @PathVariable("id") Integer id,
            Model model) {

        Ejercicio ejercicio =
                ejercicioService
                        .obtenerPorId(id)
                        .orElseThrow();

        model.addAttribute(
                "ejercicio",
                ejercicio
        );

        model.addAttribute(
                "gruposMusculares",
                grupoMuscularService.obtenerTodos()
        );

        model.addAttribute(
                "dificultades",
                Dificultad.values()
        );

        return "ejercicio/edit";
    }

    @GetMapping("/remove/{id}")
    public String remove(
            @PathVariable("id") Integer id,
            Model model) {

        Ejercicio ejercicio =
                ejercicioService
                        .obtenerPorId(id)
                        .orElseThrow();

        model.addAttribute(
                "ejercicio",
                ejercicio
        );

        return "ejercicio/delete";
    }

    @PostMapping("/delete")
    public String delete(
            Ejercicio ejercicio,
            RedirectAttributes attributes) {

        ejercicioService.eliminar(
                ejercicio.getId()
        );

        attributes.addFlashAttribute(
                "msg",
                "Ejercicio eliminado correctamente"
        );

        return "redirect:/ejercicios";
    }
}
