package com.esfe.sistemagimnasio.repositories;

import com.esfe.sistemagimnasio.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IClienteRepository extends JpaRepository<Cliente, Integer> {

    boolean existsByUsuario_Id(Integer usuarioId);

    boolean existsByDui(String dui);

    Optional<Cliente> findByUsuario_Email(String email);

    Optional<Cliente> findByCodigoQr(String codigoQr);
}