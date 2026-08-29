package com.esfe.sistemagimnasio.services.interfaces;

import com.esfe.sistemagimnasio.models.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IClienteService {

    List<Cliente> obtenerTodos();

    Optional<Cliente> obtenerPorId(Integer id);

    Cliente guardar(Cliente cliente);

    void eliminar(Integer id);

    Page<Cliente> obtenerTodosPaginados(Pageable pageable);

    int obtenerEdad(Integer id);

    boolean tieneMembresiaVigente(Integer id);

    boolean tieneEntrenadorActivo(Integer id);
}

