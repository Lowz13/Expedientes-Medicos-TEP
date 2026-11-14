package mx.unam.aragon.tsp.medisync.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "citas") // Define el nombre de la colección en MongoDB
public class Cita {

    @Id
    private String id; // Identificador único de la cita generado por MongoDB
    
    private String pacienteId; // ID del paciente (referencia al módulo de Backend A)
    private String medicoId;   // ID del médico (referencia al módulo correspondiente)
    private LocalDateTime fechaHora; // Fecha y hora programada para la cita

    // Constructores vacíos y con parámetros
    public Cita() {}

    public Cita(String pacienteId, String medicoId, LocalDateTime fechaHora) {
        this.pacienteId = pacienteId;
        this.medicoId = medicoId;
        this.fechaHora = fechaHora;
    }

    // Getters y Setters (necesarios para que Spring Boot convierta los datos a JSON)
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPacienteId() { return pacienteId; }
    public void setPacienteId(String pacienteId) { this.pacienteId = pacienteId; }

    public String getMedicoId() { return medicoId; }
    public void setMedicoId(String medicoId) { this.medicoId = medicoId; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
}