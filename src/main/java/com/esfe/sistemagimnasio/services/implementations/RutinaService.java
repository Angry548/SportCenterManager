package com.esfe.sistemagimnasio.services.implementations;

import com.esfe.sistemagimnasio.models.Rutina;
import com.esfe.sistemagimnasio.repositories.IRutinaRepository;
import com.esfe.sistemagimnasio.services.interfaces.IRutinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RutinaService implements IRutinaService {

    @Autowired
    private IRutinaRepository rutinaRepository;
    @Override
    public List<Rutina> obtenerTodosRutinas() {
        return rutinaRepository.findAll();
    }

    @Override
    public Optional<Rutina> obtenerPorId(Integer id) {
        return rutinaRepository.findById(id);
    }

    @Override
    public Rutina guardar(Rutina rutina) {
        return rutinaRepository.save(rutina);
    }

    @Override
    public void eliminar(Integer id) {
        rutinaRepository.deleteById(id);
    }
}
