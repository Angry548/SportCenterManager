package com.esfe.sistemagimnasio.services.implementations;

import com.esfe.sistemagimnasio.models.TipoMembresia;
import com.esfe.sistemagimnasio.repositories.ITipoMembresiaRepository;
import com.esfe.sistemagimnasio.services.interfaces.ITipoMembresiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Service
public class TipoMembresiaService implements ITipoMembresiaService {

    @Autowired
    private ITipoMembresiaRepository tipoMembresiaRepository;

    @Override
    public List<TipoMembresia> obtenerTodos() {
        return tipoMembresiaRepository.findAll();
    }

    @Override
    public Optional<TipoMembresia> obtenerPorId(Integer id) {
        return tipoMembresiaRepository.findById(id);
    }

    @Override
    public TipoMembresia guardar(TipoMembresia tipoMembresia) {
        return tipoMembresiaRepository.save(tipoMembresia);
    }

    @Override
    public void eliminar(Integer id) {
tipoMembresiaRepository.deleteById(id);
    }

    @Override
    public Page<TipoMembresia> obtenerTodosPaginados(Pageable pageable) {
        return tipoMembresiaRepository.findAll(pageable);
    }

    @Override
    public LocalDate calcularFechaVencimiento(Integer id, LocalDate fechaInicio) {
        TipoMembresia tipoMembresia = obtenerPorId(id).orElseThrow();

        return fechaInicio.plusDays(tipoMembresia.getDuracionDias());
    }

    @Override
    public void activar(Integer id) {
        TipoMembresia tipoMembresia = obtenerPorId(id).orElseThrow();

        tipoMembresia.setActivo(true);
        guardar(tipoMembresia);
    }

    @Override
    public void desactivar(Integer id) {
        TipoMembresia tipoMembresia = obtenerPorId(id).orElseThrow();

        tipoMembresia.setActivo(false);
        guardar(tipoMembresia);
    }
}
