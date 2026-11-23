package mx.unam.aragon.tsp.medisync.dtos;

import java.time.LocalDate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

public class PacienteRequestDTO {
    // Sección de validación para los campos del DTO
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido paterno es obligatorio")
    private String apellidoPaterno;

    @NotBlank(message = "El apellido materno es obligatorio")
    private String apellidoMaterno;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser anterior a la fecha actual")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;

    @Email(message = "El formato del correo electrónico no es válido")
    private String correo;

    private String alergias;

    private String antecedentes; // Información clínica sensible, protegida

    public PacienteRequestDTO() {
    }

    // Getters y Setters
    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getApellidoPaterno() {return apellidoPaterno;}
    public void setApellidoPaterno(String apellidoPaterno) {this.apellidoPaterno = apellidoPaterno;}

    public String getApellidoMaterno() {return apellidoMaterno;}
    public void setApellidoMaterno(String apellidoMaterno) {this.apellidoMaterno = apellidoMaterno;}

    public LocalDate getFechaNacimiento() {return fechaNacimiento;}
    public void setFechaNacimiento(LocalDate fechaNacimiento) {this.fechaNacimiento = fechaNacimiento;}

    public String getTelefono() {return telefono;}
    public void setTelefono(String telefono) {this.telefono = telefono;}

    public String getCorreo() {return correo;}
    public void setCorreo(String correo) {this.correo = correo;}

    public String getAlergias() {return alergias;}
    public void setAlergias(String alergias) {this.alergias = alergias;}

    public String getAntecedentes() {return antecedentes;}
    public void setAntecedentes(String antecedentes) {this.antecedentes = antecedentes;}
}