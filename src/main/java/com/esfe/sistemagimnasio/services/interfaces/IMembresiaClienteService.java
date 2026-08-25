package com.esfe.sistemagimnasio.services.interfaces;

import com.esfe.sistemagimnasio.models.MembresiaCliente;

import java.util.List;
import java.util.Optional;

public interface IMembresiaClienteService {

    List<MembresiaCliente> obtenerTodos();

    Optional<MembresiaCliente> obtenerPorId(Integer id);

    MembresiaCliente guardar(MembresiaCliente membresiaCliente);

    void eliminar(Integer id);
}