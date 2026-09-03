package com.esfe.sistemagimnasio.repositories;

import com.esfe.sistemagimnasio.models.Rutina;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRutinaRepository
        extends JpaRepository<Rutina, Integer> {

    Page<Rutina> findByEntrenador_Usuario_Email(
            String email,
            Pageable pageable
    );

    Optional<Rutina> findByIdAndEntrenador_Usuario_Email(
            Integer id,
            String email
    );
}