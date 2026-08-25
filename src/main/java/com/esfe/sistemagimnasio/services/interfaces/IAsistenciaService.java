package com.esfe.sistemagimnasio.services.interfaces;

import com.esfe.sistemagimnasio.models.Asistencia;

import java.util.List;
import java.util.Optional;

public interface IAsistenciaService {

    List<Asistencia> obtenerTodos();

    Optional<Asistencia> obtenerPorId(Integer id);

    Asistencia guardar(Asistencia asistencia);

    void eliminar(Integer id);

}
