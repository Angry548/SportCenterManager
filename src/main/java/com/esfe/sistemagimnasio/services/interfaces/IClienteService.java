package com.esfe.sistemagimnasio.services.interfaces;


import com.esfe.sistemagimnasio.models.Cliente;
import com.esfe.sistemagimnasio.repositories.IClienteRepository;

import java.util.List;
import java.util.Optional;

public interface IClienteService {


    List<IClienteRepository> obtenerTodos();

    Optional<IClienteRepository> obtenerPorId(Integer id);

    Cliente guardar(Cliente cliente);

    void eliminar(Integer id);

    boolean tieneMembresiaVigente(Integer clienteId);

    boolean tieneEntrenadorActivo(Integer clienteId);


    List<Cliente> obtenerTodasMembresias();

    Optional<Cliente> obtenerMembresiaPorId(Integer id);

    Cliente guardarMembresia(Cliente membresiaCliente);

    void eliminarMembresia(Integer id);
}
