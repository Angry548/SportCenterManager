package com.esfe.sistemagimnasio.services.implementations;

import com.esfe.sistemagimnasio.models.Usuario;
import com.esfe.sistemagimnasio.repositories.IUsuarioRepository;
import com.esfe.sistemagimnasio.services.interfaces.IClienteService;
import com.esfe.sistemagimnasio.services.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Page<Usuario> obtenerTodosPaginados(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    //Se modificarán más adelante apropiadamente para SpringSecurity
    @Override
    public boolean autenticar(Integer id, String password) {
        Usuario usuario = obtenerPorId(id).orElseThrow();

        return usuario.getPasswordHash().equals(password);
    }

    @Override
    public void cambiarPassword(Integer id, String nuevaPassword) {
        Usuario usuario = obtenerPorId(id).orElseThrow();

        usuario.setPasswordHash(nuevaPassword);
        guardar(usuario);
    }

    @Override
    public void activar(Integer id) {
        Usuario usuario = obtenerPorId(id).orElseThrow();

        usuario.setActivo(true);
        guardar(usuario);
    }

    @Override
    public void desactivar(Integer id) {
        Usuario usuario = obtenerPorId(id).orElseThrow();

        usuario.setActivo(false);
        guardar(usuario);
    }


}

