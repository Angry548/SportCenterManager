package com.esfe.sistemagimnasio.services.implementations;

import com.esfe.sistemagimnasio.models.MetodoPago;
import com.esfe.sistemagimnasio.models.Pago;
import com.esfe.sistemagimnasio.repositories.IMetodoPagoRepository;
import com.esfe.sistemagimnasio.repositories.IPagoRepository;
import com.esfe.sistemagimnasio.services.interfaces.IMetodoPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MetodoPagoService implements IMetodoPagoService {

    @Autowired
    private IMetodoPagoRepository metodoPagoRepository;

    @Override
    public List<MetodoPago> obtenerTodos() {
        return metodoPagoRepository.findAll();
    }

    @Override
    public Optional<MetodoPago> obtenerPorId(Integer id) {
        return metodoPagoRepository.findById(id);
    }

    @Override
    public MetodoPago guardar(MetodoPago MetodoPago) {
        return metodoPagoRepository.save(MetodoPago);
    }

    @Override
    public void eliminar(Integer id) {
        metodoPagoRepository.deleteById(id);
    }

    @Override
    public Page<MetodoPago> obtenerTodosPaginados(Pageable pageable) {
        return metodoPagoRepository.findAll(pageable);
    }

    @Override
    public void activar(Integer id) {
        MetodoPago metodoPago = obtenerPorId(id).orElseThrow();

        metodoPago.setActivo(true);
        guardar(metodoPago);
    }

    @Override
    public void desactivar(Integer id) {
        MetodoPago metodoPago = obtenerPorId(id).orElseThrow();

        metodoPago.setActivo(false);
        guardar(metodoPago);
    }
}


