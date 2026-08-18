package com.esfe.sistemagimnasio.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "ASIGNACION_ENTRENADOR")
public class AsignacionEntrenador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "El cliente es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false, foreignKey = @ForeignKey(name = "FK_asignacion_cliente"))
    private Cliente cliente;

//    @NotNull(message = "El entrenador es obligatorio")
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "entrenador_id", nullable = false, foreignKey = @ForeignKey(name = "FK_asignacion_entrenador"))
//    private Entrenador entrenador;

    @NotNull(message = "La fecha de asignacion es obligatoria")
    @Column(name = "fecha_asignacion", nullable = false, updatable = false)
    private LocalDate fechaAsignacion;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;
}
