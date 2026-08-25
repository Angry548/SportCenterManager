package com.esfe.sistemagimnasio.services.implementations;


import com.esfe.sistemagimnasio.models.Asistencia;
import com.esfe.sistemagimnasio.repositories.IAsistenciaRepository;
import com.esfe.sistemagimnasio.services.interfaces.IAsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AsistenciaService implements IAsistenciaService {

    @Autowired
    private IAsistenciaRepository IAsistenciaRepository;

    @Override
    public List<Asistencia> obtenerTodos() {
        return IAsistenciaRepository.findAll();
    }

    @Override
    public Optional<Asistencia> obtenerPorId(Integer id) {
        return IAsistenciaRepository.findById(id);
    }

    @Override
    public Asistencia guardar(Asistencia asistencia) {
        return IAsistenciaRepository.save(asistencia);
    }

    @Override
    public void eliminar(Integer id) {
        IAsistenciaRepository.deleteById(id);
    }

}