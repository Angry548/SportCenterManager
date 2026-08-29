package com.esfe.sistemagimnasio.services.interfaces;

import com.esfe.sistemagimnasio.models.Pago;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IPagoService {

    List<Pago> obtenerTodos();

    Optional<Pago> obtenerPorId(Integer id);

    Pago guardar (Pago pago);

    void eliminar(Integer id);

    Page<Pago> obtenerTodosPaginados(Pageable pageable);

    String generarNumeroComprobante();

    byte[] generarComprobantePDF(Integer id);
}
