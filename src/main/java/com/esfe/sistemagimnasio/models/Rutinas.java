package com.esfe.sistemagimnasio.models;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Rutinas")
public class Rutinas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer clienteId;
    private Integer entrenadorId;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;


    public Rutinas() {
    }


    public Rutinas(Integer id, Integer clienteId, Integer entrenadorId, LocalDateTime fechaCreacion, LocalDateTime fechaModificacion) {
        this.setId(id);
        this.setClienteId(clienteId);
        this.setEntrenadorId(entrenadorId);
        this.setFechaCreacion(fechaCreacion);
        this.setFechaModificacion(fechaModificacion);
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

    public Integer getEntrenadorId() {
        return entrenadorId;
    }

    public void setEntrenadorId(Integer entrenadorId) {
        if (entrenadorId == null) {
            throw new IllegalArgumentException("El ID del entrenador es obligatorio.");
        }
        if (entrenadorId <= 0) {
            throw new IllegalArgumentException("El ID del entrenador debe ser un número positivo.");
        }
        this.entrenadorId = entrenadorId;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        if (fechaCreacion == null) {
            throw new IllegalArgumentException("La fecha de creación es obligatoria.");
        }
        if (fechaCreacion.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha de creación no puede ser futura.");
        }
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDateTime fechaModificacion) {
        if (fechaModificacion == null) {
            throw new IllegalArgumentException("La fecha de modificación es obligatoria.");
        }
        if (fechaModificacion.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha de modificación no puede ser futura.");
        }
        if (this.fechaCreacion != null && fechaModificacion.isBefore(this.fechaCreacion)) {
            throw new IllegalArgumentException("La fecha de modificación no puede ser anterior a la fecha de creación.");
        }
        this.fechaModificacion = fechaModificacion;
    }
}