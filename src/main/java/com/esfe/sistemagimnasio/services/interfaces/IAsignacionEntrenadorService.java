package com.esfe.sistemagimnasio.services.interfaces;

import com.esfe.sistemagimnasio.models.AsignacionEntrenador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IAsignacionEntrenadorService {

    List<AsignacionEntrenador> obtenerTodos();

    Optional<AsignacionEntrenador> obtenerPorId (Integer id);

    AsignacionEntrenador guardar (AsignacionEntrenador asignacionEntrenador);

    void eliminar(Integer id);

    Page<AsignacionEntrenador> obtenerTodosPaginados(Pageable pageable);

    void finalizar(Integer id);

    boolean estaActiva(Integer id);
}
