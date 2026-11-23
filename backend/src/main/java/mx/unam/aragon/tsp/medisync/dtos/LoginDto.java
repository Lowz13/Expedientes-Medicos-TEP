package mx.unam.aragon.tsp.medisync.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {

    @NotBlank(message = "Ingresa tu usuario o correo electrónico")
    private String identificador;

    @NotBlank(message = "Ingresa tu contraseña")
    private String password;
}