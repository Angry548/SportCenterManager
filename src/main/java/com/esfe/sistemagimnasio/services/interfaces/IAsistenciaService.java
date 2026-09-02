package com.esfe.sistemagimnasio.services.interfaces;

import com.esfe.sistemagimnasio.models.Asistencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IAsistenciaService {

    List<Asistencia> obtenerTodos();

    Optional<Asistencia> obtenerPorId(Integer id);

    Asistencia guardar(Asistencia asistencia);

    void eliminar(Integer id);

    Page<Asistencia> obtenerTodosPaginados(Pageable pageable);

    boolean validarAcceso(Integer id);

}
