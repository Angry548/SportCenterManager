package com.esfe.sistemagimnasio.controllers;

import com.esfe.sistemagimnasio.models.TipoMembresia;
import com.esfe.sistemagimnasio.services.interfaces.ITipoMembresiaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/tipos-membresia")
public class TipoMembresiaController {

    @Autowired
    private ITipoMembresiaService tipoMembresiaService;

    // 1. LISTADO PAGINADO
    @GetMapping
    public String index(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);

        Pageable pageable = PageRequest.of(currentPage, pageSize);
        Page<TipoMembresia> tiposMembresia = tipoMembresiaService.obtenerTodosPaginados(pageable);

        model.addAttribute("tiposMembresia", tiposMembresia);

        int totalPages = tiposMembresia.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream
                    .rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());

            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "tipoMembresia/index";
    }

    // 2. CREACIÓN (Formulario)
    @GetMapping("/create")
    public String create(TipoMembresia tipoMembresia, Model model) {
        return "tipoMembresia/create";
    }

    // 2. GUARDADO
    @PostMapping("/save")
    public String save(
            @Valid TipoMembresia tipoMembresia,
            BindingResult result,
            Model model,
            RedirectAttributes attributes) {

        if (result.hasErrors()) {
            model.addAttribute("error", "No se pudo guardar debido a errores de validación.");
            return "tipoMembresia/create";
        }

        tipoMembresiaService.guardar(tipoMembresia);
        attributes.addFlashAttribute("msg", "Tipo de membresía guardado correctamente");

        return "redirect:/tipos-membresia";
    }

    // 3. VISTA DE DETALLES
    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model) {
        TipoMembresia tipoMembresia = tipoMembresiaService.obtenerPorId(id).orElseThrow();

        model.addAttribute("tipoMembresia", tipoMembresia);

        // Cálculo proyectado de fecha de vencimiento partiendo del día de hoy
        LocalDate fechaVencimientoProyectada = tipoMembresiaService.calcularFechaVencimiento(id, LocalDate.now());
        model.addAttribute("fechaVencimientoProyectada", fechaVencimientoProyectada);

        return "tipoMembresia/details";
    }

    // 4. EDICIÓN (Formulario)
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        TipoMembresia tipoMembresia = tipoMembresiaService.obtenerPorId(id).orElseThrow();

        model.addAttribute("tipoMembresia", tipoMembresia);
        return "tipoMembresia/edit";
    }

    // 5. ELIMINACIÓN (Vista de Confirmación)
    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model) {
        TipoMembresia tipoMembresia = tipoMembresiaService.obtenerPorId(id).orElseThrow();

        model.addAttribute("tipoMembresia", tipoMembresia);
        return "tipoMembresia/delete";
    }

    // 5. ELIMINACIÓN (Acción)
    @PostMapping("/delete")
    public String delete(TipoMembresia tipoMembresia, RedirectAttributes attributes) {
        tipoMembresiaService.eliminar(tipoMembresia.getId());
        attributes.addFlashAttribute("msg", "Tipo de membresía eliminado correctamente");

        return "redirect:/tipos-membresia";
    }

    // 6. CÁLCULO DE FECHA DE VENCIMIENTO (Consulta dinámica)
    @GetMapping("/calcular-vencimiento/{id}")
    public String calcularFechaVencimiento(
            @PathVariable("id") Integer id,
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            Model model) {

        TipoMembresia tipoMembresia = tipoMembresiaService.obtenerPorId(id).orElseThrow();
        LocalDate fechaVencimiento = tipoMembresiaService.calcularFechaVencimiento(id, fechaInicio);

        model.addAttribute("tipoMembresia", tipoMembresia);
        model.addAttribute("fechaInicio", fechaInicio);
        model.addAttribute("fechaVencimiento", fechaVencimiento);

        return "tipoMembresia/vencimiento-resultado";
    }

    // 7. ACTIVACIÓN DEL TIPO DE MEMBRESÍA
    @PostMapping("/activate/{id}")
    public String activate(@PathVariable("id") Integer id, RedirectAttributes attributes) {
        tipoMembresiaService.cambiarEstado(id, true);
        attributes.addFlashAttribute("msg", "Tipo de membresía activado correctamente");
        return "redirect:/tipos-membresia";
    }

    // 8. DESACTIVACIÓN DEL TIPO DE MEMBRESÍA
    @PostMapping("/deactivate/{id}")
    public String deactivate(@PathVariable("id") Integer id, RedirectAttributes attributes) {
        tipoMembresiaService.cambiarEstado(id, false);
        attributes.addFlashAttribute("msg", "Tipo de membresía desactivado correctamente");
        return "redirect:/tipos-membresia";
    }
}