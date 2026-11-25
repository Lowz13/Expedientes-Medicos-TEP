package mx.unam.aragon.tsp.medisync.dtos;

import mx.unam.aragon.tsp.medisync.models.Rol;

public class LoginResponseDto {

    private String token;
    private String tipo = "Bearer";
    private String usuarioId;
    private String medicoId;
    private String nombre;
    private String username;
    private String correo;
    private Rol rol;

    public LoginResponseDto() {}

    public LoginResponseDto(String token, String usuarioId, String medicoId, String nombre,
                            String username, String correo, Rol rol) {
        this.token = token;
        this.usuarioId = usuarioId;
        this.medicoId = medicoId;
        this.nombre = nombre;
        this.username = username;
        this.correo = correo;
        this.rol = rol;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }

    public String getMedicoId() { return medicoId; }
    public void setMedicoId(String medicoId) { this.medicoId = medicoId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
}