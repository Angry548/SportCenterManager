package com.esfe.sistemagimnasio.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalTime;

@Entity
@Table(name = "RUTINA_EJERCICIO")
public class RutinaEjercicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "La rutina es obligatoria")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "rutina_id", nullable = false, foreignKey = @ForeignKey(name = "FK_rutinaEjercicio_rutina"))
    private Rutina rutina;

    @NotNull(message = "El ejercicio es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ejercicio_id", nullable = false, foreignKey = @ForeignKey(name = "FK_rutinaEjercicio_ejercicio"))
    private Ejercicio ejercicio;

    @NotNull(message = "Las series son obligatorias")
    @Min(value = 1, message = "Debe haber al menos 1 serie")
    @Column(name = "series", nullable = false)
    private Integer series;

    @NotNull(message = "Las repeticiones son obligatorias")
    @Min(value = 1, message = "Debe haber al menos 1 repeticion")
    @Column(name = "repeticiones", nullable = false)
    private Integer repeticiones;

    @NotNull(message = "El descanso es obligatorio")
    @Min(value = 0, message = "El descanso no puede ser negativo")
    @Column(name = "descanso_segundos", nullable = false)
    private Integer descansoSegundos;

    @Size(max = 300, message = "Las observaciones no pueden exceder 300 caracteres")
    @Column(name = "observaciones", length = 300)
    private String observaciones;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Rutina getRutina() {
        return rutina;
    }

    public void setRutina(Rutina rutina) {
        this.rutina = rutina;
    }

    public Ejercicio getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(Ejercicio ejercicio) {
        this.ejercicio = ejercicio;
    }

    public Integer getSeries() {
        return series;
    }

    public void setSeries(Integer series) {
        this.series = series;
    }

    public Integer getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(Integer repeticiones) {
        this.repeticiones = repeticiones;
    }

    public Integer getDescansoSegundos() {
        return descansoSegundos;
    }

    public void setDescansoSegundos(Integer descansoSegundos) {
        this.descansoSegundos = descansoSegundos;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
