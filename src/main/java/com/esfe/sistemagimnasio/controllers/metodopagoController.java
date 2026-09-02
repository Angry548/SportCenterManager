package com.esfe.sistemagimnasio.controllers;

import com.esfe.sistemagimnasio.models.MetodoPago;
import com.esfe.sistemagimnasio.services.interfaces.IMetodoPagoService;
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
@RequestMapping("/metodos-pago")
public class metodopagoController {

    @Autowired
    private IMetodoPagoService metodoPagoService;

    @GetMapping
    public String index(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);

        Pageable pageable = PageRequest.of(currentPage, pageSize);
        Page<MetodoPago> metodosPago = metodoPagoService.obtenerTodosPaginados(pageable);

        model.addAttribute("metodosPago", metodosPago);

        int totalPages = metodosPago.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream
                    .rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "metodoPago/index";
    }

    @GetMapping("/create")
    public String create(@ModelAttribute("metodoPago") MetodoPago metodoPago) {
        return "metodoPago/create";
    }

    @PostMapping("/save")
    public String save(
            @Valid @ModelAttribute("metodoPago") MetodoPago metodoPago,
            BindingResult result,
            Model model,
            RedirectAttributes attributes) {

        if (result.hasErrors()) {
            model.addAttribute("error", "Por favor corrija los errores en el formulario.");
            return metodoPago.getId() == null ? "metodoPago/create" : "metodoPago/edit";
        }

        metodoPagoService.guardar(metodoPago);
        attributes.addFlashAttribute("msg", "Método de pago guardado correctamente.");

        return "redirect:/metodos-pago";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model, RedirectAttributes attributes) {
        Optional<MetodoPago> metodoOpt = metodoPagoService.obtenerPorId(id);

        if (metodoOpt.isEmpty()) {
            attributes.addFlashAttribute("error", "El método de pago no existe.");
            return "redirect:/metodos-pago";
        }

        model.addAttribute("metodoPago", metodoOpt.get());
        return "metodoPago/details";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model, RedirectAttributes attributes) {
        Optional<MetodoPago> metodoOpt = metodoPagoService.obtenerPorId(id);

        if (metodoOpt.isEmpty()) {
            attributes.addFlashAttribute("error", "El método de pago no existe.");
            return "redirect:/metodos-pago";
        }

        model.addAttribute("metodoPago", metodoOpt.get());
        return "metodoPago/edit";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model, RedirectAttributes attributes) {
        Optional<MetodoPago> metodoOpt = metodoPagoService.obtenerPorId(id);

        if (metodoOpt.isEmpty()) {
            attributes.addFlashAttribute("error", "El método de pago no existe.");
            return "redirect:/metodos-pago";
        }

        model.addAttribute("metodoPago", metodoOpt.get());
        return "metodoPago/delete";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute MetodoPago metodoPago, RedirectAttributes attributes) {
        metodoPagoService.eliminar(metodoPago.getId());
        attributes.addFlashAttribute("msg", "Método de pago eliminado correctamente.");

        return "redirect:/metodos-pago";
    }

    @GetMapping("/activar/{id}")
    public String activar(@PathVariable("id") Integer id, RedirectAttributes attributes) {
        metodoPagoService.activar(id);
        attributes.addFlashAttribute("msg", "Método de pago activado correctamente.");
        return "redirect:/metodos-pago";
    }

    @GetMapping("/desactivar/{id}")
    public String desactivar(@PathVariable("id") Integer id, RedirectAttributes attributes) {
        metodoPagoService.desactivar(id);
        attributes.addFlashAttribute("msg", "Método de pago desactivado correctamente.");
        return "redirect:/metodos-pago";
    }
}