package com.esfe.sistemagimnasio.services.interfaces;

import com.esfe.sistemagimnasio.models.EvaluacionFisica;

import java.util.List;
import java.util.Optional;

public interface IEvaluacionFisicaService {

    List<EvaluacionFisica> obtenerTodos();

    Optional<EvaluacionFisica> obtenerPorId(Integer id);

    EvaluacionFisica guardar (EvaluacionFisica evaluacionFisica);

    void eliminar (Integer id);
}
