package com.esfe.sistemagimnasio.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "asistencias")
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer clienteId;
    private LocalDateTime fechaHora;
    private Boolean autorizado;


    public Asistencia() {
    }


    public Asistencia(Integer id, Integer clienteId, LocalDateTime fechaHora, Boolean autorizado) {
        this.setId(id);
        this.setClienteId(clienteId);
        this.setFechaHora(fechaHora);
        this.setAutorizado(autorizado);
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

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {

        if (fechaHora == null) {
            throw new IllegalArgumentException("La fecha y hora son obligatorias.");
        }
        if (fechaHora.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha y hora no pueden ser futuras.");
        }
        this.fechaHora = fechaHora;
    }

    public Boolean getAutorizado() {
        return autorizado;
    }

    public void setAutorizado(Boolean autorizado) {

        if (autorizado == null) {
            throw new IllegalArgumentException("El estado de autorización es obligatorio.");
        }
        this.autorizado = autorizado;
    }
}
