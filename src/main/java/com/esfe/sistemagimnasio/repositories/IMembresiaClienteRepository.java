package com.esfe.sistemagimnasio.repositories;

import com.esfe.sistemagimnasio.models.MembresiaCliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface IMembresiaClienteRepository extends JpaRepository<MembresiaCliente, Integer> {
    List<MembresiaCliente> findByCliente_IdOrderByFechaVencimientoDesc(Integer clienteId);
}
