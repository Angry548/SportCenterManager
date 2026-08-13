package com.esfe.sistemagimnasio.models;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nombres", length = 100, nullable = false)
    private String nombres;

    @Column(name = "apellidos", length = 100, nullable = false)
    private String apellidos;

    @Column(name = "dui", length = 10, unique = true, nullable = false)
    private String dui;

    @Column(name = "correo", length = 150, unique = true, nullable = false)
    private String correo;

    @Column(name = "contrasena", length = 255, nullable = false)
    private String contrasena;

    @Column(name = "telefono", length = 15, nullable = true)
    private String telefono;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoUsuario estado = EstadoUsuario.Activo;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private RolUsuario rol;

    // Constructor vacío por defecto
    public Usuario() {
    }

    // Constructor completo
    public Usuario(int id, String nombres, String apellidos, String dui, String correo,
                   String contrasena, String telefono, EstadoUsuario estado, RolUsuario rol) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dui = dui;
        this.correo = correo;
        this.contrasena = contrasena;
        this.telefono = telefono;
        this.estado = estado;
        this.rol = rol;
    }

    // Métodos del UML (puedes dejarlos declarados o implementarlos según lógica)
    public boolean iniciarSesion() {
        return false;
    }

    public void cerrarSesion() {
    }

    public void cambiarEstado() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public EstadoUsuario getEstado() {
        return estado;
    }

    public void setEstado(EstadoUsuario estado) {
        this.estado = estado;
    }

    public RolUsuario getRol() {
        return rol;
    }

    public void setRol(RolUsuario rol) {
        this.rol = rol;
    }
}