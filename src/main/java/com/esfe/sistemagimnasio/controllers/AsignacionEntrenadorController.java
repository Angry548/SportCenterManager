package com.esfe.sistemagimnasio.controllers;

import com.esfe.sistemagimnasio.models.AsignacionEntrenador;
import com.esfe.sistemagimnasio.services.interfaces.IAsignacionEntrenadorService;
import com.esfe.sistemagimnasio.services.interfaces.IClienteService;
import com.esfe.sistemagimnasio.services.interfaces.IEntrenadorService;
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
@RequestMapping("/asignaciones-entrenador")
public class AsignacionEntrenadorController {

    @Autowired
    private IAsignacionEntrenadorService asignacionEntrenadorService;

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IEntrenadorService entrenadorService;

    @GetMapping
    public String index(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);

        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<AsignacionEntrenador> asignaciones =
                asignacionEntrenadorService.obtenerTodosPaginados(pageable);

        model.addAttribute("asignaciones", asignaciones);

        int totalPages = asignaciones.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream
                    .rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());

            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "asignacionEntrenador/index";
    }

    @GetMapping("/create")
    public String create(
            AsignacionEntrenador asignacionEntrenador,
            Model model) {

        model.addAttribute(
                "clientes",
                clienteService.obtenerTodos()
        );

        model.addAttribute(
                "entrenadores",
                entrenadorService.obtenerTodos()
        );

        return "asignacionEntrenador/create";
    }

    @PostMapping("/save")
    public String save(
            @Valid AsignacionEntrenador asignacionEntrenador,
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
                    "No se pudo guardar debido a un error de validación."
            );

            return "asignacionEntrenador/create";
        }

        asignacionEntrenadorService.guardar(asignacionEntrenador);

        attributes.addFlashAttribute(
                "msg",
                "Asignación guardada correctamente"
        );

        return "redirect:/asignaciones-entrenador";
    }

    @GetMapping("/details/{id}")
    public String details(
            @PathVariable("id") Integer id,
            Model model) {

        AsignacionEntrenador asignacion =
                asignacionEntrenadorService
                        .obtenerPorId(id)
                        .orElseThrow();

        model.addAttribute(
                "asignacionEntrenador",
                asignacion
        );

        model.addAttribute(
                "activa",
                asignacionEntrenadorService.estaActiva(id)
        );

        return "asignacionEntrenador/details";
    }

    @GetMapping("/edit/{id}")
    public String edit(
            @PathVariable("id") Integer id,
            Model model) {

        AsignacionEntrenador asignacion =
                asignacionEntrenadorService
                        .obtenerPorId(id)
                        .orElseThrow();

        model.addAttribute(
                "asignacionEntrenador",
                asignacion
        );

        model.addAttribute(
                "clientes",
                clienteService.obtenerTodos()
        );

        model.addAttribute(
                "entrenadores",
                entrenadorService.obtenerTodos()
        );

        return "asignacionEntrenador/edit";
    }

    @GetMapping("/remove/{id}")
    public String remove(
            @PathVariable("id") Integer id,
            Model model) {

        AsignacionEntrenador asignacion =
                asignacionEntrenadorService
                        .obtenerPorId(id)
                        .orElseThrow();

        model.addAttribute(
                "asignacionEntrenador",
                asignacion
        );

        return "asignacionEntrenador/delete";
    }

    @PostMapping("/delete")
    public String delete(
            AsignacionEntrenador asignacionEntrenador,
            RedirectAttributes attributes) {

        asignacionEntrenadorService.eliminar(
                asignacionEntrenador.getId()
        );

        attributes.addFlashAttribute(
                "msg",
                "Asignación eliminada correctamente"
        );

        return "redirect:/asignaciones-entrenador";
    }

    @GetMapping("/finalizar/{id}")
    public String finalizar(
            @PathVariable("id") Integer id,
            RedirectAttributes attributes) {

        asignacionEntrenadorService.finalizar(id);

        attributes.addFlashAttribute(
                "msg",
                "La asignación ha sido finalizada con éxito."
        );

        return "redirect:/asignaciones-entrenador";
    }
}