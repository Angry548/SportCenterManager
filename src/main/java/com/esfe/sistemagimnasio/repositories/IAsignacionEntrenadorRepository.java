package com.esfe.sistemagimnasio.repositories;

import com.esfe.sistemagimnasio.models.AsignacionEntrenador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IAsignacionEntrenadorRepository extends JpaRepository<AsignacionEntrenador, Integer> {
    boolean existsByClienteIdAndFechaFinIsNull(Integer clienteId);
}
