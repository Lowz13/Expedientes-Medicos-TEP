package mx.unam.aragon.tsp.medisync.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usuarios")
public class Usuario {

    @Id
    private String id;

    @Indexed(unique = true)
    private String username;

    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private LocalDate fechaNacimiento;

    @Indexed(unique = true)
    private String correo;

    private String passwordHash;
    private String telefono;
    private Rol rol;
    private boolean activo = true;
    private LocalDateTime createdAt = LocalDateTime.now();

    public Usuario() {
    }

    public Usuario(String id, String username, String nombre, String apellidoPaterno, String apellidoMaterno,
            LocalDate fechaNacimiento, String correo, String passwordHash, String telefono, Rol rol, boolean activo,
            LocalDateTime createdAt) {
        this.id = id;
        setUsername(username);
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fechaNacimiento = fechaNacimiento;
        setCorreo(correo);
        this.passwordHash = passwordHash;
        this.telefono = telefono;
        this.rol = rol;
        this.activo = activo;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim().toLowerCase(Locale.ROOT);
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidoPaterno() { return apellidoPaterno; }
    public void setApellidoPaterno(String apellidoPaterno) { this.apellidoPaterno = apellidoPaterno; }

    public String getApellidoMaterno() { return apellidoMaterno; }
    public void setApellidoMaterno(String apellidoMaterno) { this.apellidoMaterno = apellidoMaterno; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) {
        this.correo = correo == null ? null : correo.trim().toLowerCase(Locale.ROOT);
    }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}