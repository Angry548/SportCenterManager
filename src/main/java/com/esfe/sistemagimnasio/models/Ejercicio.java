package com.esfe.sistemagimnasio.models;

import com.esfe.sistemagimnasio.enums.Dificultad;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table (name = "EJERCICIO")
public class Ejercicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @NotNull(message = "El grupo muscular es obligatorio")
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "grupo_muscular_id", nullable = false, foreignKey = @ForeignKey(name = "FK_ejercicio_grupoMuscular"))
//    private GrupoMuscular grupoMuscular;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @NotNull(message = "La dificultad es obligatoria")
    @Enumerated(EnumType.STRING)
    @Column(name = "dificultad", nullable = false, length = 20)
    private Dificultad dificultad;

    @Size(max = 255, message = "La URL de la imagen no puede exceder 255 caracteres")
    @Column(name = "imagen_url", length = 255)
    private String imagenUrl;

}


