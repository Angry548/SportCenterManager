package com.esfe.sistemagimnasio.controllers;

import com.esfe.sistemagimnasio.models.MembresiaCliente;
import com.esfe.sistemagimnasio.services.interfaces.IClienteService;
import com.esfe.sistemagimnasio.services.interfaces.IMembresiaClienteService;
import com.esfe.sistemagimnasio.services.interfaces.ITipoMembresiaService;
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
@RequestMapping("/membresias-clientes")
public class MembresiaClienteController {

    @Autowired
    private IMembresiaClienteService membresiaClienteService;

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private ITipoMembresiaService tipoMembresiaService;

    @GetMapping
    public String index(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);

        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<MembresiaCliente> membresias =
                membresiaClienteService.obtenerTodosPaginados(pageable);

        model.addAttribute("membresias", membresias);

        int totalPages = membresias.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream
                    .rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());

            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "membresiaCliente/index";
    }

    @GetMapping("/create")
    public String create(
            MembresiaCliente membresiaCliente,
            Model model) {

        model.addAttribute(
                "clientes",
                clienteService.obtenerTodos()
        );

        model.addAttribute(
                "tiposMembresia",
                tipoMembresiaService.obtenerTodos()
        );

        return "membresiaCliente/create";
    }

    @PostMapping("/save")
    public String save(
            @Valid MembresiaCliente membresiaCliente,
            BindingResult result,
            Model model,
            RedirectAttributes attributes) {

        if (result.hasErrors()) {

            model.addAttribute(
                    "clientes",
                    clienteService.obtenerTodos()
            );

            model.addAttribute(
                    "tiposMembresia",
                    tipoMembresiaService.obtenerTodos()
            );

            attributes.addFlashAttribute(
                    "error",
                    "No se pudo guardar debido a un error."
            );

            return "membresiaCliente/create";
        }

        membresiaClienteService.guardar(membresiaCliente);

        attributes.addFlashAttribute(
                "msg",
                "Membresía creada correctamente"
        );

        return "redirect:/membresias-clientes";
    }

    @GetMapping("/details/{id}")
    public String details(
            @PathVariable("id") Integer id,
            Model model) {

        MembresiaCliente membresia =
                membresiaClienteService
                        .obtenerPorId(id)
                        .orElseThrow();

        model.addAttribute(
                "membresiaCliente",
                membresia
        );

        model.addAttribute(
                "vigente",
                membresiaClienteService.estaVigente(id)
        );

        model.addAttribute(
                "diasRestantes",
                membresiaClienteService.diasRestantes(id)
        );

        return "membresiaCliente/details";
    }

    @GetMapping("/edit/{id}")
    public String edit(
            @PathVariable("id") Integer id,
            Model model) {

        MembresiaCliente membresia =
                membresiaClienteService
                        .obtenerPorId(id)
                        .orElseThrow();

        model.addAttribute(
                "membresiaCliente",
                membresia
        );

        model.addAttribute(
                "clientes",
                clienteService.obtenerTodos()
        );

        model.addAttribute(
                "tiposMembresia",
                tipoMembresiaService.obtenerTodos()
        );

        return "membresiaCliente/edit";
    }

    @GetMapping("/remove/{id}")
    public String remove(
            @PathVariable("id") Integer id,
            Model model) {

        MembresiaCliente membresia =
                membresiaClienteService
                        .obtenerPorId(id)
                        .orElseThrow();

        model.addAttribute(
                "membresiaCliente",
                membresia
        );

        return "membresiaCliente/delete";
    }

    @PostMapping("/delete")
    public String delete(
            MembresiaCliente membresiaCliente,
            RedirectAttributes attributes) {

        membresiaClienteService.eliminar(
                membresiaCliente.getId()
        );

        attributes.addFlashAttribute(
                "msg",
                "Membresía eliminada correctamente"
        );

        return "redirect:/membresias-clientes";
    }
}