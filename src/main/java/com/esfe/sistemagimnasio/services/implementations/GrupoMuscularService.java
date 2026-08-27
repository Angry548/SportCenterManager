package com.esfe.sistemagimnasio.services.implementations;

import com.esfe.sistemagimnasio.models.GrupoMuscular;
import com.esfe.sistemagimnasio.repositories.IGrupoMuscularRepository;
import com.esfe.sistemagimnasio.services.interfaces.IGrupoMuscularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GrupoMuscularService implements IGrupoMuscularService {

    @Autowired
    private IGrupoMuscularRepository grupoMuscularRepository;

    @Override
    public List<GrupoMuscular> obtenerTodos() {
        return grupoMuscularRepository.findAll();
    }

    @Override
    public Optional<GrupoMuscular> obtenerPorId(Integer id) {
        return grupoMuscularRepository.findById(id);
    }

    @Override
    public GrupoMuscular guardar(GrupoMuscular grupoMuscular) {
        return grupoMuscularRepository.save(grupoMuscular);
    }

    @Override
    public void eliminar(Integer id) {
        grupoMuscularRepository.deleteById(id);
    }
}
