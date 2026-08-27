package com.esfe.sistemagimnasio.services.interfaces;

import com.esfe.sistemagimnasio.models.GrupoMuscular;

import java.util.List;
import java.util.Optional;

public interface IGrupoMuscularService {

    List<GrupoMuscular> obtenerTodos();

    Optional<GrupoMuscular> obtenerPorId(Integer id);

    GrupoMuscular guardar(GrupoMuscular grupoMuscular);

    void eliminar(Integer id);
}
