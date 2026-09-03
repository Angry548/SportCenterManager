package com.esfe.sistemagimnasio.services.implementations;

import com.esfe.sistemagimnasio.enums.Resultado;
import com.esfe.sistemagimnasio.models.Asistencia;
import com.esfe.sistemagimnasio.models.Cliente;
import com.esfe.sistemagimnasio.repositories.IAsistenciaRepository;
import com.esfe.sistemagimnasio.repositories.IClienteRepository;
import com.esfe.sistemagimnasio.repositories.IMembresiaClienteRepository;
import com.esfe.sistemagimnasio.services.interfaces.IAsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AsistenciaService
        implements IAsistenciaService {

    @Autowired
    private IAsistenciaRepository asistenciaRepository;

    @Autowired
    private IMembresiaClienteRepository membresiaClienteRepository;

    @Autowired
    private IClienteRepository clienteRepository;


    @Override
    public List<Asistencia> obtenerTodos() {
        return asistenciaRepository.findAll();
    }


    @Override
    public Optional<Asistencia> obtenerPorId(
            Integer id) {

        return asistenciaRepository.findById(id);
    }


    @Override
    public Asistencia guardar(
            Asistencia asistencia) {

        return asistenciaRepository.save(
                asistencia
        );
    }


    @Override
    public void eliminar(Integer id) {
        asistenciaRepository.deleteById(id);
    }


    @Override
    public Page<Asistencia> obtenerTodosPaginados(
            Pageable pageable) {

        return asistenciaRepository.findAll(
                pageable
        );
    }


    // =====================================================
    // VALIDAR ASISTENCIA EXISTENTE
    // =====================================================

    @Override
    public boolean validarAcceso(Integer id) {

        Asistencia asistencia =
                obtenerPorId(id)
                        .orElseThrow();

        LocalDate hoy =
                LocalDate.now();


        boolean acceso =
                membresiaClienteRepository
                        .findFirstByCliente_IdAndFechaInicioLessThanEqualAndFechaVencimientoGreaterThanEqualOrderByFechaVencimientoDesc(
                                asistencia
                                        .getCliente()
                                        .getId(),
                                hoy,
                                hoy
                        )
                        .isPresent();


        asistencia.setResultado(
                acceso
                        ? Resultado.PERMITIDO
                        : Resultado.RECHAZADO
        );


        guardar(asistencia);

        return acceso;
    }


    // =====================================================
    // ÚLTIMAS ASISTENCIAS
    // =====================================================

    @Override
    public List<Asistencia> obtenerUltimasPorCliente(
            Integer clienteId) {

        return asistenciaRepository
                .findTop5ByCliente_IdOrderByFechaHoraDesc(
                        clienteId
                );
    }


    // =====================================================
    // CONTADOR
    // =====================================================

    @Override
    public long contarPorCliente(
            Integer clienteId) {

        return asistenciaRepository
                .countByCliente_Id(
                        clienteId
                );
    }


    // =====================================================
    // REGISTRAR ACCESO POR QR
    // =====================================================

    @Override
    public Asistencia registrarAccesoPorQr(
            String codigoQr) {


        // ==========================================
        // BUSCAR CLIENTE
        // ==========================================

        Cliente cliente =
                clienteRepository
                        .findByCodigoQr(codigoQr)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "El código QR no corresponde a ningún cliente"
                                )
                        );


        LocalDate hoy =
                LocalDate.now();


        // ==========================================
        // VALIDAR MEMBRESÍA
        // ==========================================

        boolean membresiaVigente =
                membresiaClienteRepository
                        .findFirstByCliente_IdAndFechaInicioLessThanEqualAndFechaVencimientoGreaterThanEqualOrderByFechaVencimientoDesc(
                                cliente.getId(),
                                hoy,
                                hoy
                        )
                        .isPresent();


        // ==========================================
        // CREAR ASISTENCIA
        // ==========================================

        Asistencia asistencia =
                new Asistencia();

        asistencia.setCliente(
                cliente
        );

        asistencia.setFechaHora(
                LocalDateTime.now()
        );

        asistencia.setResultado(
                membresiaVigente
                        ? Resultado.PERMITIDO
                        : Resultado.RECHAZADO
        );


        return asistenciaRepository.save(
                asistencia
        );
    }
}