package mx.unam.aragon.tsp.medisync.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "usuarios") // Indica que esta clase se mapea a la colección "usuarios" en MongoDB
public class Usuario {

    @Id
    private String id;
    private String nombre;

    @Indexed(unique = true) // Asegura que el correo sea único en la base de datos[cite: 4]
    private String correo;
    
    private String passwordHash;
    private Rol rol; // ADMIN, RECEPCIONISTA, MEDICO[cite: 4]
    private boolean activo = true;
    private LocalDateTime createdAt = LocalDateTime.now();

    // Constructores vacíos y con parámetros
    
    // Getters y Setters (necesarios para que Spring Boot pueda leer y escribir los datos)
    public String getId() { return id; }
    public Usuario(String nombre, String correo, String passwordHash, Rol rol, boolean activo) {
        this.nombre = nombre;
        this.correo = correo;
        this.passwordHash = passwordHash;
        this.rol = rol;
        this.activo = activo;
    }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}