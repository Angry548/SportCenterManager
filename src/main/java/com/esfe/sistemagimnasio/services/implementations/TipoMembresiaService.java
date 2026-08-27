package com.esfe.sistemagimnasio.services.implementations;

import com.esfe.sistemagimnasio.models.TipoMembresia;
import com.esfe.sistemagimnasio.repositories.ITipoMembresiaRepository;
import com.esfe.sistemagimnasio.services.interfaces.ITipoMembresiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
