package com.esfe.sistemagimnasio.controllers;

import com.esfe.sistemagimnasio.models.GrupoMuscular;
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
@RequestMapping("/grupos-musculares")
public class GrupoMuscularController {

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

        Page<GrupoMuscular> gruposMusculares =
                grupoMuscularService.obtenerTodosPaginados(pageable);

        model.addAttribute("gruposMusculares", gruposMusculares);

        int totalPages = gruposMusculares.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream
                    .rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());

            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "grupoMuscular/index";
    }

    @GetMapping("/create")
    public String create(GrupoMuscular grupoMuscular) {
        return "grupoMuscular/create";
    }

    @PostMapping("/save")
    public String save(
            @Valid GrupoMuscular grupoMuscular,
            BindingResult result,
            Model model,
            RedirectAttributes attributes) {

        if (result.hasErrors()) {

            model.addAttribute(
                    "grupoMuscular",
                    grupoMuscular
            );

            attributes.addFlashAttribute(
                    "error",
                    "No se pudo guardar debido a un error."
            );

            if (grupoMuscular.getId() != null) {
                return "grupoMuscular/edit";
            }

            return "grupoMuscular/create";
        }

        grupoMuscularService.guardar(grupoMuscular);

        attributes.addFlashAttribute(
                "msg",
                "Grupo muscular guardado correctamente"
        );

        return "redirect:/grupos-musculares";
    }

    @GetMapping("/details/{id}")
    public String details(
            @PathVariable("id") Integer id,
            Model model) {

        GrupoMuscular grupoMuscular =
                grupoMuscularService
                        .obtenerPorId(id)
                        .orElseThrow();

        model.addAttribute(
                "grupoMuscular",
                grupoMuscular
        );

        return "grupoMuscular/details";
    }

    @GetMapping("/edit/{id}")
    public String edit(
            @PathVariable("id") Integer id,
            Model model) {

        GrupoMuscular grupoMuscular =
                grupoMuscularService
                        .obtenerPorId(id)
                        .orElseThrow();

        model.addAttribute(
                "grupoMuscular",
                grupoMuscular
        );

        return "grupoMuscular/edit";
    }

    @GetMapping("/remove/{id}")
    public String remove(
            @PathVariable("id") Integer id,
            Model model) {

        GrupoMuscular grupoMuscular =
                grupoMuscularService
                        .obtenerPorId(id)
                        .orElseThrow();

        model.addAttribute(
                "grupoMuscular",
                grupoMuscular
        );

        return "grupoMuscular/delete";
    }

    @PostMapping("/delete")
    public String delete(
            GrupoMuscular grupoMuscular,
            RedirectAttributes attributes) {

        grupoMuscularService.eliminar(
                grupoMuscular.getId()
        );

        attributes.addFlashAttribute(
                "msg",
                "Grupo muscular eliminado correctamente"
        );

        return "redirect:/grupos-musculares";
    }
}