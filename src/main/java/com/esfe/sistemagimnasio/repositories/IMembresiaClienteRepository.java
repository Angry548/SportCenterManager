package com.esfe.sistemagimnasio.repositories;

import com.esfe.sistemagimnasio.models.MembresiaCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface IMembresiaClienteRepository extends JpaRepository<MembresiaCliente, Integer> {


}
