package com.esfe.sistemagimnasio.services.implementations;

import com.esfe.sistemagimnasio.models.Usuario;
import com.esfe.sistemagimnasio.services.interfaces.IClienteService;
import com.esfe.sistemagimnasio.services.interfaces.IUsuarioService;

import java.util.List;
import java.util.Optional;

public class UsuarioService implements IUsuarioService {

    @Override
    public List<Usuario> obtenerTodasMembresias() {
        return List.of();
    }

    @Override
    public Optional<Usuario> obtenerMembresiaPorId(Integer id) {
        return Optional.empty();
    }

    @Override
    public Usuario guardarMembresia(Usuario usuario) {
        return null;
    }

    @Override
    public void eliminarMembresia(Integer id) {

    }
}
