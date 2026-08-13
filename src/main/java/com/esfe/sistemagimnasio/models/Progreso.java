package com.esfe.sistemagimnasio.models;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import javax.xml.crypto.Data;

@Entity
@Table(name = "progresos")
public class Progreso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private Integer clienteId;

    @NotNull
    private Integer entrenadorId;

    @NotNull
    private Data fecha;

    private double peso;
    private double altura;
    private double porcentajeGrasaCorporal;
    private String observaciones;

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

    public Integer getEntrenadorId() {
        return entrenadorId;
    }

    public void setEntrenadorId(Integer entrenadorId) {
        this.entrenadorId = entrenadorId;
    }

    public Data getFecha() {
        return fecha;
    }

    public void setFecha(Data fecha) {
        this.fecha = fecha;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getPorcentajeGrasaCorporal() {
        return porcentajeGrasaCorporal;
    }

    public void setPorcentajeGrasaCorporal(double porcentajeGrasaCorporal) {
        this.porcentajeGrasaCorporal = porcentajeGrasaCorporal;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
