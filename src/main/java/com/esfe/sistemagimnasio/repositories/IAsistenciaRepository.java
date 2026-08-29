package com.esfe.sistemagimnasio.repositories;

import com.esfe.sistemagimnasio.enums.Resultado;
import com.esfe.sistemagimnasio.models.Asistencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAsistenciaRepository extends JpaRepository<Asistencia, Integer> {

}
