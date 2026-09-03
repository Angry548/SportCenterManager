package com.esfe.sistemagimnasio.models;

import com.esfe.sistemagimnasio.enums.Sexo;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "CLIENTE")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "El cliente debe estar asociado a un usuario")
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true, foreignKey = @ForeignKey(name = "FK_cliente_usuario"))
    private Usuario usuario;

    @Column(name = "nombres", length = 80)
    private String nombres;

    @Column(name = "apellidos", length = 80)
    private String apellidos;

    @NotBlank(message = "El DUI es obligatorio")
    @Pattern(regexp = "\\d{8}-\\d{1}", message = "El DUI debe tener el formato 00000000-0")
    @Column(name = "dui", nullable = false, length = 10, unique = true)
    private String dui;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser anterior a hoy")
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @NotNull(message = "El sexo es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "sexo", nullable = false, length = 10)
    private Sexo sexo;

    @NotBlank(message = "El telefono es obligatorio")
    @Pattern(regexp = "\\d{4}-\\d{4}", message = "El telefono debe tener el formato 0000-0000")
    @Column(name = "telefono", nullable = false, length = 20)
    private String telefono;

    @NotBlank(message = "La direccion es obligatoria")
    @Size(max = 200, message = "La direccion no puede exceder 200 caracteres")
    @Column(name = "direccion", nullable = false, length = 200)
    private String direccion;

    @Column(name = "codigo_qr", nullable = false, unique = true, length = 36)
    private String codigoQr;

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

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    public String getCodigoQr() {
        return codigoQr;
    }

    public void setCodigoQr(String codigoQr) {
        this.codigoQr = codigoQr;
    }
}



