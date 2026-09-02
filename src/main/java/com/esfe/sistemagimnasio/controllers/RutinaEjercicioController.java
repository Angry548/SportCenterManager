package com.esfe.sistemagimnasio.controllers;

import com.esfe.sistemagimnasio.models.RutinaEjercicio;
import com.esfe.sistemagimnasio.services.interfaces.IEjercicioService;
import com.esfe.sistemagimnasio.services.interfaces.IRutinaEjercicioService;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/rutina-ejercicios")
public class RutinaEjercicioController {

    @Autowired
    private IRutinaEjercicioService rutinaEjercicioService;

    @Autowired
    private IRutinaService rutinaService;

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

        Page<RutinaEjercicio> rutinaEjercicios =
                rutinaEjercicioService.obtenerTodosPaginados(pageable);

        model.addAttribute("rutinaEjercicios", rutinaEjercicios);

        int totalPages = rutinaEjercicios.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream
                    .rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());

            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "rutinaEjercicio/index";
    }

    @GetMapping("/create")
    public String create(
            RutinaEjercicio rutinaEjercicio,
            Model model) {

        model.addAttribute("rutinas", rutinaService.obtenerTodos());
        model.addAttribute("ejercicios", ejercicioService.obtenerTodos());

        return "rutinaEjercicio/create";
    }

    @PostMapping("/save")
    public String save(
            @Valid RutinaEjercicio rutinaEjercicio,
            BindingResult result,
            Model model,
            RedirectAttributes attributes) {

        if (result.hasErrors()) {
            model.addAttribute("rutinas", rutinaService.obtenerTodos());
            model.addAttribute("ejercicios", ejercicioService.obtenerTodos());

            attributes.addFlashAttribute(
                    "error",
                    "No se pudo guardar debido a un error de validación."
            );

            return "rutinaEjercicio/create";
        }

        rutinaEjercicioService.guardar(rutinaEjercicio);

        attributes.addFlashAttribute(
                "msg",
                "Asignación de ejercicio a rutina guardada correctamente"
        );

        return "redirect:/rutina-ejercicios";
    }

    @GetMapping("/details/{id}")
    public String details(
            @PathVariable("id") Integer id,
            Model model) {

        RutinaEjercicio rutinaEjercicio =
                rutinaEjercicioService
                        .obtenerPorId(id)
                        .orElseThrow();

        model.addAttribute("rutinaEjercicio", rutinaEjercicio);

        return "rutinaEjercicio/details";
    }

    @GetMapping("/edit/{id}")
    public String edit(
            @PathVariable("id") Integer id,
            Model model) {

        RutinaEjercicio rutinaEjercicio =
                rutinaEjercicioService
                        .obtenerPorId(id)
                        .orElseThrow();

        model.addAttribute("rutinaEjercicio", rutinaEjercicio);
        model.addAttribute("rutinas", rutinaService.obtenerTodos());
        model.addAttribute("ejercicios", ejercicioService.obtenerTodos());

        return "rutinaEjercicio/edit";
    }

    @GetMapping("/remove/{id}")
    public String remove(
            @PathVariable("id") Integer id,
            Model model) {

        RutinaEjercicio rutinaEjercicio =
                rutinaEjercicioService
                        .obtenerPorId(id)
                        .orElseThrow();

        model.addAttribute("rutinaEjercicio", rutinaEjercicio);

        return "rutinaEjercicio/delete";
    }

    @PostMapping("/delete")
    public String delete(
            RutinaEjercicio rutinaEjercicio,
            RedirectAttributes attributes) {

        rutinaEjercicioService.eliminar(rutinaEjercicio.getId());

        attributes.addFlashAttribute(
                "msg",
                "Ejercicio eliminado de la rutina correctamente"
        );

        return "redirect:/rutina-ejercicios";
    }
}