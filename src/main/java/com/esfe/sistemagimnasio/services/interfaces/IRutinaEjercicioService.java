package com.esfe.sistemagimnasio.services.interfaces;

import com.esfe.sistemagimnasio.models.RutinaEjercicio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IRutinaEjercicioService {

    List<RutinaEjercicio>obtenerTodos();

    Optional<RutinaEjercicio>obtenerPorId(Integer id);

    RutinaEjercicio guardar (RutinaEjercicio rutinaEjercicio);

    void eliminar (Integer id);

    Page<RutinaEjercicio> obtenerTodosPaginados(Pageable pageable);
}
