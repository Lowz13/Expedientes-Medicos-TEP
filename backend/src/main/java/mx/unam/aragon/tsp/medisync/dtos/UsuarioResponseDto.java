package mx.unam.aragon.tsp.medisync.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import mx.unam.aragon.tsp.medisync.models.Rol;
import mx.unam.aragon.tsp.medisync.models.Usuario;

public class UsuarioResponseDto {
private String id;
    private String username;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private LocalDate fechaNacimiento;
    private String correo;
    private String telefono;
    private Rol rol;
    private boolean activo;
    private LocalDateTime createdAt;

    public UsuarioResponseDto() {}

    public UsuarioResponseDto(Usuario usuario) {
        this.id = usuario.getId();
        this.username = usuario.getUsername();
        this.nombre = usuario.getNombre();
        this.apellidoPaterno = usuario.getApellidoPaterno();
        this.apellidoMaterno = usuario.getApellidoMaterno();
        this.fechaNacimiento = usuario.getFechaNacimiento();
        this.correo = usuario.getCorreo();
        this.telefono = usuario.getTelefono();
        this.rol = usuario.getRol();
        this.activo = usuario.isActivo();
        this.createdAt = usuario.getCreatedAt();
    }

    public String getId() { return id; }
    public String getUsername() { return username; }
    public String getNombre() { return nombre; }
    public String getApellidoPaterno() { return apellidoPaterno; }
    public String getApellidoMaterno() { return apellidoMaterno; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public String getCorreo() { return correo; }
    public String getTelefono() { return telefono; }
    public Rol getRol() { return rol; }
    public boolean isActivo() { return activo; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}