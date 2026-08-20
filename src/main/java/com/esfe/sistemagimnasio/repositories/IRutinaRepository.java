package com.esfe.sistemagimnasio.repositories;

import com.esfe.sistemagimnasio.models.Rutina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRutinaRepository extends JpaRepository<Rutina,Integer> {
    List<Rutina> findByCliente_Id(Integer clienteId);

    List<Rutina> findByEntrenador_Id(Integer entrenadorId);
}
