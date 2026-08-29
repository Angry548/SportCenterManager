package com.esfe.sistemagimnasio.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PAGO")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "La membresia asociada es obligatoria")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "membresia_cliente_id", nullable = false, foreignKey = @ForeignKey(name = "FK_pago_membresiaCliente"))
    private MembresiaCliente membresiaCliente;

    @NotNull(message = "El metodo de pago es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "metodo_pago_id", nullable = false, foreignKey = @ForeignKey(name = "FK_pago_metodoPago"))
    private MetodoPago metodoPago;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor a 0")
    @Digits(integer = 8, fraction = 2, message = "El monto admite hasta 2 decimales")
    @Column(name = "monto", nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Column(name = "fecha", nullable = false, updatable = false)
    private LocalDateTime fecha;

    @Size(max = 20)
    @Column(name = "numero_comprobante", nullable = false, length = 20, unique = true, updatable = false)
    private String numeroComprobante;

    //Encapsulamiento
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MembresiaCliente getMembresiaCliente() {
        return membresiaCliente;
    }

    public void setMembresiaCliente(MembresiaCliente membresiaCliente) {
        this.membresiaCliente = membresiaCliente;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getNumeroComprobante() {
        return numeroComprobante;
    }

    public void setNumeroComprobante(String numeroComprobante) {
        this.numeroComprobante = numeroComprobante;
    }

}
