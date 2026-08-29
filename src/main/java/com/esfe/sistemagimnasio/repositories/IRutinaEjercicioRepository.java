package com.esfe.sistemagimnasio.repositories;

import com.esfe.sistemagimnasio.models.RutinaEjercicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRutinaEjercicioRepository extends JpaRepository<RutinaEjercicio,Integer> {
    List<RutinaEjercicio> findByRutinaId(Integer rutinaId);

}
