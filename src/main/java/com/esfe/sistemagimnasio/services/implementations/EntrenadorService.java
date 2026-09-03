package com.esfe.sistemagimnasio.services.implementations;

import com.esfe.sistemagimnasio.models.Cliente;
import com.esfe.sistemagimnasio.models.Entrenador;
import com.esfe.sistemagimnasio.repositories.IAsignacionEntrenadorRepository;
import com.esfe.sistemagimnasio.repositories.IEntrenadorRepository;
import com.esfe.sistemagimnasio.services.interfaces.IEntrenadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntrenadorService implements IEntrenadorService {

    @Autowired
    private IEntrenadorRepository entrenadorRepository;

    @Autowired
    private IAsignacionEntrenadorRepository asignacionEntrenadorRepository;

    @Override
    public List<Entrenador> obtenerTodos() {
        return entrenadorRepository.findAll();
    }

    @Override
    public Optional<Entrenador> obtenerPorId(Integer Id) {
        return entrenadorRepository.findById(Id);
    }

    @Override
    public Entrenador guardar(Entrenador entrenador) {
        return entrenadorRepository.save(entrenador);
    }

    @Override
    public void eliminar(Integer id) {
        entrenadorRepository.deleteById(id);
    }

    @Override
    public Page<Entrenador> obtenerTodosPaginados(Pageable pageable) {
        return entrenadorRepository.findAll(pageable);
    }

    @Override
    public List<Cliente> obtenerClientesActivos(Integer id) {
        return asignacionEntrenadorRepository.obtenerClientesActivos(id);
    }

    @Override
    public boolean existePorUsuario(Integer usuarioId) {
        return entrenadorRepository.existsByUsuario_Id(usuarioId);
    }
}
