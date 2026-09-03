package com.esfe.sistemagimnasio.controllers;

import com.esfe.sistemagimnasio.enums.Sexo;
import com.esfe.sistemagimnasio.models.forms.RegistroClienteForm;
import com.esfe.sistemagimnasio.services.implementations.RegistroClienteService;
import com.esfe.sistemagimnasio.services.interfaces.IClienteService;
import com.esfe.sistemagimnasio.services.interfaces.IUsuarioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistroClienteController {

    private final RegistroClienteService registroClienteService;
    private final IUsuarioService usuarioService;
    private final IClienteService clienteService;

    public RegistroClienteController(
            RegistroClienteService registroClienteService,
            IUsuarioService usuarioService,
            IClienteService clienteService) {

        this.registroClienteService = registroClienteService;
        this.usuarioService = usuarioService;
        this.clienteService = clienteService;
    }


    @GetMapping("/registro")
    public String registro(Model model) {

        model.addAttribute(
                "registro",
                new RegistroClienteForm()
        );

        model.addAttribute(
                "sexos",
                Sexo.values()
        );

        return "cliente/registro";
    }


    @PostMapping("/registro")
    public String registrar(
            @Valid
            @ModelAttribute("registro")
            RegistroClienteForm registro,

            BindingResult result,
            Model model,
            RedirectAttributes attributes) {


        String email =
                registro.getEmail() != null
                        ? registro.getEmail()
                        .trim()
                        .toLowerCase()
                        : null;


        // ==========================================
        // CORREO DUPLICADO
        // ==========================================

        if (email != null
                && usuarioService.existePorEmail(email)) {

            result.rejectValue(
                    "email",
                    "email.duplicado",
                    "El correo electrónico ya está registrado"
            );
        }


        // ==========================================
        // DUI DUPLICADO
        // ==========================================

        if (registro.getDui() != null
                && clienteService.existePorDui(
                registro.getDui()
        )) {

            result.rejectValue(
                    "dui",
                    "dui.duplicado",
                    "El DUI ya está registrado"
            );
        }


        // ==========================================
        // ERRORES
        // ==========================================

        if (result.hasErrors()) {

            model.addAttribute(
                    "sexos",
                    Sexo.values()
            );

            return "cliente/registro";
        }


        registro.setEmail(email);

        registroClienteService.registrar(
                registro
        );


        attributes.addFlashAttribute(
                "msg",
                "Cuenta creada correctamente. Ya puedes iniciar sesión."
        );


        return "redirect:/usuarios/login";
    }
}