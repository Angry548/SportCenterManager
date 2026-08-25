package com.esfe.sistemagimnasio.services.implementations;

import com.esfe.sistemagimnasio.repositories.IAsignacionEntrenadorRepository;
import com.esfe.sistemagimnasio.services.interfaces.IAsigacionEntrenadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AsignacionEntrenador implements IAsigacionEntrenadorService {
    @Autowired
    private IAsignacionEntrenadorRepository asignacionEntrenadorRepository;

    @Override
    public List<com.esfe.sistemagimnasio.models.AsignacionEntrenador> obtenerTodos() {
        return asignacionEntrenadorRepository.findAll();
    }

    @Override
    public Optional<com.esfe.sistemagimnasio.models.AsignacionEntrenador> obtenerporId(Integer id) {
        return asignacionEntrenadorRepository.findById(id);
    }

    @Override
    public com.esfe.sistemagimnasio.models.AsignacionEntrenador guardar(com.esfe.sistemagimnasio.models.AsignacionEntrenador asignacionEntrenador) {
        return asignacionEntrenadorRepository.save(asignacionEntrenador);
    }

    @Override
    public void eliminar(Integer id) {
        asignacionEntrenadorRepository.deleteById(id);

    }
}
