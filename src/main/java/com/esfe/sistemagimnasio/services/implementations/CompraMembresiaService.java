package com.esfe.sistemagimnasio.services.implementations;

import com.esfe.sistemagimnasio.models.Cliente;
import com.esfe.sistemagimnasio.models.MembresiaCliente;
import com.esfe.sistemagimnasio.models.MetodoPago;
import com.esfe.sistemagimnasio.models.Pago;
import com.esfe.sistemagimnasio.models.TipoMembresia;
import com.esfe.sistemagimnasio.services.interfaces.IClienteService;
import com.esfe.sistemagimnasio.services.interfaces.IMembresiaClienteService;
import com.esfe.sistemagimnasio.services.interfaces.IMetodoPagoService;
import com.esfe.sistemagimnasio.services.interfaces.IPagoService;
import com.esfe.sistemagimnasio.services.interfaces.ITipoMembresiaService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class CompraMembresiaService {

    private final IClienteService clienteService;
    private final IMembresiaClienteService membresiaClienteService;
    private final ITipoMembresiaService tipoMembresiaService;
    private final IMetodoPagoService metodoPagoService;
    private final IPagoService pagoService;

    public CompraMembresiaService(
            IClienteService clienteService,
            IMembresiaClienteService membresiaClienteService,
            ITipoMembresiaService tipoMembresiaService,
            IMetodoPagoService metodoPagoService,
            IPagoService pagoService) {

        this.clienteService = clienteService;
        this.membresiaClienteService = membresiaClienteService;
        this.tipoMembresiaService = tipoMembresiaService;
        this.metodoPagoService = metodoPagoService;
        this.pagoService = pagoService;
    }


    @Transactional
    public Pago adquirir(
            String email,
            Integer tipoMembresiaId,
            Integer metodoPagoId) {

        // =====================================================
        // CLIENTE AUTENTICADO
        // =====================================================

        Cliente cliente =
                clienteService
                        .obtenerPorUsuarioEmail(email)
                        .orElseThrow(() ->
                                new AccessDeniedException(
                                        "No existe un perfil de cliente asociado a esta cuenta."
                                )
                        );


        // =====================================================
        // EVITAR DOS MEMBRESÍAS VIGENTES
        // =====================================================

        boolean tieneMembresia =
                membresiaClienteService
                        .obtenerMembresiaVigenteCliente(
                                cliente.getId()
                        )
                        .isPresent();


        if (tieneMembresia) {

            throw new IllegalStateException(
                    "Ya cuentas con una membresía vigente."
            );
        }


        // =====================================================
        // TIPO DE MEMBRESÍA
        // =====================================================

        TipoMembresia tipo =
                tipoMembresiaService
                        .obtenerPorId(tipoMembresiaId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "El plan seleccionado no existe."
                                )
                        );


        if (!Boolean.TRUE.equals(tipo.getActivo())) {

            throw new IllegalStateException(
                    "El plan seleccionado no se encuentra disponible."
            );
        }


        // =====================================================
        // MÉTODO DE PAGO
        // =====================================================

        MetodoPago metodoPago =
                metodoPagoService
                        .obtenerPorId(metodoPagoId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "El método de pago seleccionado no existe."
                                )
                        );


        if (!Boolean.TRUE.equals(metodoPago.getActivo())) {

            throw new IllegalStateException(
                    "El método de pago seleccionado no está disponible."
            );
        }


        // =====================================================
        // CREAR MEMBRESÍA
        // =====================================================

        LocalDate fechaInicio =
                LocalDate.now();


        LocalDate fechaVencimiento =
                tipoMembresiaService
                        .calcularFechaVencimiento(
                                tipo.getId(),
                                fechaInicio
                        );


        MembresiaCliente membresia =
                new MembresiaCliente();

        membresia.setCliente(cliente);
        membresia.setTipoMembresia(tipo);
        membresia.setFechaInicio(fechaInicio);
        membresia.setFechaVencimiento(fechaVencimiento);


        MembresiaCliente membresiaGuardada =
                membresiaClienteService
                        .guardar(membresia);


        // =====================================================
        // CREAR PAGO
        // =====================================================

        Pago pago =
                new Pago();

        pago.setMembresiaCliente(
                membresiaGuardada
        );

        pago.setMetodoPago(
                metodoPago
        );

        /*
         * El monto sale del plan seleccionado.
         * El cliente no puede manipular el precio.
         */
        pago.setMonto(
                tipo.getPrecio()
        );

        pago.setFecha(
                LocalDateTime.now()
        );

        pago.setNumeroComprobante(
                pagoService
                        .generarNumeroComprobante()
        );


        return pagoService.guardar(pago);
    }
}