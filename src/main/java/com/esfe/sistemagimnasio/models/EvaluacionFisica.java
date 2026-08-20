package com.esfe.sistemagimnasio.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Entity
@Table(name = "EVALUACION_FISICA")
public class EvaluacionFisica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "El cliente es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false, foreignKey = @ForeignKey(name = "FK_evaluacion_cliente"))
    private Cliente cliente;

    @NotNull(message = "El entrenador es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entrenador_id", nullable = false, foreignKey = @ForeignKey(name = "FK_evaluacion_entrenador"))
    private Entrenador entrenador;

    @NotNull(message = "La fecha es obligatoria")
    @PastOrPresent(message = "La fecha no puede ser futura")
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @NotNull(message = "El peso es obligatorio")
    @DecimalMin(value = "1.0", message = "El peso debe ser mayor a 0")
    @Digits(integer = 3, fraction = 2, message = "El peso admite hasta 2 decimales")
    @Column(name = "peso", nullable = false, precision = 5, scale = 2)
    private BigDecimal peso; //kg

    @NotNull(message = "La estatura es obligatoria")
    @DecimalMin(value = "0.5", message = "La estatura debe ser mayor a 0")
    @Digits(integer = 3, fraction = 2, message = "La estatura admite hasta 2 decimales")
    @Column(name = "estatura", nullable = false, precision = 5, scale = 2)
    private BigDecimal estatura; // metros

    @DecimalMin(value = "0.0", message = "El porcentaje de grasa no puede ser negativo")
    @DecimalMax(value = "100.0", message = "El porcentaje de grasa no puede superar 100")
    @Digits(integer = 3, fraction = 2, message = "El porcentaje admite hasta 2 decimales")
    @Column(name = "porcentaje_grasa", precision = 5, scale = 2)
    private BigDecimal porcentajeGrasa;

    @Size(max = 500, message = "Las observaciones no pueden exceder 500 caracteres")
    @Column(name = "observaciones", length = 500)
    private String observaciones;

    //Encapsulamiento
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public BigDecimal getEstatura() {
        return estatura;
    }

    public void setEstatura(BigDecimal estatura) {
        this.estatura = estatura;
    }

    public BigDecimal getPorcentajeGrasa() {
        return porcentajeGrasa;
    }

    public void setPorcentajeGrasa(BigDecimal porcentajeGrasa) {
        this.porcentajeGrasa = porcentajeGrasa;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    //Metodos de negocio
    public BigDecimal calcularIMC() {
        if (estatura == null || estatura.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal estaturaAlCuadrado = estatura.multiply(estatura);
        return peso.divide(estaturaAlCuadrado, 2, RoundingMode.HALF_UP);
    }
}
