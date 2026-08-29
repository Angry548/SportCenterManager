package com.esfe.sistemagimnasio.services.implementations;


import com.esfe.sistemagimnasio.enums.Resultado;
import com.esfe.sistemagimnasio.models.Asistencia;
import com.esfe.sistemagimnasio.repositories.IAsistenciaRepository;
import com.esfe.sistemagimnasio.repositories.IMembresiaClienteRepository;
import com.esfe.sistemagimnasio.services.interfaces.IAsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AsistenciaService implements IAsistenciaService {

    @Autowired
    private IAsistenciaRepository asistenciaRepository;

    @Autowired
    private IMembresiaClienteRepository membresiaClienteRepository;

    @Override
    public List<Asistencia> obtenerTodos() {
        return asistenciaRepository.findAll();
    }

    @Override
    public Optional<Asistencia> obtenerPorId(Integer id) {
        return asistenciaRepository.findById(id);
    }

    @Override
    public Asistencia guardar(Asistencia asistencia) {
        return asistenciaRepository.save(asistencia);
    }

    @Override
    public void eliminar(Integer id) {
        asistenciaRepository.deleteById(id);
    }

    @Override
    public Page<Asistencia> obtenerTodosPaginados(Pageable pageable) {
        return asistenciaRepository.findAll(pageable);
    }

    @Override
    public boolean validarAcceso(Integer id) {
        Asistencia asistencia = obtenerPorId(id).orElseThrow();

        boolean acceso = membresiaClienteRepository
                .existsByClienteIdAndFechaVencimientoGreaterThanEqual(
                        asistencia.getCliente().getId(),
                        LocalDate.now()
                );

        asistencia.setResultado(
                acceso ? Resultado.PERMITIDO : Resultado.RECHAZADO
        );

        guardar(asistencia);

        return acceso;
    }

}