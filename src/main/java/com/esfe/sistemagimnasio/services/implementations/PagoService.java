package com.esfe.sistemagimnasio.services.implementations;

import com.esfe.sistemagimnasio.models.Pago;
import com.esfe.sistemagimnasio.repositories.IPagoRepository;
import com.esfe.sistemagimnasio.services.interfaces.IPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class PagoService implements IPagoService {

    @Autowired
    private IPagoRepository IPagoRepository;

    @Override
    public List<Pago> obtenerTodos() {
        return IPagoRepository.findAll();
    }

    @Override
    public Optional<Pago> obtenerPorId(Integer id) {
        return IPagoRepository.findById(id);
    }

    @Override
    public Pago guardar(Pago pago) {
        return IPagoRepository.save(pago);
    }

    @Override
    public void eliminar(Integer id) {
      IPagoRepository.deleteById(id);
    }
}
