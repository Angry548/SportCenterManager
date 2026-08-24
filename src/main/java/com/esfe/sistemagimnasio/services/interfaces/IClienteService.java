package com.esfe.sistemagimnasio.services.interfaces;

import com.esfe.sistemagimnasio.models.Cliente;

import java.util.List;
import java.util.Optional;

public interface IClienteService {

    List<IClienteService> obtenerTodos();

    Optional<IClienteService> obtenerPorId(Integer id);

    Cliente guardar(Cliente cliente);

    void eliminar(Integer id);

    boolean tieneMembresiaVigente(Integer clienteId);

    boolean tieneEntrenadorActivo(Integer clienteId);
}
