package com.esfe.sistemagimnasio.controllers;

import com.esfe.sistemagimnasio.models.Asistencia;
import com.esfe.sistemagimnasio.services.interfaces.IAsistenciaService;
import com.esfe.sistemagimnasio.services.interfaces.IClienteService;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/asistencias")
public class AsistenciaController {

    @Autowired
    private IAsistenciaService asistenciaService;

    @Autowired
    private IClienteService clienteService;

    @GetMapping
    public String index(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);

        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Asistencia> asistencias =
                asistenciaService.obtenerTodosPaginados(pageable);

        model.addAttribute(
                "asistencias",
                asistencias
        );

        int totalPages = asistencias.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream
                    .rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());

            model.addAttribute(
                    "pageNumbers",
                    pageNumbers
            );
        }

        return "asistencia/index";
    }

    @GetMapping("/create")
    public String create(
            Asistencia asistencia,
            Model model) {

        model.addAttribute(
                "clientes",
                clienteService.obtenerTodos()
        );

        return "asistencia/create";
    }

    @PostMapping("/save")
    public String save(
            @Valid Asistencia asistencia,
            BindingResult result,
            Model model,
            RedirectAttributes attributes) {

        if (result.hasErrors()) {

            model.addAttribute(
                    "asistencia",
                    asistencia
            );

            model.addAttribute(
                    "clientes",
                    clienteService.obtenerTodos()
            );

            attributes.addFlashAttribute(
                    "error",
                    "No se pudo guardar debido a un error."
            );

            if (asistencia.getId() != null) {
                return "asistencia/edit";
            }

            return "asistencia/create";
        }

        if (asistencia.getFechaHora() == null) {

            if (asistencia.getId() != null) {

                Asistencia asistenciaExistente =
                        asistenciaService
                                .obtenerPorId(asistencia.getId())
                                .orElseThrow();

                asistencia.setFechaHora(
                        asistenciaExistente.getFechaHora()
                );

            } else {
                asistencia.setFechaHora(
                        LocalDateTime.now()
                );
            }
        }

        asistenciaService.guardar(asistencia);

        attributes.addFlashAttribute(
                "msg",
                "Asistencia guardada correctamente"
        );

        return "redirect:/asistencias";
    }

    @GetMapping("/details/{id}")
    public String details(
            @PathVariable("id") Integer id,
            Model model) {

        Asistencia asistencia =
                asistenciaService
                        .obtenerPorId(id)
                        .orElseThrow();

        model.addAttribute(
                "asistencia",
                asistencia
        );

        return "asistencia/details";
    }

    @GetMapping("/edit/{id}")
    public String edit(
            @PathVariable("id") Integer id,
            Model model) {

        Asistencia asistencia =
                asistenciaService
                        .obtenerPorId(id)
                        .orElseThrow();

        model.addAttribute(
                "asistencia",
                asistencia
        );

        model.addAttribute(
                "clientes",
                clienteService.obtenerTodos()
        );

        return "asistencia/edit";
    }

    @GetMapping("/remove/{id}")
    public String remove(
            @PathVariable("id") Integer id,
            Model model) {

        Asistencia asistencia =
                asistenciaService
                        .obtenerPorId(id)
                        .orElseThrow();

        model.addAttribute(
                "asistencia",
                asistencia
        );

        return "asistencia/delete";
    }

    @PostMapping("/delete")
    public String delete(
            Asistencia asistencia,
            RedirectAttributes attributes) {

        asistenciaService.eliminar(
                asistencia.getId()
        );

        attributes.addFlashAttribute(
                "msg",
                "Asistencia eliminada correctamente"
        );

        return "redirect:/asistencias";
    }

    @PostMapping("/validar-acceso/{id}")
    public String validarAcceso(
            @PathVariable("id") Integer id,
            RedirectAttributes attributes) {

        try {

            boolean tieneAcceso =
                    asistenciaService.validarAcceso(id);

            if (tieneAcceso) {
                attributes.addFlashAttribute(
                        "msg",
                        "Acceso válido"
                );
            } else {
                attributes.addFlashAttribute(
                        "error",
                        "El cliente no tiene acceso"
                );
            }

        } catch (Exception e) {

            attributes.addFlashAttribute(
                    "error",
                    "No se pudo validar el acceso"
            );
        }

        return "redirect:/asistencias";
    }
}