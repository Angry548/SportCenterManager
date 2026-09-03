package com.esfe.sistemagimnasio.services.implementations;

import com.esfe.sistemagimnasio.enums.Rol;
import com.esfe.sistemagimnasio.models.Cliente;
import com.esfe.sistemagimnasio.models.Usuario;
import com.esfe.sistemagimnasio.models.forms.RegistroClienteForm;
import com.esfe.sistemagimnasio.services.interfaces.IClienteService;
import com.esfe.sistemagimnasio.services.interfaces.IUsuarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistroClienteService {

    private final IUsuarioService usuarioService;
    private final IClienteService clienteService;

    public RegistroClienteService(
            IUsuarioService usuarioService,
            IClienteService clienteService) {

        this.usuarioService = usuarioService;
        this.clienteService = clienteService;
    }


    @Transactional
    public void registrar(RegistroClienteForm form) {

        // ==========================================
        // CREAR USUARIO
        // ==========================================

        Usuario usuario = new Usuario();

        usuario.setEmail(
                form.getEmail()
                        .trim()
                        .toLowerCase()
        );

        usuario.setPasswordHash(
                form.getPassword()
        );

        usuario.setRol(
                Rol.CLIENTE
        );

        usuario.setActivo(true);


        /*
         * UsuarioService ya se encarga de:
         *
         * - BCrypt
         * - fechaRegistro
         */
        Usuario usuarioGuardado =
                usuarioService.guardar(usuario);


        // ==========================================
        // CREAR PERFIL CLIENTE
        // ==========================================

        Cliente cliente =
                new Cliente();

        cliente.setUsuario(
                usuarioGuardado
        );

        cliente.setNombres(
                form.getNombres().trim()
        );

        cliente.setApellidos(
                form.getApellidos().trim()
        );

        cliente.setDui(
                form.getDui()
        );

        cliente.setFechaNacimiento(
                form.getFechaNacimiento()
        );

        cliente.setSexo(
                form.getSexo()
        );

        cliente.setTelefono(
                form.getTelefono()
        );

        cliente.setDireccion(
                form.getDireccion().trim()
        );


        clienteService.guardar(cliente);
    }
}