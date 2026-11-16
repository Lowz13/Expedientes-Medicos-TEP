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
    private String motivo; // Motivo de la cita, opcional
    
    // Variable agregada aquí arriba, con las demás
    private EstadoCita estado = EstadoCita.PROGRAMADA; // Estado de la cita: PROGRAMADA, CANCELADA, ATENDIDA

    // Constructores
    public Cita() {}

    public Cita(String pacienteId, String medicoId, LocalDateTime fechaHora, String motivo, EstadoCita estado) {
        this.pacienteId = pacienteId;
        this.medicoId = medicoId;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.estado = EstadoCita.PROGRAMADA;
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

    public String getMotivo() { return motivo;}
    public void setMotivo(String motivo) { this.motivo = motivo; }
    
    public EstadoCita getEstado() { return estado; }
    public void setEstado(EstadoCita estado) { this.estado = estado; }
    
}