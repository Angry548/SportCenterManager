package com.esfe.sistemagimnasio.services.implementations;

import com.esfe.sistemagimnasio.models.EvaluacionFisica;
import com.esfe.sistemagimnasio.repositories.IEvaluacionFisicaRepository;
import com.esfe.sistemagimnasio.services.interfaces.IEvaluacionFisicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvaluacionFisicaService
        implements IEvaluacionFisicaService {

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
    public EvaluacionFisica guardar(
            EvaluacionFisica evaluacionFisica) {

        return evaluacionFisicaRepository.save(
                evaluacionFisica
        );
    }


    @Override
    public void eliminar(Integer id) {
        evaluacionFisicaRepository.deleteById(id);
    }


    @Override
    public Page<EvaluacionFisica> obtenerTodosPaginados(
            Pageable pageable) {

        return evaluacionFisicaRepository.findAll(
                pageable
        );
    }


    @Override
    public double calcularIMC(Integer id) {

        EvaluacionFisica evaluacion =
                obtenerPorId(id)
                        .orElseThrow();

        double peso =
                evaluacion.getPeso().doubleValue();

        double estatura =
                evaluacion.getEstatura().doubleValue();

        return peso / (estatura * estatura);
    }


    // ==========================================
    // FILTRADO POR ENTRENADOR
    // ==========================================

    @Override
    public Page<EvaluacionFisica> obtenerPorEntrenadorEmail(
            String email,
            Pageable pageable) {

        return evaluacionFisicaRepository
                .findByEntrenador_Usuario_Email(
                        email,
                        pageable
                );
    }


    @Override
    public Optional<EvaluacionFisica> obtenerPorIdYEntrenadorEmail(
            Integer id,
            String email) {

        return evaluacionFisicaRepository
                .findByIdAndEntrenador_Usuario_Email(
                        id,
                        email
                );
    }
}