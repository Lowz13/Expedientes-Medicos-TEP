package mx.unam.aragon.tsp.medisync.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "consultas") // Define el nombre de la colección en MongoDB
public class Consulta {

    @Id
    private String id; // Identificador único de la consulta generado por MongoDB
    
    private String pacienteId;   // ID del paciente al que pertenece la consulta
    private String medicoId;     // ID del médico que realizó la consulta
    private LocalDateTime fechaConsulta; // Fecha y hora en que se llevó a cabo
    private String diagnostico;  // Diagnóstico médico
    private String tratamiento;  // Tratamiento o receta indicada
    private String notasMedicas; // Notas adicionales u observaciones clínicas

    // Constructores vacíos y con parámetros
    public Consulta() {}

    public Consulta(String pacienteId, String medicoId, LocalDateTime fechaConsulta, String diagnostico, String tratamiento, String notasMedicas) {
        this.pacienteId = pacienteId;
        this.medicoId = medicoId;
        this.fechaConsulta = fechaConsulta;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.notasMedicas = notasMedicas;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPacienteId() { return pacienteId; }
    public void setPacienteId(String pacienteId) { this.pacienteId = pacienteId; }

    public String getMedicoId() { return medicoId; }
    public void setMedicoId(String medicoId) { this.medicoId = medicoId; }

    public LocalDateTime getFechaConsulta() { return fechaConsulta; }
    public void setFechaConsulta(LocalDateTime fechaConsulta) { this.fechaConsulta = fechaConsulta; }

    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }

    public String getTratamiento() { return tratamiento; }
    public void setTratamiento(String tratamiento) { this.tratamiento = tratamiento; }

    public String getNotasMedicas() { return notasMedicas; }
    public void setNotasMedicas(String notasMedicas) { this.notasMedicas = notasMedicas; }
}