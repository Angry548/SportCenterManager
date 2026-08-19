package com.esfe.sistemagimnasio.repositories;

import com.esfe.sistemagimnasio.models.AsignacionEntrenador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IAsignacionEntrenadorRepository extends JpaRepository<AsignacionEntrenador, Integer> {

    List<AsignacionEntrenador> findByEntrenador_IdAndFechaFinIsNull(Integer entrenadorId);
    List<AsignacionEntrenador> findByCliente_IdOrderByFechaAsignacionDesc(Integer clienteId);
}
