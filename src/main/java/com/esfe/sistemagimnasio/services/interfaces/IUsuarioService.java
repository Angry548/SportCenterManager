package com.esfe.sistemagimnasio.services.interfaces;

import com.esfe.sistemagimnasio.models.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {

    List<Usuario> obtenerTodos();

    Optional<Usuario> obtenerPorId(Integer id);

    Usuario guardar(Usuario usuario);

    void eliminar(Integer id);

    Page<Usuario> obtenerTodosPaginados(Pageable pageable);

    boolean autenticar(Integer id, String password);

    void cambiarPassword(Integer id, String nuevaPassword);

    void activar(Integer id);

    void desactivar(Integer id);

    List<Usuario> obtenerUsuariosDisponiblesCliente();

    List<Usuario> obtenerUsuariosDisponiblesEntrenador();

    boolean existePorEmail(String email);
}