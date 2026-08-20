package com.esfe.sistemagimnasio.repositories;

import com.esfe.sistemagimnasio.models.Asistencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAsistenciaRepository extends JpaRepository<Asistencia, Integer> {
    Page<Asistencia> findByCliente_IdOrderByFechaHoraDesc(Integer clienteId, Pageable pageable);

    long countByCliente_IdAndResultado(Integer clienteId, Resultado resultado);
}
