package com.esfe.sistemagimnasio.repositories;

import com.esfe.sistemagimnasio.models.Entrenador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IEntrenadorRepository extends JpaRepository<Entrenador, Integer> {

}
