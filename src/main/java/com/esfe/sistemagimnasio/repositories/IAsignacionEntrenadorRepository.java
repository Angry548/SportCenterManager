package com.esfe.sistemagimnasio.repositories;

import com.esfe.sistemagimnasio.models.AsignacionEntrenador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IAsignacionEntrenadorRepository extends JpaRepository<AsignacionEntrenador, Integer> {

    List<AsignacionEntrenador> findByEntrenador_IdAndFechaFinIsNull(Integer entrenadorId);
    List<AsignacionEntrenador> findByCliente_IdOrderByFechaAsignacionDesc(Integer clienteId);
    Optional<AsignacionEntrenador> findByCliente_IdAndFechaFinIsNull(Integer clienteId);
}
