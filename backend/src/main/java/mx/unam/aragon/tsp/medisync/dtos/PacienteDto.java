package mx.unam.aragon.tsp.medisync.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;

public class PacienteDto {
    @NotNull(message = "El ID es obligatorio")
    private String id;
    @NotBlank(message = "El nombre es obligatorio")
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
