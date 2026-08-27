package com.esfe.sistemagimnasio.services.interfaces;

import com.esfe.sistemagimnasio.models.Rutina;

import java.util.List;
import java.util.Optional;

public interface IRutinaService {

    List<Rutina> obtenerTodosRutinas();

    Optional<Rutina> obtenerPorId(Integer id);

    Rutina guardar (Rutina rutina);

    void eliminar (Integer id);
}
