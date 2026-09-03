package com.esfe.sistemagimnasio.repositories;

import com.esfe.sistemagimnasio.models.MembresiaCliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface IMembresiaClienteRepository
        extends JpaRepository<MembresiaCliente, Integer> {

    boolean existsByClienteIdAndFechaVencimientoGreaterThanEqual(
            Integer clienteId,
            LocalDate fecha
    );

    Optional<MembresiaCliente>
    findFirstByCliente_IdAndFechaInicioLessThanEqualAndFechaVencimientoGreaterThanEqualOrderByFechaVencimientoDesc(
            Integer clienteId,
            LocalDate fechaInicio,
            LocalDate fechaVencimiento
    );
}