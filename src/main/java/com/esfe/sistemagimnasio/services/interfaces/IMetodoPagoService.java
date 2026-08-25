package com.esfe.sistemagimnasio.services.interfaces;

import com.esfe.sistemagimnasio.models.MetodoPago;

import java.util.List;
import java.util.Optional;

public interface IMetodoPagoService {

        List<MetodoPago> obtenerTodos();
        List<MetodoPago> obtenerActivos();
        Optional<MetodoPago> obtenerPorId(Integer id);
        MetodoPago guardar(MetodoPago metodoPago);
        void eliminar(Integer id);
    }

