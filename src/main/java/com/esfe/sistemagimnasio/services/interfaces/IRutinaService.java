package com.esfe.sistemagimnasio.services.interfaces;

import com.esfe.sistemagimnasio.models.Rutina;
import com.esfe.sistemagimnasio.models.RutinaEjercicio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IRutinaService {

    List<Rutina> obtenerTodos();

    Optional<Rutina> obtenerPorId(Integer id);

    Rutina guardar (Rutina rutina);

    void eliminar (Integer id);

    Page<Rutina> obtenerTodosPaginados(Pageable page);

    void agregarEjercicio(Integer rutinaId, RutinaEjercicio rutinaEjercicio);

    void quitarEjercicio(Integer rutinaId, Integer rutinaEjercicioId);

    List<RutinaEjercicio> listarEjercicios(Integer rutinaId);

    Page<Rutina> obtenerPorEntrenadorEmail(
            String email,
            Pageable pageable
    );

    Optional<Rutina> obtenerPorIdYEntrenadorEmail(
            Integer id,
            String email
    );
}
