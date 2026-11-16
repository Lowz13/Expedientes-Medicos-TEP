package mx.unam.aragon.tsp.medisync.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Document(collection = "pacientes")
public class Paciente {

    @Id
    private String id;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private LocalDate fechaNacimiento;
    private String correo;
    private String telefono;
    private String antecedentes; // Proteger información clínica
    private String alergias;
    private boolean activo = true;

    
    public Paciente(String id, String nombre, String apellidoPaterno, String apellidoMaterno, LocalDate fechaNacimiento,
            String correo, String telefono, String antecedentes, String alergias) {
        this.id = id;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fechaNacimiento = fechaNacimiento;
        this.correo = correo;
        this.telefono = telefono;
        this.antecedentes = antecedentes;
        this.alergias = alergias;
    }
    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidoPaterno() {return apellidoPaterno;}
    public void setApellidoPaterno(String apellidoPaterno) {this.apellidoPaterno = apellidoPaterno;}
    
    public String getApellidoMaterno() {return apellidoMaterno;}
    public void setApellidoMaterno(String apellidoMaterno) {this.apellidoMaterno = apellidoMaterno;}
    
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getAntecedentes() { return antecedentes; }
    public void setAntecedentes(String antecedentes) { this.antecedentes = antecedentes; }

    public String getAlergias() { return alergias; }
    public void setAlergias(String alergias) { this.alergias = alergias; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}