package com.esfe.sistemagimnasio.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "ENTRENADOR")
public class Entrenador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "El entrenador debe estar asociado a un usuario")
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true, foreignKey = @ForeignKey(name = "FK_entrenador_usuario"))
    private Usuario usuario;

    @NotBlank(message = "El DUI es obligatorio")
    @Pattern(regexp = "\\d{8}-\\d{1}", message = "El DUI debe tener el formato 00000000-0")
    @Column(name = "dui", nullable = false, length = 10, unique = true)
    private String dui;

    @NotBlank(message = "La especialidad es obligatoria")
    @Size(max = 100, message = "La especialidad no puede exceder 100 caracteres")
    @Column(name = "especialidad", nullable = false, length = 100)
    private String especialidad;

    @NotBlank(message = "El telefono es obligatorio")
    @Pattern(regexp = "\\d{4}-\\d{4}", message = "El telefono debe tener el formato 0000-0000")
    @Column(name = "telefono", nullable = false, length = 20)
    private String telefono;

    //Encapsulamiento
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    //Metodos de negocio
    public java.util.List<AsignacionEntrenador> obtenerClientesActivos(
            java.util.List<AsignacionEntrenador> asignaciones) {
        return asignaciones.stream()
                .filter(AsignacionEntrenador::estaActiva)
                .toList();
    }

    public boolean estaDisponible(java.util.List<AsignacionEntrenador> asignaciones, int cupoMaximo) {
        long activos = asignaciones.stream().filter(AsignacionEntrenador::estaActiva).count();
        return activos < cupoMaximo;
    }
}
