package com.esfe.sistemagimnasio.services;

import com.esfe.sistemagimnasio.models.Usuario;
import com.esfe.sistemagimnasio.repositories.IUsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService
        implements UserDetailsService {

    private final IUsuarioRepository usuarioRepository;

    public CustomUserDetailsService(
            IUsuarioRepository usuarioRepository) {

        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Usuario usuario =
                usuarioRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new UsernameNotFoundException(
                                        "Usuario no encontrado"
                                )
                        );

        return User
                .withUsername(usuario.getEmail())
                .password(usuario.getPasswordHash())

                // Suponiendo que tu enum tiene:
                // ADMIN, CLIENTE, ENTRENADOR
                .authorities(
                        usuario.getRol().name()
                )

                .disabled(
                        !Boolean.TRUE.equals(
                                usuario.getActivo()
                        )
                )

                .build();
    }
}