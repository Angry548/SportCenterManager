package com.esfe.sistemagimnasio.models;

import jakarta.persistence.*;

@Entity
@Table(name = "membresias")
public class Membresia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "precio", precision = 10, scale = 2, nullable = false)
    private double precio;

    @Column(name = "duracion_dias", nullable = false)
    private int duracionDias;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoUsuario estado = EstadoUsuario.Activo;

    // Constructor vacío
    public Membresia() {
    }

    // Constructor completo
    public Membresia(int id, String nombre, double precio, int duracionDias, EstadoUsuario estado) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.duracionDias = duracionDias;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getDuracionDias() {
        return duracionDias;
    }

    public void setDuracionDias(int duracionDias) {
        this.duracionDias = duracionDias;
    }

    public EstadoUsuario getEstado() {
        return estado;
    }

    public void setEstado(EstadoUsuario estado) {
        this.estado = estado;
    }
}
