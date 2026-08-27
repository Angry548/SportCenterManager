package com.esfe.sistemagimnasio.services.implementations;

import com.esfe.sistemagimnasio.models.Usuario;
import com.esfe.sistemagimnasio.repositories.IUsuarioRepository;
import com.esfe.sistemagimnasio.services.interfaces.IClienteService;
import com.esfe.sistemagimnasio.services.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements IUsuarioService {
    @Autowired
    private IUsuarioRepository usuarioRepository;

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
        return usuarioRepository.save(usuario);
    }

    @Override
    public void eliminar(Integer id) {
        usuarioRepository.deleteById(id);

    }
}

