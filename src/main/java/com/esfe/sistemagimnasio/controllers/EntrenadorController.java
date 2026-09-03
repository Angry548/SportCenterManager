package com.esfe.sistemagimnasio.controllers;

import com.esfe.sistemagimnasio.enums.Rol;
import com.esfe.sistemagimnasio.models.Entrenador;
import com.esfe.sistemagimnasio.models.Usuario;
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
import java.util.Objects;
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

        int currentPage =
                page.orElse(1) - 1;

        int pageSize =
                size.orElse(5);

        Pageable pageable =
                PageRequest.of(
                        currentPage,
                        pageSize
                );

        Page<Entrenador> entrenadores =
                entrenadorService
                        .obtenerTodosPaginados(pageable);

        model.addAttribute(
                "entrenadores",
                entrenadores
        );

        int totalPages =
                entrenadores.getTotalPages();

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

        return "entrenador/index";
    }


    // =====================================================
    // CREAR
    // =====================================================

    @GetMapping("/create")
    public String create(
            Entrenador entrenador,
            Model model) {

        model.addAttribute(
                "usuarios",
                obtenerUsuariosDisponibles(null)
        );

        return "entrenador/create";
    }


    // =====================================================
    // GUARDAR
    // =====================================================

    @PostMapping("/save")
    public String save(
            @Valid Entrenador entrenador,
            BindingResult result,
            Model model,
            RedirectAttributes attributes) {


        // ==========================================
        // VALIDAR USUARIO
        // ==========================================

        if (entrenador.getUsuario() != null &&
                entrenador.getUsuario().getId() != null) {

            Integer usuarioId =
                    entrenador
                            .getUsuario()
                            .getId();

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
            else if (usuario.getRol() != Rol.ENTRENADOR) {

                result.rejectValue(
                        "usuario.id",
                        "usuario.rolInvalido",
                        "El usuario seleccionado debe tener rol ENTRENADOR"
                );
            }

            // Usuario ya asociado a otro entrenador
            else {

                boolean usuarioYaAsignado =
                        entrenadorService
                                .obtenerTodos()
                                .stream()
                                .anyMatch(entrenadorExistente ->

                                        entrenadorExistente.getUsuario() != null

                                                && Objects.equals(
                                                entrenadorExistente
                                                        .getUsuario()
                                                        .getId(),
                                                usuarioId
                                        )

                                                && !Objects.equals(
                                                entrenadorExistente.getId(),
                                                entrenador.getId()
                                        )
                                );


                if (usuarioYaAsignado) {

                    result.rejectValue(
                            "usuario.id",
                            "usuario.yaAsignado",
                            "Este usuario ya está asociado a otro entrenador"
                    );

                } else {

                    /*
                     * Usamos la entidad Usuario
                     * recuperada de la BD.
                     */
                    entrenador.setUsuario(usuario);
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
                            entrenador.getId()
                    )
            );

            model.addAttribute(
                    "error",
                    "No se pudo guardar debido a un error de validación."
            );

            if (entrenador.getId() != null) {
                return "entrenador/edit";
            }

            return "entrenador/create";
        }


        // ==========================================
        // GUARDAR
        // ==========================================

        entrenadorService.guardar(entrenador);

        attributes.addFlashAttribute(
                "msg",
                "Entrenador guardado correctamente"
        );

        return "redirect:/entrenadores";
    }


    // =====================================================
    // DETALLES
    // =====================================================

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
                entrenadorService
                        .obtenerClientesActivos(id)
        );

        return "entrenador/details";
    }


    // =====================================================
    // EDITAR
    // =====================================================

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

        /*
         * Incluye:
         *
         * - Usuarios ENTRENADOR disponibles.
         * - El usuario actualmente asociado
         *   a este entrenador.
         */
        model.addAttribute(
                "usuarios",
                obtenerUsuariosDisponibles(id)
        );

        return "entrenador/edit";
    }


    // =====================================================
    // ELIMINAR
    // =====================================================

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


    // =====================================================
    // USUARIOS DISPONIBLES PARA ENTRENADOR
    // =====================================================

    private List<Usuario> obtenerUsuariosDisponibles(
            Integer entrenadorActualId) {

        List<Entrenador> entrenadoresExistentes =
                entrenadorService.obtenerTodos();

        return usuarioService
                .obtenerTodos()
                .stream()

                // Solo usuarios ENTRENADOR
                .filter(usuario ->
                        usuario.getRol() == Rol.ENTRENADOR
                )

                /*
                 * Excluir usuarios que ya pertenecen
                 * a otro entrenador.
                 *
                 * En edición permitimos el usuario
                 * del entrenador actual.
                 */
                .filter(usuario ->

                        entrenadoresExistentes
                                .stream()
                                .noneMatch(entrenador ->

                                        entrenador.getUsuario() != null

                                                && Objects.equals(
                                                entrenador
                                                        .getUsuario()
                                                        .getId(),
                                                usuario.getId()
                                        )

                                                && !Objects.equals(
                                                entrenador.getId(),
                                                entrenadorActualId
                                        )
                                )
                )

                .toList();
    }
}