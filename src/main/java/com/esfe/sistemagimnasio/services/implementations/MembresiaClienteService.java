package com.esfe.sistemagimnasio.services.implementations;

import com.esfe.sistemagimnasio.models.MembresiaCliente;
import com.esfe.sistemagimnasio.repositories.IMembresiaClienteRepository;
import com.esfe.sistemagimnasio.services.interfaces.IMembresiaClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class MembresiaClienteService implements IMembresiaClienteService {

    @Autowired
    private IMembresiaClienteRepository membresiaClienteRepository;

    @Override
    public List<MembresiaCliente> obtenerTodos() {
        return membresiaClienteRepository.findAll();
    }

    @Override
    public Optional<MembresiaCliente> obtenerPorId(Integer id) {
        return membresiaClienteRepository.findById(id);
    }

    @Override
    public MembresiaCliente guardar(MembresiaCliente membresiaCliente) {
        return membresiaClienteRepository.save(membresiaCliente);
    }

    @Override
    public void eliminar(Integer id) {
        membresiaClienteRepository.deleteById(id);
    }

    @Override
    public Page<MembresiaCliente> obtenerTodosPaginados(Pageable pageable) {
        return membresiaClienteRepository.findAll(pageable);
    }

    @Override
    public boolean estaVigente(Integer id) {
        MembresiaCliente membresia = obtenerPorId(id).orElseThrow();

        return !LocalDate.now().isAfter(membresia.getFechaVencimiento());
    }

    @Override
    public int diasRestantes(Integer id) {
        MembresiaCliente membresia = obtenerPorId(id).orElseThrow();

        long dias = ChronoUnit.DAYS.between(
                LocalDate.now(),
                membresia.getFechaVencimiento()
        );

        return (int) Math.max(dias, 0);
    }

    @Override
    public Optional<MembresiaCliente> obtenerMembresiaVigenteCliente(
            Integer clienteId) {

        LocalDate hoy = LocalDate.now();

        return membresiaClienteRepository
                .findFirstByCliente_IdAndFechaInicioLessThanEqualAndFechaVencimientoGreaterThanEqualOrderByFechaVencimientoDesc(
                        clienteId,
                        hoy,
                        hoy
                );
    }

}
