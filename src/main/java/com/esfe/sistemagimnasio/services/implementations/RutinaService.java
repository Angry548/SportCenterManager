package com.esfe.sistemagimnasio.services.implementations;

import com.esfe.sistemagimnasio.models.Rutina;
import com.esfe.sistemagimnasio.models.RutinaEjercicio;
import com.esfe.sistemagimnasio.repositories.IRutinaEjercicioRepository;
import com.esfe.sistemagimnasio.repositories.IRutinaRepository;
import com.esfe.sistemagimnasio.services.interfaces.IRutinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RutinaService implements IRutinaService {

    @Autowired
    private IRutinaRepository rutinaRepository;

    @Autowired
    private IRutinaEjercicioRepository rutinaEjercicioRepository;

    @Override
    public List<Rutina> obtenerTodos() {
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

    @Override
    public Page<Rutina> obtenerTodosPaginados(Pageable page) {
        return rutinaRepository.findAll(page);
    }

    @Override
    public void agregarEjercicio(Integer rutinaId, RutinaEjercicio rutinaEjercicio) {
        Rutina rutina = obtenerPorId(rutinaId).orElseThrow();

        rutinaEjercicio.setRutina(rutina);
        rutinaEjercicioRepository.save(rutinaEjercicio);
    }

    @Override
    public void quitarEjercicio(Integer rutinaId, Integer rutinaEjercicioId) {
        RutinaEjercicio rutinaEjercicio = rutinaEjercicioRepository
                .findById(rutinaEjercicioId)
                .orElseThrow();

        if (!rutinaEjercicio.getRutina().getId().equals(rutinaId)) {
            throw new IllegalArgumentException(
                    "El ejercicio no pertenece a esta rutina"
            );
        }

        rutinaEjercicioRepository.delete(rutinaEjercicio);
    }

    @Override
    public List<RutinaEjercicio> listarEjercicios(Integer rutinaId) {
        return rutinaEjercicioRepository.findByRutinaId(rutinaId);
    }
}
