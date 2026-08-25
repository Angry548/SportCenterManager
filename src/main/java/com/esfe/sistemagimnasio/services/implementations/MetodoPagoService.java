package com.esfe.sistemagimnasio.services.implementations;

import com.esfe.sistemagimnasio.models.MetodoPago;
import com.esfe.sistemagimnasio.models.Pago;
import com.esfe.sistemagimnasio.repositories.IMetodoPagoRepository;
import com.esfe.sistemagimnasio.repositories.IPagoRepository;
import com.esfe.sistemagimnasio.services.interfaces.IMetodoPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MetodoPagoService implements IMetodoPagoService {

    @Autowired
    private IMetodoPagoRepository IMetodoPagoRepository;

    @Override
    public List<MetodoPago> obtenerTodos() {
        return IMetodoPagoRepository.findAll();
    }

    @Override
    public List<MetodoPago> obtenerActivos() {
        return IMetodoPagoRepository.findByActivoTrue();
    }

    @Override
    public Optional<MetodoPago> obtenerPorId(Integer id) {
        return IMetodoPagoRepository.findById(id);
    }

    @Override
    public MetodoPago guardar(MetodoPago MetodoPago) {
        return IMetodoPagoRepository.save(MetodoPago);
    }


    @Override
    public void eliminar(Integer id) {
        IMetodoPagoRepository.deleteById(id);
    }
}


