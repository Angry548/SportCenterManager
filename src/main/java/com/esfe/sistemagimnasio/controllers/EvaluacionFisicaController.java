package com.esfe.sistemagimnasio.controllers;

import com.esfe.sistemagimnasio.models.EvaluacionFisica;
import com.esfe.sistemagimnasio.services.interfaces.IClienteService;
import com.esfe.sistemagimnasio.services.interfaces.IEntrenadorService;
import com.esfe.sistemagimnasio.services.interfaces.IEvaluacionFisicaService;
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
@RequestMapping("/evaluaciones-fisicas")
public class EvaluacionFisicaController {

    @Autowired
    private IEvaluacionFisicaService evaluacionFisicaService;

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

        Page<EvaluacionFisica> evaluaciones =
                evaluacionFisicaService.obtenerTodosPaginados(pageable);

        model.addAttribute("evaluaciones", evaluaciones);

        int totalPages = evaluaciones.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream
                    .rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());

            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "evaluacionFisica/index";
    }

    @GetMapping("/create")
    public String create(
            EvaluacionFisica evaluacionFisica,
            Model model) {

        model.addAttribute(
                "clientes",
                clienteService.obtenerTodos()
        );

        model.addAttribute(
                "entrenadores",
                entrenadorService.obtenerTodos()
        );

        return "evaluacionFisica/create";
    }

    @PostMapping("/save")
    public String save(
            @Valid EvaluacionFisica evaluacionFisica,
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

            if (evaluacionFisica.getId() != null) {
                return "evaluacionFisica/edit";
            }

            return "evaluacionFisica/create";
        }

        evaluacionFisicaService.guardar(evaluacionFisica);

        attributes.addFlashAttribute(
                "msg",
                "Evaluación física guardada correctamente"
        );

        return "redirect:/evaluaciones-fisicas";
    }

    @GetMapping("/details/{id}")
    public String details(
            @PathVariable("id") Integer id,
            Model model) {

        EvaluacionFisica evaluacion =
                evaluacionFisicaService
                        .obtenerPorId(id)
                        .orElseThrow();

        model.addAttribute(
                "evaluacionFisica",
                evaluacion
        );

        model.addAttribute(
                "imc",
                evaluacionFisicaService.calcularIMC(id)
        );

        return "evaluacionFisica/details";
    }

    @GetMapping("/edit/{id}")
    public String edit(
            @PathVariable("id") Integer id,
            Model model) {

        EvaluacionFisica evaluacion =
                evaluacionFisicaService
                        .obtenerPorId(id)
                        .orElseThrow();

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
                entrenadorService.obtenerTodos()
        );

        return "evaluacionFisica/edit";
    }

    @GetMapping("/remove/{id}")
    public String remove(
            @PathVariable("id") Integer id,
            Model model) {

        EvaluacionFisica evaluacion =
                evaluacionFisicaService
                        .obtenerPorId(id)
                        .orElseThrow();

        model.addAttribute(
                "evaluacionFisica",
                evaluacion
        );

        return "evaluacionFisica/delete";
    }

    @PostMapping("/delete")
    public String delete(
            EvaluacionFisica evaluacionFisica,
            RedirectAttributes attributes) {

        evaluacionFisicaService.eliminar(
                evaluacionFisica.getId()
        );

        attributes.addFlashAttribute(
                "msg",
                "Evaluación física eliminada correctamente"
        );

        return "redirect:/evaluaciones-fisicas";
    }
}