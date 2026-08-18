package com.esfe.sistemagimnasio.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "MEMBRESIA_CLIENTE")
public class MembresiaCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "El cliente es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false, foreignKey = @ForeignKey(name = "FK_membresiaCliente_cliente"))
    private Cliente cliente;

//    @NotNull(message = "El tipo de membresia es obligatorio")
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "tipo_membresia_id", nullable = false, foreignKey = @ForeignKey(name = "FK_membresiaCliente_tipoMembresia"))
//    private TipoMembresia tipoMembresia;

    @NotNull(message = "La fecha de inicio es obligatoria")
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    @FutureOrPresent(message = "La fecha de vencimiento no puede ser anterior a hoy al crear el registro")
    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;
}
