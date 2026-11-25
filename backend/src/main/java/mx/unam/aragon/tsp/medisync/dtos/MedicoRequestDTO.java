package mx.unam.aragon.tsp.medisync.dtos;

import java.time.LocalDate;
import java.util.List;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class MedicoRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido paterno es obligatorio")
    private String apellidoPaterno;

    private String apellidoMaterno;

    @Past(message = "La fecha de nacimiento debe ser anterior a la fecha actual")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;

    @NotBlank(message = "La especialidad es obligatoria")
    private String especialidad;

    @NotBlank(message = "La cédula profesional es obligatoria")
    private String cedulaProfesional;

    private List<String> horarios;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 4, max = 30, message = "El usuario debe tener entre 4 y 30 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9._]+$", message = "El usuario solo puede contener letras, números, puntos y guiones bajos")
    private String username;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El formato del correo electrónico no es válido")
    private String correo;

    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    public MedicoRequestDTO() {}

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidoPaterno() { return apellidoPaterno; }
    public void setApellidoPaterno(String apellidoPaterno) { this.apellidoPaterno = apellidoPaterno; }

    public String getApellidoMaterno() { return apellidoMaterno; }
    public void setApellidoMaterno(String apellidoMaterno) { this.apellidoMaterno = apellidoMaterno; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public String getCedulaProfesional() { return cedulaProfesional; }
    public void setCedulaProfesional(String cedulaProfesional) { this.cedulaProfesional = cedulaProfesional; }

    public List<String> getHorarios() { return horarios; }
    public void setHorarios(List<String> horarios) { this.horarios = horarios; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}