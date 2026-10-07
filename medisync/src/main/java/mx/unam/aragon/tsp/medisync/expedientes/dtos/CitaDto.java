package mx.unam.aragon.tsp.medisync.expedientes.dtos;

public class CitaDto {
private String id;
    private String pacienteId;
    private String fechaHora;
    private String correoPaciente;

    public CitaDto() {}

    public CitaDto(String id, String pacienteId, String fechaHora, String correoPaciente) {
        this.id = id;
        this.pacienteId = pacienteId;
        this.fechaHora = fechaHora;
        this.correoPaciente = correoPaciente;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPacienteId() { return pacienteId; }
    public void setPacienteId(String pacienteId) { this.pacienteId = pacienteId; }

    public String getFechaHora() { return fechaHora; }
    public void setFechaHora(String fechaHora) { this.fechaHora = fechaHora; }

    public String getCorreoPaciente() { return correoPaciente; }
    public void setCorreoPaciente(String correoPaciente) { this.correoPaciente = correoPaciente; }
}