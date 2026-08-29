package com.esfe.sistemagimnasio.repositories;


import com.esfe.sistemagimnasio.models.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IPagoRepository extends JpaRepository<Pago,Integer> {

}