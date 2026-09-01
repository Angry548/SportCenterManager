package com.esfe.sistemagimnasio.controllers;

import com.esfe.sistemagimnasio.models.Pago;
import com.esfe.sistemagimnasio.services.interfaces.IClienteService;
import com.esfe.sistemagimnasio.services.interfaces.IMembresiaClienteService;
import com.esfe.sistemagimnasio.services.interfaces.IMetodoPagoService;
import com.esfe.sistemagimnasio.services.interfaces.IPagoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/pagos")
public class PagoController {

    @Autowired
    private IPagoService pagoService;

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IMembresiaClienteService membresiaClienteService;

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
        Page<Pago> pagos = pagoService.obtenerTodosPaginados(pageable);

        model.addAttribute("pagos", pagos);

        int totalPages = pagos.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream
                    .rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "pago/index";
    }

    @GetMapping("/create")
    public String create(@ModelAttribute("pago") Pago pago, Model model) {
        pago.setNumeroComprobante(pagoService.generarNumeroComprobante());
        cargarCatalogos(model);
        return "pago/create";
    }

    @PostMapping("/save")
    public String save(
            @Valid @ModelAttribute("pago") Pago pago,
            BindingResult result,
            Model model,
            RedirectAttributes attributes) {

        if (result.hasErrors()) {
            cargarCatalogos(model);
            model.addAttribute("error", "Por favor revise los campos requeridos.");
            return "pago/create";
        }

        // Asigna metadatos automáticos en caso de ser un nuevo pago
        if (pago.getId() == null) {
            pago.setFecha(LocalDateTime.now());
            if (pago.getNumeroComprobante() == null || pago.getNumeroComprobante().isBlank()) {
                pago.setNumeroComprobante(pagoService.generarNumeroComprobante());
            }
        }

        pagoService.guardar(pago);
        attributes.addFlashAttribute("msg", "Pago registrado correctamente");

        return "redirect:/pagos";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model, RedirectAttributes attributes) {
        Optional<Pago> pagoOpt = pagoService.obtenerPorId(id);

        if (pagoOpt.isEmpty()) {
            attributes.addFlashAttribute("error", "El pago solicitado no existe.");
            return "redirect:/pagos";
        }

        model.addAttribute("pago", pagoOpt.get());
        return "pago/details";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model, RedirectAttributes attributes) {
        Optional<Pago> pagoOpt = pagoService.obtenerPorId(id);

        if (pagoOpt.isEmpty()) {
            attributes.addFlashAttribute("error", "El pago solicitado no existe.");
            return "redirect:/pagos";
        }

        model.addAttribute("pago", pagoOpt.get());
        return "pago/delete";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute Pago pago, RedirectAttributes attributes) {
        pagoService.eliminar(pago.getId());
        attributes.addFlashAttribute("msg", "Pago eliminado correctamente");
        return "redirect:/pagos";
    }

    @GetMapping("/pdf/{id}")
    public ResponseEntity<byte[]> descargarComprobantePDF(@PathVariable("id") Integer id) {
        Optional<Pago> pagoOpt = pagoService.obtenerPorId(id);

        if (pagoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        byte[] pdfBytes = pagoService.generarComprobantePDF(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=comprobante_" + pagoOpt.get().getNumeroComprobante() + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }


    private void cargarCatalogos(Model model) {
        model.addAttribute("clientes", clienteService.obtenerTodos());
        model.addAttribute("membresiasClientes", membresiaClienteService.obtenerTodos());
        model.addAttribute("metodosPago", metodoPagoService.obtenerTodos());
    }
}