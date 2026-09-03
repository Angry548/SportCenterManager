package com.esfe.sistemagimnasio.controllers;

import com.esfe.sistemagimnasio.models.Cliente;
import com.esfe.sistemagimnasio.models.MetodoPago;
import com.esfe.sistemagimnasio.models.Pago;
import com.esfe.sistemagimnasio.models.TipoMembresia;
import com.esfe.sistemagimnasio.services.implementations.CompraMembresiaService;
import com.esfe.sistemagimnasio.services.interfaces.IClienteService;
import com.esfe.sistemagimnasio.services.interfaces.IMembresiaClienteService;
import com.esfe.sistemagimnasio.services.interfaces.IMetodoPagoService;
import com.esfe.sistemagimnasio.services.interfaces.ITipoMembresiaService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/mi-membresia")
public class MiMembresiaController {

    private final IClienteService clienteService;
    private final IMembresiaClienteService membresiaClienteService;
    private final ITipoMembresiaService tipoMembresiaService;
    private final IMetodoPagoService metodoPagoService;
    private final CompraMembresiaService compraMembresiaService;


    public MiMembresiaController(
            IClienteService clienteService,
            IMembresiaClienteService membresiaClienteService,
            ITipoMembresiaService tipoMembresiaService,
            IMetodoPagoService metodoPagoService,
            CompraMembresiaService compraMembresiaService) {

        this.clienteService = clienteService;
        this.membresiaClienteService = membresiaClienteService;
        this.tipoMembresiaService = tipoMembresiaService;
        this.metodoPagoService = metodoPagoService;
        this.compraMembresiaService = compraMembresiaService;
    }


    // =====================================================
    // PLANES DISPONIBLES
    // =====================================================

    @GetMapping("/planes")
    public String planes(
            Authentication authentication,
            Model model) {


        Cliente cliente =
                obtenerClienteActual(authentication);


        boolean tieneMembresia =
                membresiaClienteService
                        .obtenerMembresiaVigenteCliente(
                                cliente.getId()
                        )
                        .isPresent();


        // =================================================
        // SOLO PLANES ACTIVOS
        // =================================================

        List<TipoMembresia> planes =
                tipoMembresiaService
                        .obtenerTodos()
                        .stream()
                        .filter(tipo ->
                                Boolean.TRUE.equals(
                                        tipo.getActivo()
                                )
                        )
                        .toList();


        // =================================================
        // SOLO MÉTODOS DE PAGO ACTIVOS
        // =================================================

        List<MetodoPago> metodosPago =
                metodoPagoService
                        .obtenerTodos()
                        .stream()
                        .filter(metodo ->
                                Boolean.TRUE.equals(
                                        metodo.getActivo()
                                )
                        )
                        .toList();


        model.addAttribute(
                "cliente",
                cliente
        );

        model.addAttribute(
                "planes",
                planes
        );

        model.addAttribute(
                "metodosPago",
                metodosPago
        );

        model.addAttribute(
                "tieneMembresia",
                tieneMembresia
        );


        return "cliente/planesMembresia";
    }


    // =====================================================
    // ADQUIRIR
    // =====================================================

    @PostMapping("/adquirir")
    public String adquirir(
            @RequestParam("tipoMembresiaId")
            Integer tipoMembresiaId,

            @RequestParam("metodoPagoId")
            Integer metodoPagoId,

            Authentication authentication,
            RedirectAttributes attributes) {


        try {

            Pago pago =
                    compraMembresiaService
                            .adquirir(
                                    authentication.getName(),
                                    tipoMembresiaId,
                                    metodoPagoId
                            );


            attributes.addFlashAttribute(
                    "msg",
                    "Membresía adquirida correctamente. " +
                            "Comprobante: " +
                            pago.getNumeroComprobante()
            );


            return "redirect:/home";


        } catch (IllegalArgumentException |
                 IllegalStateException exception) {


            attributes.addFlashAttribute(
                    "error",
                    exception.getMessage()
            );


            return "redirect:/mi-membresia/planes";
        }
    }


    // =====================================================
    // CLIENTE ACTUAL
    // =====================================================

    private Cliente obtenerClienteActual(
            Authentication authentication) {

        return clienteService
                .obtenerPorUsuarioEmail(
                        authentication.getName()
                )
                .orElseThrow(() ->
                        new AccessDeniedException(
                                "No existe un perfil de cliente asociado a esta cuenta."
                        )
                );
    }
}