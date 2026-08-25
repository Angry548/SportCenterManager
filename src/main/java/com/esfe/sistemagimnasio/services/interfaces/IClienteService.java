package com.esfe.sistemagimnasio.services.interfaces;


import com.esfe.sistemagimnasio.models.Cliente;
import com.esfe.sistemagimnasio.repositories.IClienteRepository;

import java.util.List;
import java.util.Optional;

public interface IClienteService {

    List<Cliente> obtenerTodasMembresias();

    Optional<Cliente> obtenerMembresiaPorId(Integer id);

    Cliente guardarMembresia(Cliente membresiaCliente);

    void eliminarMembresia(Integer id);
}

