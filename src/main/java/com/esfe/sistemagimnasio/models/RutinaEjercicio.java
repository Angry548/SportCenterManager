package com.esfe.sistemagimnasio.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalTime;

@Entity
@Table(name = "RutinaEjercicios")
public class RutinaEjercicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @NotBlank(message = "este campo es obligatorio")
    private String series;

    @NotBlank(message = "las repeticiones es obligatorio")
    private String repeticiones;

    @NotBlank(message = "el tiempoDescanso no puede quedar vacio")
    private LocalTime tiempoDescanso;

    private String observaciones;

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(String repeticiones) {
        this.repeticiones = repeticiones;
    }

    public LocalTime getTiempoDescanso() {
        return tiempoDescanso;
    }

    public void setTiempoDescanso(LocalTime tiempoDescanso) {
        this.tiempoDescanso = tiempoDescanso;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
