package com.esfe.sistemagimnasio.services.implementations;

import com.esfe.sistemagimnasio.enums.Rol;
import com.esfe.sistemagimnasio.models.Usuario;
import com.esfe.sistemagimnasio.repositories.IUsuarioRepository;
import com.esfe.sistemagimnasio.services.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }


    @Override
    public Optional<Usuario> obtenerPorId(Integer id) {
        return usuarioRepository.findById(id);
    }


    @Override
    public Usuario guardar(Usuario usuario) {

        // NUEVO USUARIO
        if (usuario.getId() == null) {

            usuario.setFechaRegistro(LocalDateTime.now());

            if (usuario.getActivo() == null) {
                usuario.setActivo(true);
            }

            usuario.setPasswordHash(
                    passwordEncoder.encode(
                            usuario.getPasswordHash()
                    )
            );

            return usuarioRepository.save(usuario);
        }


        // EDICIÓN
        Usuario usuarioExistente =
                usuarioRepository
                        .findById(usuario.getId())
                        .orElseThrow();

        usuario.setPasswordHash(
                usuarioExistente.getPasswordHash()
        );

        usuario.setFechaRegistro(
                usuarioExistente.getFechaRegistro()
        );

        return usuarioRepository.save(usuario);
    }


    @Override
    public void eliminar(Integer id) {

        usuarioRepository.deleteById(id);
    }


    @Override
    public Page<Usuario> obtenerTodosPaginados(
            Pageable pageable) {

        return usuarioRepository.findAll(pageable);
    }


    /*
     * Por ahora conservamos este método.
     * Ya compara correctamente utilizando BCrypt.
     */
    @Override
    public boolean autenticar(
            Integer id,
            String password) {

        Usuario usuario =
                obtenerPorId(id)
                        .orElseThrow();


        return passwordEncoder.matches(
                password,
                usuario.getPasswordHash()
        );
    }


    /*
     * El cambio de contraseña sí debe generar
     * un nuevo hash BCrypt.
     */
    @Override
    public void cambiarPassword(
            Integer id,
            String nuevaPassword) {

        Usuario usuario =
                obtenerPorId(id)
                        .orElseThrow();


        usuario.setPasswordHash(
                passwordEncoder.encode(
                        nuevaPassword
                )
        );


        /*
         * Aquí usamos directamente repository.save().
         *
         * NO llamamos a guardar(usuario), porque guardar()
         * conservaría el hash anterior al detectar que
         * el usuario ya tiene ID.
         */
        usuarioRepository.save(usuario);
    }


    @Override
    public void activar(Integer id) {

        Usuario usuario =
                obtenerPorId(id)
                        .orElseThrow();

        usuario.setActivo(true);

        guardar(usuario);
    }


    @Override
    public void desactivar(Integer id) {

        Usuario usuario =
                obtenerPorId(id)
                        .orElseThrow();

        usuario.setActivo(false);

        guardar(usuario);
    }

    @Override
    public List<Usuario> obtenerUsuariosDisponiblesCliente() {

        return usuarioRepository
                .obtenerUsuariosDisponiblesCliente(
                        Rol.CLIENTE
                );
    }


    @Override
    public List<Usuario> obtenerUsuariosDisponiblesEntrenador() {

        return usuarioRepository
                .obtenerUsuariosDisponiblesEntrenador(
                        Rol.ENTRENADOR
                );
    }

    @Override
    public boolean existePorEmail(String email) {
        return usuarioRepository
                .findByEmail(email)
                .isPresent();
    }

}