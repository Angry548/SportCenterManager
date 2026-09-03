package com.esfe.sistemagimnasio.services.implementations;

import com.esfe.sistemagimnasio.models.Pago;
import com.esfe.sistemagimnasio.repositories.IPagoRepository;
import com.esfe.sistemagimnasio.services.interfaces.IPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;

@Service
public class PagoService implements IPagoService {

    @Autowired
    private IPagoRepository pagoRepository;

    @Override
    public List<Pago> obtenerTodos() {
        return pagoRepository.findAll();
    }

    @Override
    public Optional<Pago> obtenerPorId(Integer id) {
        return pagoRepository.findById(id);
    }

    @Override
    public Pago guardar(Pago pago) {
        return pagoRepository.save(pago);
    }

    @Override
    public void eliminar(Integer id) {
        pagoRepository.deleteById(id);
    }

    @Override
    public Page<Pago> obtenerTodosPaginados(Pageable pageable) {
        return pagoRepository.findAll(pageable);
    }

    @Override
    public String generarNumeroComprobante() {
        return "COMP-" + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] generarComprobantePDF(Integer id) {

        Pago pago = obtenerPorId(id)
                .orElseThrow();

        ByteArrayOutputStream outputStream =
                new ByteArrayOutputStream();

        Document document =
                new Document();

        PdfWriter.getInstance(
                document,
                outputStream
        );

        document.open();


        document.add(
                new Paragraph(
                        "COMPROBANTE DE PAGO"
                )
        );


        document.add(
                new Paragraph(
                        "Número de comprobante: "
                                + pago.getNumeroComprobante()
                )
        );


        document.add(
                new Paragraph(
                        "Cliente: "
                                + pago.getMembresiaCliente()
                                .getCliente()
                                .getNombres()
                                + " "
                                + pago.getMembresiaCliente()
                                .getCliente()
                                .getApellidos()
                )
        );


        document.add(
                new Paragraph(
                        "Membresía: "
                                + pago.getMembresiaCliente()
                                .getTipoMembresia()
                                .getNombre()
                )
        );


        document.add(
                new Paragraph(
                        "Monto: $"
                                + pago.getMonto()
                )
        );


        document.add(
                new Paragraph(
                        "Método de pago: "
                                + pago.getMetodoPago()
                                .getNombre()
                )
        );


        document.add(
                new Paragraph(
                        "Fecha: "
                                + pago.getFecha()
                )
        );


        document.close();

        return outputStream.toByteArray();
    }
}
