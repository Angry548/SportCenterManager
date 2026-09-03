package com.esfe.sistemagimnasio.models.forms;

import com.esfe.sistemagimnasio.enums.Sexo;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class RegistroClienteForm {

    @NotBlank(message = "Los nombres son obligatorios")
    @Size(max = 80, message = "Los nombres no pueden exceder 80 caracteres")
    private String nombres;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(max = 80, message = "Los apellidos no pueden exceder 80 caracteres")
    private String apellidos;

    @NotBlank(message = "El DUI es obligatorio")
    @Pattern(
            regexp = "\\d{8}-\\d{1}",
            message = "El DUI debe tener el formato 00000000-0"
    )
    private String dui;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser anterior a hoy")
    private LocalDate fechaNacimiento;

    @NotNull(message = "El sexo es obligatorio")
    private Sexo sexo;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(
            regexp = "\\d{4}-\\d{4}",
            message = "El teléfono debe tener el formato 0000-0000"
    )
    private String telefono;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo no tiene un formato válido")
    @Size(max = 100)
    private String email;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 200)
    private String direccion;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(
            min = 6,
            max = 72,
            message = "La contraseña debe tener entre 6 y 72 caracteres"
    )
    private String password;


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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}