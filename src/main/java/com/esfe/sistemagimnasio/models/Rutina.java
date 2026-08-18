package com.esfe.sistemagimnasio.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "RUTINA")
public class Rutina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "El cliente es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false, foreignKey = @ForeignKey(name = "FK_rutina_cliente"))
    private Cliente cliente;

//    @NotNull(message = "El entrenador es obligatorio")
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "entrenador_id", nullable = false, foreignKey = @ForeignKey(name = "FK_rutina_entrenador"))
//    private Entrenador entrenador;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDate fechaCreacion;

    @Column(name = "fecha_modificacion")
    private LocalDate fechaModificacion;


}