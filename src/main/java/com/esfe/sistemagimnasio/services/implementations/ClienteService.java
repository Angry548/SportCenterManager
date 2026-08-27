package com.esfe.sistemagimnasio.services.implementations;

import com.esfe.sistemagimnasio.models.Cliente;
import com.esfe.sistemagimnasio.repositories.IClienteRepository;
import com.esfe.sistemagimnasio.services.interfaces.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ClienteService implements IClienteService {
    @Autowired
    IClienteRepository clienteRepository;

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
}
