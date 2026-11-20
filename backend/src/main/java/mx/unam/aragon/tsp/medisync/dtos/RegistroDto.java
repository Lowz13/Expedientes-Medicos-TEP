package mx.unam.aragon.tsp.medisync.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Email;
import lombok.Data;
import mx.unam.aragon.tsp.medisync.models.Rol;

@Data
public class RegistroDto {

    @NotBlank(message = "El nombre completo es obligatorio")
    private String nombre;

    @NotBlank(message = "El username es obligatorio")
    @Size(min = 4, max = 30, message = "El username debe tener entre 4 y 30 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9._]+$", message = "Solo se permiten letras, números, puntos y guiones bajos")
    private String username;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Formato de correo electrónico inválido")
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    @NotNull(message = "El rol es obligatorio")
    private Rol rol; // Usa el enum Rol de mx.unam.aragon.tsp.medisync.models
}