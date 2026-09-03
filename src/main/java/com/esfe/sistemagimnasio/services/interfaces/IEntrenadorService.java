package com.esfe.sistemagimnasio.services.interfaces;

import com.esfe.sistemagimnasio.models.Cliente;
import com.esfe.sistemagimnasio.models.Entrenador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IEntrenadorService {

    List<Entrenador> obtenerTodos();

    Optional<Entrenador> obtenerPorId(Integer Id);

    Entrenador guardar (Entrenador entrenador);

    void eliminar (Integer id);

    Page<Entrenador> obtenerTodosPaginados(Pageable pageable);

    List<Cliente> obtenerClientesActivos(Integer id);

    boolean existePorUsuario(Integer usuarioId);
}
