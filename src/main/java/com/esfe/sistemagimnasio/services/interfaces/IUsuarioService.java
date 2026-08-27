package com.esfe.sistemagimnasio.services.interfaces;

import com.esfe.sistemagimnasio.models.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {

    List<Usuario> obtenerTodos();

    Optional<Usuario> obtenerPorId(Integer id);

    Usuario guardar (Usuario usuario);

    void eliminar(Integer id);
}


