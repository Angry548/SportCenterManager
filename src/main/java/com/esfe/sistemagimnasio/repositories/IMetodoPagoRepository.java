package com.esfe.sistemagimnasio.repositories;

import com.esfe.sistemagimnasio.models.MetodoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface IMetodoPagoRepository extends JpaRepository<MetodoPago, Integer> {
    List<MetodoPago> findByActivoTrue();
}
