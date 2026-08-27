package com.esfe.sistemagimnasio.services.implementations;

import com.esfe.sistemagimnasio.models.AsignacionEntrenador;
import com.esfe.sistemagimnasio.repositories.IAsignacionEntrenadorRepository;
import com.esfe.sistemagimnasio.services.interfaces.IAsigacionEntrenadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AsignacionEntrenadorService implements IAsigacionEntrenadorService {
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
}
