package com.esfe.sistemagimnasio.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "pagos")
public class pagos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer clienteId;
    private Integer membresiaAsignadaId;
    private BigDecimal monto;
    private LocalDate fecha;
    private String metodoPago;
    private String numeroComprobante;

    
    public pagos() {
    }


    public pagos(Integer id, Integer clienteId, Integer membresiaAsignadaId, BigDecimal monto, LocalDate fecha, String metodoPago, String numeroComprobante) {
        this.setId(id);
        this.setClienteId(clienteId);
        this.setMembresiaAsignadaId(membresiaAsignadaId);
        this.setMonto(monto);
        this.setFecha(fecha);
        this.setMetodoPago(metodoPago);
        this.setNumeroComprobante(numeroComprobante);
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        if (clienteId == null) {
            throw new IllegalArgumentException("El ID del cliente es obligatorio.");
        }
        if (clienteId <= 0) {
            throw new IllegalArgumentException("El ID del cliente debe ser un número positivo.");
        }
        this.clienteId = clienteId;
    }

    public Integer getMembresiaAsignadaId() {
        return membresiaAsignadaId;
    }

    public void setMembresiaAsignadaId(Integer membresiaAsignadaId) {
        if (membresiaAsignadaId == null) {
            throw new IllegalArgumentException("El ID de la membresía asignada es obligatorio.");
        }
        if (membresiaAsignadaId <= 0) {
            throw new IllegalArgumentException("El ID de la membresía debe ser un número positivo.");
        }
        this.membresiaAsignadaId = membresiaAsignadaId;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        if (monto == null) {
            throw new IllegalArgumentException("El monto es obligatorio.");
        }
        if (monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a 0.");
        }
        this.monto = monto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha es obligatoria.");
        }
        if (fecha.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha no puede ser futura.");
        }
        this.fecha = fecha;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        if (metodoPago == null || metodoPago.trim().isEmpty()) {
            throw new IllegalArgumentException("El método de pago es obligatorio.");
        }
        if (metodoPago.length() > 50) {
            throw new IllegalArgumentException("El método de pago no puede exceder los 50 caracteres.");
        }
        this.metodoPago = metodoPago;
    }

    public String getNumeroComprobante() {
        return numeroComprobante;
    }

    public void setNumeroComprobante(String numeroComprobante) {
        if (numeroComprobante == null || numeroComprobante.trim().isEmpty()) {
            throw new IllegalArgumentException("El número de comprobante es obligatorio.");
        }
        if (numeroComprobante.length() > 100) {
            throw new IllegalArgumentException("El número de comprobante no puede exceder los 100 caracteres.");
        }
        this.numeroComprobante = numeroComprobante;
    }
}

