package com.esfe.sistemagimnasio.controllers;

import com.esfe.sistemagimnasio.models.Cliente;
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
@RequestMapping("/clientes")
public class ClienteController {

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

        Page<Cliente> clientes =
                clienteService.obtenerTodosPaginados(pageable);

        model.addAttribute("clientes", clientes);

        int totalPages = clientes.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream
                    .rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());

            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "cliente/index";
    }

    @GetMapping("/create")
    public String create(
            Cliente cliente,
            Model model) {

        model.addAttribute(
                "entrenadores",
                entrenadorService.obtenerTodos()
        );

        return "cliente/create";
    }

    @PostMapping("/save")
    public String save(
            @Valid Cliente cliente,
            BindingResult result,
            Model model,
            RedirectAttributes attributes) {

        if (result.hasErrors()) {

            model.addAttribute(
                    "entrenadores",
                    entrenadorService.obtenerTodos()
            );

            model.addAttribute(
                    "error",
                    "No se pudo guardar debido a un error de validación."
            );

            if (cliente.getId() != null) {
                return "cliente/edit";
            }

            return "cliente/create";
        }

        clienteService.guardar(cliente);

        attributes.addFlashAttribute(
                "msg",
                "Cliente guardado correctamente"
        );

        return "redirect:/clientes";
    }

    @GetMapping("/details/{id}")
    public String details(
            @PathVariable("id") Integer id,
            Model model) {

        Cliente cliente =
                clienteService.obtenerPorId(id).orElseThrow();

        model.addAttribute(
                "cliente",
                cliente
        );

        model.addAttribute(
                "edad",
                clienteService.obtenerEdad(id)
        );

        model.addAttribute(
                "membresiaVigente",
                clienteService.tieneMembresiaVigente(id)
        );

        model.addAttribute(
                "entrenadorActivo",
                clienteService.tieneEntrenadorActivo(id)
        );

        return "cliente/details";
    }

    @GetMapping("/edit/{id}")
    public String edit(
            @PathVariable("id") Integer id,
            Model model) {

        Cliente cliente =
                clienteService.obtenerPorId(id).orElseThrow();

        model.addAttribute(
                "cliente",
                cliente
        );

        model.addAttribute(
                "entrenadores",
                entrenadorService.obtenerTodos()
        );

        return "cliente/edit";
    }

    @GetMapping("/remove/{id}")
    public String remove(
            @PathVariable("id") Integer id,
            Model model) {

        Cliente cliente =
                clienteService.obtenerPorId(id).orElseThrow();

        model.addAttribute(
                "cliente",
                cliente
        );

        return "cliente/delete";
    }

    @PostMapping("/delete")
    public String delete(
            Cliente cliente,
            RedirectAttributes attributes) {

        clienteService.eliminar(
                cliente.getId()
        );

        attributes.addFlashAttribute(
                "msg",
                "Cliente eliminado correctamente"
        );

        return "redirect:/clientes";
    }
}