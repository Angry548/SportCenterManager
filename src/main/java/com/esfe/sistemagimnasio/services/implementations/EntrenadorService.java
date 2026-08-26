package com.esfe.sistemagimnasio.services.implementations;

import com.esfe.sistemagimnasio.models.Entrenador;
import com.esfe.sistemagimnasio.repositories.IEntrenadorRepository;
import com.esfe.sistemagimnasio.services.interfaces.IEntrenadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntrenadorService implements IEntrenadorService {

    @Autowired
    private IEntrenadorRepository entrenadorRepository;

    @Override
    public List<Entrenador> obtenerTodos() {
        return entrenadorRepository.findAll();
    }

    @Override
    public Optional<Entrenador> obtenerPorld(Integer id) {
        return entrenadorRepository.findById(id);
    }

    @Override
    public Entrenador guardar(Entrenador entrenador) {
        return entrenadorRepository.save(entrenador);
    }

    @Override
    public void eliminar(Integer id) {
        entrenadorRepository.deleteById(id);
    }
}
