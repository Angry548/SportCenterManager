package com.esfe.sistemagimnasio.repositories;

import com.esfe.sistemagimnasio.models.AsignacionEntrenador;
import com.esfe.sistemagimnasio.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IAsignacionEntrenadorRepository extends JpaRepository<AsignacionEntrenador, Integer> {
    boolean existsByClienteIdAndFechaFinIsNull(Integer clienteId);

    @Query("""
    SELECT a.cliente
    FROM AsignacionEntrenador a
    WHERE a.entrenador.id = :entrenadorId
    AND a.fechaFin IS NULL
""")
    List<Cliente> obtenerClientesActivos(Integer entrenadorId);
}
