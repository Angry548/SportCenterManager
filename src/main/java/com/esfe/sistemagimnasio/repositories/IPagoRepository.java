package com.esfe.sistemagimnasio.repositories;


import com.esfe.sistemagimnasio.models.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPagoRepository extends JpaRepository<Pago,Integer> {
}
