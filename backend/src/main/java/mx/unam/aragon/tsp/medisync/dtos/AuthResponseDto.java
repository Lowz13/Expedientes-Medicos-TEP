package mx.unam.aragon.tsp.medisync.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import mx.unam.aragon.tsp.medisync.models.Rol;

@Data
@AllArgsConstructor
public class AuthResponseDto {
    private String token;
    private String tipo;
    private String id;
    private String nombre;
    private String username;
    private String correo;
    private Rol rol;
}