package com.esfe.sistemagimnasio.repositories;

import com.esfe.sistemagimnasio.models.MembresiaCliente;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IMembresiaClienteRepository extends JpaRepository<MembresiaCliente, Integer> {

}
