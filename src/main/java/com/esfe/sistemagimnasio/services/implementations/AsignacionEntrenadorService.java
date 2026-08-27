package com.esfe.sistemagimnasio.services.implementations;

import com.esfe.sistemagimnasio.models.AsignacionEntrenador;
import com.esfe.sistemagimnasio.repositories.IAsignacionEntrenadorRepository;
import com.esfe.sistemagimnasio.services.interfaces.IAsignacionEntrenadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AsignacionEntrenadorService implements IAsignacionEntrenadorService {
    @Autowired
    private IAsignacionEntrenadorRepository asignacionEntrenadorRepository;

    @Override
    public List<AsignacionEntrenador> obtenerTodos() {
        return asignacionEntrenadorRepository.findAll();
    }

    @Override
    public Optional<AsignacionEntrenador> obtenerPorId(Integer id) {
        return asignacionEntrenadorRepository.findById(id);
    }

    @Override
    public AsignacionEntrenador guardar(AsignacionEntrenador asignacionEntrenador) {
        return asignacionEntrenadorRepository.save(asignacionEntrenador);
    }

    @Override
    public void eliminar(Integer id) {
        asignacionEntrenadorRepository.deleteById(id);
    }

    @Override
    public Page<AsignacionEntrenador> obtenerTodosPaginados(Pageable pageable) {
        return asignacionEntrenadorRepository.findAll(pageable);
    }

    @Override
    public void finalizar(Integer id) {
        AsignacionEntrenador asignacion = obtenerPorId(id).orElseThrow();

        asignacion.setFechaFin(LocalDate.now());
        guardar(asignacion);
    }

    @Override
    public boolean estaActiva(Integer id) {
        AsignacionEntrenador asignacion = obtenerPorId(id).orElseThrow();

        return asignacion.getFechaFin() == null;
    }
}
