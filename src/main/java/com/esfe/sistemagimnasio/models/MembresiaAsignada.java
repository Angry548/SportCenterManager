package com.esfe.sistemagimnasio.models;

import com.esfe.sistemagimnasio.enums.EstadoMembresiaAsignada;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Date;

@Entity
@Table (name = "membresia_asignadas")
public class MembresiaAsignada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private Integer clienteId;
    @NotNull
    private Integer membresiaId;
    @NotNull
    private Date fechaInicio;
    @NotNull
    private Date fechaVencimiento;
    @NotNull
    private EstadoMembresiaAsignada estado;

    public Integer getMembresiaId() {
        return membresiaId;
    }

    public void setMembresiaId(Integer membresiaId) {
        this.membresiaId = membresiaId;
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
        this.clienteId = clienteId;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public EstadoMembresiaAsignada getEstado() {
        return estado;
    }

    public void setEstado(EstadoMembresiaAsignada estado) {
        this.estado = estado;
    }
}
