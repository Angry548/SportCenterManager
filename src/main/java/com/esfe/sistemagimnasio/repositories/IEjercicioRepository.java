package com.esfe.sistemagimnasio.repositories;

import com.esfe.sistemagimnasio.models.Ejercicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IEjercicioRepository extends JpaRepository<Ejercicio,Integer> {

}
