package com.esfe.sistemagimnasio.services.implementations;

import com.esfe.sistemagimnasio.models.Cliente;
import com.esfe.sistemagimnasio.repositories.IClienteRepository;
import com.esfe.sistemagimnasio.services.interfaces.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
@Service
public class ClienteService implements IClienteService {
    @Autowired
    IClienteRepository clienteRepository;

    @Autowired
    private IMembresiaClienteRepository membresiaClienteRepository;

    @Autowired
    private IAsignacionEntrenadorRepository asignacionEntrenadorRepository;

    @Override
    public List<Cliente> obtenerTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Optional<Cliente> obtenerPorId(Integer id) {
        return clienteRepository.findById(id);
    }

    @Override
    public Cliente guardar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public void eliminar(Integer id) {
        clienteRepository.deleteById(id);

    }

    @Override
    public Page<Cliente> obtenerTodosPaginados(Pageable pageable) {
        return clienteRepository .findAll(pageable);
    }

    @Override
    public int obtenerEdad(Integer id) {
        Cliente cliente = obtenerPorId(id).orElseThrow();

        return Period.between(
                cliente.getFechaNacimiento(),
                LocalDate.now()
        ).getYears();
    }

    @Override
    public boolean tieneMembresiaVigente(Integer id) {
        return membresiaClienteRepository
                .existsByClienteIdAndFechaVencimientoGreaterThanEqual(
                        id,
                        LocalDate.now()
                );
    }

    @Override
    public boolean tieneEntrenadorActivo(Integer id) {
        return asignacionEntrenadorRepository
                .existsByClienteIdAndFechaFinIsNull(id);
    }
}
