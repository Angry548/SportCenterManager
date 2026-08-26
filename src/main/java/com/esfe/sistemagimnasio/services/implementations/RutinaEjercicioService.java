package com.esfe.sistemagimnasio.services.implementations;

import com.esfe.sistemagimnasio.models.RutinaEjercicio;
import com.esfe.sistemagimnasio.repositories.IRutinaEjercicioRepository;
import com.esfe.sistemagimnasio.services.interfaces.IRutinaEjercicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class RutinaEjercicioService implements IRutinaEjercicioService {
    @Autowired
    private IRutinaEjercicioRepository rutinaEjercicioRepository;

    @Override
    public List<RutinaEjercicio> obtenerTodos() {
        return rutinaEjercicioRepository.findAll();
    }

    @Override
    public Optional<RutinaEjercicio> obtenerPorId(Integer id) {
        return rutinaEjercicioRepository.findById(id);
    }

    @Override
    public RutinaEjercicio guardar(RutinaEjercicio rutinaEjercicio) {
        return rutinaEjercicioRepository.save(rutinaEjercicio);
    }

    @Override
    public void eliminar(Integer id) {
        rutinaEjercicioRepository.deleteById(id);
    }
}
