package com.esfe.sistemagimnasio.services.interfaces;

import com.esfe.sistemagimnasio.models.EvaluacionFisica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IEvaluacionFisicaService {

    List<EvaluacionFisica> obtenerTodos();

    Optional<EvaluacionFisica> obtenerPorId(Integer id);

    EvaluacionFisica guardar (EvaluacionFisica evaluacionFisica);

    void eliminar (Integer id);

    Page<EvaluacionFisica> obtenerTodosPaginados(Pageable pageable);

    double calcularIMC(Integer id);
}
