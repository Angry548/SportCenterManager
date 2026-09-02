package com.esfe.sistemagimnasio.services.interfaces;

import com.esfe.sistemagimnasio.models.TipoMembresia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ITipoMembresiaService {

    List<TipoMembresia> obtenerTodos();

    Optional<TipoMembresia> obtenerPorId(Integer id);

    TipoMembresia guardar(TipoMembresia tipoMembresia);

    void eliminar(Integer id);

    Page<TipoMembresia> obtenerTodosPaginados(Pageable pageable);

    LocalDate calcularFechaVencimiento(Integer id, LocalDate fechaInicio);

    void activar(Integer id);

    void desactivar(Integer id);
}