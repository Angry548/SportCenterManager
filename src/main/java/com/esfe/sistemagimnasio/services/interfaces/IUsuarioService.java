package com.esfe.sistemagimnasio.services.interfaces;

import com.esfe.sistemagimnasio.models.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {

    // Métodos de Usuario
    List<IUsuarioService> obtenerTodos();

    Optional<IUsuarioService> obtenerPorId(Integer id);

    Usuario guardar(Usuario usuario);

    void eliminar(Integer id);

    Optional<IUsuarioService> autenticar(String email, String password);

    Usuario registrarUsuario(Usuario usuario);

    // Métodos de Membresia
    List<Usuario> obtenerTodasMembresias();

    Optional<Usuario> obtenerMembresiaPorId(Integer id);

    Usuario guardarMembresia(Usuario usuario);

    void eliminarMembresia(Integer id);
}


