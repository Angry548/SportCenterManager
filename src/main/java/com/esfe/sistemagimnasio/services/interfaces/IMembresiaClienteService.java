package com.esfe.sistemagimnasio.services.interfaces;

import com.esfe.sistemagimnasio.models.MembresiaCliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface IMembresiaClienteService {

    List<MembresiaCliente> obtenerTodos();

    Optional<MembresiaCliente> obtenerPorId(Integer id);

    MembresiaCliente guardar(MembresiaCliente membresiaCliente);

    void eliminar(Integer id);

    Page <MembresiaCliente> obtenerTodosPaginados(Pageable pageable);

    boolean estaVigente(Integer membresiaClienteId);

    int diasRestantes(Integer membresiaClienteId);
}
