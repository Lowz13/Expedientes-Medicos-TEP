package mx.unam.aragon.tsp.medisync.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "citas")
public class Cita {

    @Id
    private String id;
    
    private String pacienteId; 
    private String medicoId;   
    private LocalDateTime fechaHora; 
    
    // Variable agregada aquí arriba, con las demás
    private String estado = "ACTIVA"; 

    // Constructores
    public Cita() {}

    public Cita(String pacienteId, String medicoId, LocalDateTime fechaHora) {
        this.pacienteId = pacienteId;
        this.medicoId = medicoId;
        this.fechaHora = fechaHora;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPacienteId() { return pacienteId; }
    public void setPacienteId(String pacienteId) { this.pacienteId = pacienteId; }

    public String getMedicoId() { return medicoId; }
    public void setMedicoId(String medicoId) { this.medicoId = medicoId; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    // Getters y Setters del estado aquí abajo
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
}