package com.esfe.sistemagimnasio.repositories;


import com.esfe.sistemagimnasio.models.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface IPagoRepository extends JpaRepository<Pago,Integer> {

}