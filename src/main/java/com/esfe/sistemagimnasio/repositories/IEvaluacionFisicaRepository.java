package com.esfe.sistemagimnasio.repositories;

import com.esfe.sistemagimnasio.models.EvaluacionFisica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IEvaluacionFisicaRepository
        extends JpaRepository<EvaluacionFisica, Integer> {

    Page<EvaluacionFisica> findByEntrenador_Usuario_Email(
            String email,
            Pageable pageable
    );

    Optional<EvaluacionFisica> findByIdAndEntrenador_Usuario_Email(
            Integer id,
            String email
    );
}