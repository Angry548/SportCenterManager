package com.esfe.sistemagimnasio.services.interfaces;

import com.esfe.sistemagimnasio.models.AsignacionEntrenador;

import java.util.List;
import java.util.Optional;

public interface IAsigacionEntrenadorService {
    List<AsignacionEntrenador> obtenerTodos();
    Optional<AsignacionEntrenador> obtenerporId (Integer id);
    AsignacionEntrenador guardar (AsignacionEntrenador asignacionEntrenador);
    void eliminar(Integer id);
}
