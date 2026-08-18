package com.esfe.sistemagimnasio.repositories;

import com.esfe.sistemagimnasio.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClienteRepository extends JpaRepository<Cliente, Integer> {

}
