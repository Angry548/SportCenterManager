package com.esfe.sistemagimnasio.controllers;

import com.esfe.sistemagimnasio.enums.Rol;
import com.esfe.sistemagimnasio.models.Cliente;
import com.esfe.sistemagimnasio.models.Usuario;
import com.esfe.sistemagimnasio.services.interfaces.IClienteService;
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
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IUsuarioService usuarioService;


    @GetMapping
    public String index(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);

        Pageable pageable =
                PageRequest.of(currentPage, pageSize);

        Page<Cliente> clientes =
                clienteService.obtenerTodosPaginados(pageable);

        model.addAttribute(
                "clientes",
                clientes
        );

        int totalPages =
                clientes.getTotalPages();

        if (totalPages > 0) {

            List<Integer> pageNumbers =
                    IntStream
                            .rangeClosed(1, totalPages)
                            .boxed()
                            .collect(Collectors.toList());

            model.addAttribute(
                    "pageNumbers",
                    pageNumbers
            );
        }

        return "cliente/index";
    }


    // =====================================================
    // CREAR
    // =====================================================

    @GetMapping("/create")
    public String create(
            Cliente cliente,
            Model model) {

        model.addAttribute(
                "usuarios",
                obtenerUsuariosDisponibles(null)
        );

        return "cliente/create";
    }


    // =====================================================
    // GUARDAR
    // =====================================================

    @PostMapping("/save")
    public String save(
            @Valid Cliente cliente,
            BindingResult result,
            Model model,
            RedirectAttributes attributes) {


        // ==========================================
        // VALIDAR USUARIO
        // ==========================================

        if (cliente.getUsuario() != null &&
                cliente.getUsuario().getId() != null) {

            Integer usuarioId =
                    cliente.getUsuario().getId();

            Usuario usuario =
                    usuarioService
                            .obtenerPorId(usuarioId)
                            .orElse(null);


            // Usuario inexistente
            if (usuario == null) {

                result.rejectValue(
                        "usuario.id",
                        "usuario.noExiste",
                        "El usuario seleccionado no existe"
                );
            }

            // Rol incorrecto
            else if (usuario.getRol() != Rol.CLIENTE) {

                result.rejectValue(
                        "usuario.id",
                        "usuario.rolInvalido",
                        "El usuario seleccionado debe tener rol CLIENTE"
                );
            }

            // Usuario ya asociado a otro cliente
            else {

                boolean usuarioYaAsignado =
                        clienteService
                                .obtenerTodos()
                                .stream()
                                .anyMatch(clienteExistente ->

                                        clienteExistente.getUsuario() != null

                                                && Objects.equals(
                                                clienteExistente
                                                        .getUsuario()
                                                        .getId(),
                                                usuarioId
                                        )

                                                && !Objects.equals(
                                                clienteExistente.getId(),
                                                cliente.getId()
                                        )
                                );


                if (usuarioYaAsignado) {

                    result.rejectValue(
                            "usuario.id",
                            "usuario.yaAsignado",
                            "Este usuario ya está asociado a otro cliente"
                    );

                } else {

                    /*
                     * Usamos el Usuario real recuperado
                     * desde la base de datos.
                     */
                    cliente.setUsuario(usuario);
                }
            }
        }


        // ==========================================
        // ERRORES
        // ==========================================

        if (result.hasErrors()) {

            model.addAttribute(
                    "usuarios",
                    obtenerUsuariosDisponibles(
                            cliente.getId()
                    )
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


        // ==========================================
        // GUARDAR
        // ==========================================

        clienteService.guardar(cliente);

        attributes.addFlashAttribute(
                "msg",
                "Cliente guardado correctamente"
        );

        return "redirect:/clientes";
    }


    // =====================================================
    // DETALLES
    // =====================================================

    @GetMapping("/details/{id}")
    public String details(
            @PathVariable("id") Integer id,
            Model model) {

        Cliente cliente =
                clienteService
                        .obtenerPorId(id)
                        .orElseThrow();

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


    // =====================================================
    // EDITAR
    // =====================================================

    @GetMapping("/edit/{id}")
    public String edit(
            @PathVariable("id") Integer id,
            Model model) {

        Cliente cliente =
                clienteService
                        .obtenerPorId(id)
                        .orElseThrow();

        model.addAttribute(
                "cliente",
                cliente
        );

        /*
         * Incluye:
         *
         * - Usuarios CLIENTE que todavía no tienen perfil.
         * - El usuario actualmente asociado al cliente.
         */
        model.addAttribute(
                "usuarios",
                obtenerUsuariosDisponibles(id)
        );

        return "cliente/edit";
    }


    // =====================================================
    // ELIMINAR
    // =====================================================

    @GetMapping("/remove/{id}")
    public String remove(
            @PathVariable("id") Integer id,
            Model model) {

        Cliente cliente =
                clienteService
                        .obtenerPorId(id)
                        .orElseThrow();

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


    // =====================================================
    // USUARIOS DISPONIBLES PARA CLIENTE
    // =====================================================

    private List<Usuario> obtenerUsuariosDisponibles(
            Integer clienteActualId) {

        List<Cliente> clientesExistentes =
                clienteService.obtenerTodos();

        return usuarioService
                .obtenerTodos()
                .stream()

                // Solo usuarios CLIENTE
                .filter(usuario ->
                        usuario.getRol() == Rol.CLIENTE
                )

                /*
                 * Excluir usuarios que ya pertenecen
                 * a otro Cliente.
                 *
                 * Si estamos editando, permitimos
                 * el usuario del Cliente actual.
                 */
                .filter(usuario ->

                        clientesExistentes
                                .stream()
                                .noneMatch(cliente ->

                                        cliente.getUsuario() != null

                                                && Objects.equals(
                                                cliente
                                                        .getUsuario()
                                                        .getId(),
                                                usuario.getId()
                                        )

                                                && !Objects.equals(
                                                cliente.getId(),
                                                clienteActualId
                                        )
                                )
                )

                .toList();
    }
}