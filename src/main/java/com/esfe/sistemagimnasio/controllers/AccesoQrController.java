package com.esfe.sistemagimnasio.controllers;

import com.esfe.sistemagimnasio.enums.Resultado;
import com.esfe.sistemagimnasio.models.Asistencia;
import com.esfe.sistemagimnasio.services.interfaces.IAsistenciaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/asistencias/qr")
public class AccesoQrController {

    private final IAsistenciaService asistenciaService;


    public AccesoQrController(
            IAsistenciaService asistenciaService) {

        this.asistenciaService =
                asistenciaService;
    }


    // =====================================================
    // ESCÁNER
    // =====================================================

    @GetMapping
    public String scanner() {

        return "asistencia/qr";
    }


    // =====================================================
    // REGISTRAR ACCESO
    // =====================================================

    @PostMapping("/registrar")
    public String registrar(
            @RequestParam("codigoQr")
            String codigoQr,
            RedirectAttributes attributes) {

        try {

            Asistencia asistencia =
                    asistenciaService
                            .registrarAccesoPorQr(
                                    codigoQr.trim()
                            );


            if (asistencia.getResultado()
                    == Resultado.PERMITIDO) {

                attributes.addFlashAttribute(
                        "msg",
                        "Acceso autorizado. Asistencia registrada correctamente."
                );

            } else {

                attributes.addFlashAttribute(
                        "error",
                        "Acceso rechazado. El cliente no tiene una membresía vigente."
                );
            }

        } catch (IllegalArgumentException exception) {

            attributes.addFlashAttribute(
                    "error",
                    "Código QR no válido o cliente no encontrado."
            );
        }


        return "redirect:/asistencias/qr";
    }
}