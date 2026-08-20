package com.esfe.sistemagimnasio.repositories;

import com.esfe.sistemagimnasio.models.EvaluacionFisica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IEvaluacionFisicaRepository extends JpaRepository<EvaluacionFisica, Integer> {
    List<EvaluacionFisica> findByCliente_IdOrderByFechaDesc(Integer clienteId);
}
