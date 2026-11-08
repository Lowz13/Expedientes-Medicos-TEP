package mx.unam.aragon.tsp.medisync.expedientes.dtos;

public class PacienteDto {
    private String id;
    private String nombre;
    private String historialMedico; // Resumen simple/expediente

    public PacienteDto() {}

    public PacienteDto(String id, String nombre, String historialMedico) {
        this.id = id;
        this.nombre = nombre;
        this.historialMedico = historialMedico;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getHistorialMedico() { return historialMedico; }
    public void setHistorialMedico(String historialMedico) { this.historialMedico = historialMedico; }
}
