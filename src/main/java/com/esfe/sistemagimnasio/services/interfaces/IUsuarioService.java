package com.esfe.sistemagimnasio.services.interfaces;

import com.esfe.sistemagimnasio.models.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {

    List<Usuario> obtenerTodasMembresias();

    Optional<Usuario> obtenerMembresiaPorId(Integer id);

    Usuario guardarMembresia(Usuario usuario);

    void eliminarMembresia(Integer id);
}


