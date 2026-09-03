package com.esfe.sistemagimnasio.repositories;

import com.esfe.sistemagimnasio.models.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IAsistenciaRepository
        extends JpaRepository<Asistencia, Integer> {

    List<Asistencia>
    findTop5ByCliente_IdOrderByFechaHoraDesc(
            Integer clienteId
    );

    long countByCliente_Id(
            Integer clienteId
    );
}