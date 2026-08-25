package com.esfe.sistemagimnasio.services.interfaces;

import com.esfe.sistemagimnasio.models.TipoMembresia;

import java.util.List;
import java.util.Optional;

public interface ITipoMembresiaService {

    List<TipoMembresia> obtenerTodos();

    Optional<TipoMembresia> obtenerPorId(Integer id);

    TipoMembresia guardar(TipoMembresia membresiaCliente);

    void eliminar(Integer id);
}