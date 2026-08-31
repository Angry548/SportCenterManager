package com.esfe.sistemagimnasio.controllers;

import com.esfe.sistemagimnasio.models.Usuario;
import com.esfe.sistemagimnasio.services.interfaces.IRolService;
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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IRolService rolService;


    @GetMapping
    public String index(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);

        Pageable pageable = PageRequest.of(currentPage, pageSize);
        Page<Usuario> usuarios = usuarioService.obtenerTodosPaginados(pageable);

        model.addAttribute("usuarios", usuarios);

        int totalPages = usuarios.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream
                    .rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());

            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "usuario/index";
    }

    @GetMapping("/create")
    public String create(Usuario usuario, Model model) {
        model.addAttribute("roles", rolService.obtenerTodos());
        return "usuario/create";
    }

    @PostMapping("/save")
    public String save(
            @Valid Usuario usuario,
            BindingResult result,
            Model model,
            RedirectAttributes attributes) {

        if (result.hasErrors()) {
            model.addAttribute("roles", rolService.obtenerTodos());
            return "usuario/create";
        }

        usuarioService.guardar(usuario);
        attributes.addFlashAttribute("msg", "Usuario guardado correctamente");

        return "redirect:/usuarios";
    }


    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model) {
        Usuario usuario = usuarioService.obtenerPorId(id).orElseThrow();
        model.addAttribute("usuario", usuario);
        return "usuario/details";
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Usuario usuario = usuarioService.obtenerPorId(id).orElseThrow();
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", rolService.obtenerTodos());
        return "usuario/edit";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model) {
        Usuario usuario = usuarioService.obtenerPorId(id).orElseThrow();
        model.addAttribute("usuario", usuario);
        return "usuario/delete";
    }

    @PostMapping("/delete")
    public String delete(Usuario usuario, RedirectAttributes attributes) {
        usuarioService.eliminar(usuario.getId());
        attributes.addFlashAttribute("msg", "Usuario eliminado correctamente");
        return "redirect:/usuarios";
    }


    @GetMapping("/login")
    public String login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {

        if (error != null) {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
        }
        if (logout != null) {
            model.addAttribute("msg", "Ha cerrado sesión correctamente");
        }
        return "usuario/login";
    }

    @GetMapping("/change-password/{id}")
    public String showChangePasswordForm(@PathVariable("id") Integer id, Model model) {
        Usuario usuario = usuarioService.obtenerPorId(id).orElseThrow();
        model.addAttribute("usuario", usuario);
        return "usuario/change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(
            @RequestParam("id") Integer id,
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            RedirectAttributes attributes,
            Model model) {

        boolean exito = usuarioService.cambiarPassword(id, currentPassword, newPassword);

        if (!exito) {
            Usuario usuario = usuarioService.obtenerPorId(id).orElseThrow();
            model.addAttribute("usuario", usuario);
            model.addAttribute("error", "La contraseña actual es incorrecta");
            return "usuario/change-password";
        }

        attributes.addFlashAttribute("msg", "Contraseña actualizada exitosamente");
        return "redirect:/usuarios/details/" + id;
    }

    @PostMapping("/activate/{id}")
    public String activate(@PathVariable("id") Integer id, RedirectAttributes attributes) {
        usuarioService.cambiarEstado(id, true);
        attributes.addFlashAttribute("msg", "Usuario activado correctamente");
        return "redirect:/usuarios";
    }

    @PostMapping("/deactivate/{id}")
    public String deactivate(@PathVariable("id") Integer id, RedirectAttributes attributes) {
        usuarioService.cambiarEstado(id, false);
        attributes.addFlashAttribute("msg", "Usuario desactivado correctamente");
        return "redirect:/usuarios";
    }
}
