package com.esfe.sistemagimnasio.services.interfaces;

import com.esfe.sistemagimnasio.models.Ejercicio;

import java.util.List;
import java.util.Optional;

public interface IEjercicioService {
    List<Ejercicio>obtenerTodos();
    Optional<Ejercicio>obtenerPorId(Integer id);
    Ejercicio guardar (Ejercicio ejercicio);
    void eliminar(Integer id);
}
