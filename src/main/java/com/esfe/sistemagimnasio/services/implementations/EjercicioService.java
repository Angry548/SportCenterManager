package com.esfe.sistemagimnasio.services.implementations;

import com.esfe.sistemagimnasio.models.Ejercicio;
import com.esfe.sistemagimnasio.repositories.IEjercicioRepository;
import com.esfe.sistemagimnasio.services.interfaces.IEjercicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EjercicioService implements IEjercicioService {

    @Autowired
    private IEjercicioRepository ejercicioRepository;

    @Override
    public List<Ejercicio> obtenerTodos() {
        return ejercicioRepository.findAll();
    }

    @Override
    public Optional<Ejercicio> obtenerPorId(Integer id) {
        return ejercicioRepository.findById(id);
    }

    @Override
    public Ejercicio guardar(Ejercicio ejercicio) {
        return ejercicioRepository.save(ejercicio);
    }

    @Override
    public void eliminar(Integer id) {
        ejercicioRepository.deleteById(id);

    }

    @Override
    public Page<Ejercicio> obtenerTodosPaginados(Pageable pageable) {
        return ejercicioRepository.findAll(pageable);
    }
}
