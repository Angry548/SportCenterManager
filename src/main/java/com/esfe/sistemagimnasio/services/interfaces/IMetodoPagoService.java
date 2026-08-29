package com.esfe.sistemagimnasio.services.interfaces;

import com.esfe.sistemagimnasio.models.MetodoPago;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IMetodoPagoService {

        List<MetodoPago> obtenerTodos();

        Optional<MetodoPago> obtenerPorId(Integer id);

        MetodoPago guardar(MetodoPago metodoPago);

        void eliminar(Integer id);

        Page<MetodoPago> obtenerTodosPaginados(Pageable pageable);

        void activar(Integer id);

        void desactivar(Integer id);
    }

