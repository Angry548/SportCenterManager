package com.esfe.sistemagimnasio.controllers;

import com.esfe.sistemagimnasio.models.Entrenador;
import com.esfe.sistemagimnasio.services.interfaces.IEntrenadorService;
import com.esfe.sistemagimnasio.services.interfaces.IUsuarioService;
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
    @RequestMapping("/entrenadores")
    public class EntrenadorController {

        @Autowired
        private IEntrenadorService entrenadorService;

        @Autowired
        private IUsuarioService usuarioService;

        @GetMapping
        public String index(
                Model model,
                @RequestParam("page") Optional<Integer> page,
                @RequestParam("size") Optional<Integer> size) {

            int currentPage = page.orElse(1) - 1;
            int pageSize = size.orElse(5);

            Pageable pageable = PageRequest.of(currentPage, pageSize);

            Page<Entrenador> entrenadores =
                    entrenadorService.obtenerTodosPaginados(pageable);

            model.addAttribute("entrenadores", entrenadores);

            int totalPages = entrenadores.getTotalPages();

            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream
                        .rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());

                model.addAttribute("pageNumbers", pageNumbers);
            }

            return "entrenador/index";
        }

        @GetMapping("/create")
        public String create(
                Entrenador entrenador,
                Model model) {

            model.addAttribute(
                    "usuarios",
                    usuarioService.obtenerTodos()
            );

            return "entrenador/create";
        }

        @PostMapping("/save")
        public String save(
                @Valid Entrenador entrenador,
                BindingResult result,
                Model model,
                RedirectAttributes attributes) {

            if (result.hasErrors()) {

                model.addAttribute(
                        "usuarios",
                        usuarioService.obtenerTodos()
                );

                attributes.addFlashAttribute(
                        "error",
                        "No se pudo guardar debido a un error."
                );

                return "entrenador/create";
            }

            entrenadorService.guardar(entrenador);

            attributes.addFlashAttribute(
                    "msg",
                    "Entrenador guardado correctamente"
            );

            return "redirect:/entrenadores";
        }

        @GetMapping("/details/{id}")
        public String details(
                @PathVariable("id") Integer id,
                Model model) {

            Entrenador entrenador =
                    entrenadorService
                            .obtenerPorId(id)
                            .orElseThrow();

            model.addAttribute(
                    "entrenador",
                    entrenador
            );

            model.addAttribute(
                    "clientesActivos",
                    entrenadorService.obtenerClientesActivos(id)
            );

            return "entrenador/details";
        }

        @GetMapping("/edit/{id}")
        public String edit(
                @PathVariable("id") Integer id,
                Model model) {

            Entrenador entrenador =
                    entrenadorService
                            .obtenerPorId(id)
                            .orElseThrow();

            model.addAttribute(
                    "entrenador",
                    entrenador
            );

            model.addAttribute(
                    "usuarios",
                    usuarioService.obtenerTodos()
            );

            return "entrenador/edit";
        }

        @GetMapping("/remove/{id}")
        public String remove(
                @PathVariable("id") Integer id,
                Model model) {

            Entrenador entrenador =
                    entrenadorService
                            .obtenerPorId(id)
                            .orElseThrow();

            model.addAttribute(
                    "entrenador",
                    entrenador
            );

            return "entrenador/delete";
        }

        @PostMapping("/delete")
        public String delete(
                Entrenador entrenador,
                RedirectAttributes attributes) {

            entrenadorService.eliminar(
                    entrenador.getId()
            );

            attributes.addFlashAttribute(
                    "msg",
                    "Entrenador eliminado correctamente"
            );

            return "redirect:/entrenadores";
        }
    }

