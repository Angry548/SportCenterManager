package com.esfe.sistemagimnasio.services.implementations;

import com.esfe.sistemagimnasio.models.EvaluacionFisica;
import com.esfe.sistemagimnasio.repositories.IEvaluacionFisicaRepository;
import com.esfe.sistemagimnasio.services.interfaces.IEvaluacionFisicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvaluacionFisicaService implements IEvaluacionFisicaService {

    @Autowired
    private IEvaluacionFisicaRepository evaluacionFisicaRepository;
    @Override
    public List<EvaluacionFisica> obtenerTodos() {
        return evaluacionFisicaRepository.findAll();
    }

    @Override
    public Optional<EvaluacionFisica> obtenerPorId(Integer id) {
        return evaluacionFisicaRepository.findById(id);
    }

    @Override
    public EvaluacionFisica guardar(EvaluacionFisica evaluacionFisica) {
        return evaluacionFisicaRepository.save(evaluacionFisica);
    }

    @Override
    public void eliminar(Integer id) {
        evaluacionFisicaRepository.deleteById(id);
    }
}
