package com.esfe.sistemagimnasio.services.interfaces;

import com.esfe.sistemagimnasio.models.Entrenador;

import java.util.List;
import java.util.Optional;

public interface IEntrenadorService {
    List<Entrenador> obtenerTodos();
    Optional<Entrenador> obtenerPorld(Integer Id);
    Entrenador guardar (Entrenador entrenador);
    void eliminar (Integer id);
}
