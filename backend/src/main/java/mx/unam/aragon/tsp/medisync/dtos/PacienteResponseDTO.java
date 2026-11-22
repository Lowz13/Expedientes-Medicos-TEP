package mx.unam.aragon.tsp.medisync.dtos;

import java.time.LocalDate;
import mx.unam.aragon.tsp.medisync.models.Paciente;

public class PacienteResponseDTO {

    private String id;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private LocalDate fechaNacimiento;
    private String telefono;
    private String correo;
    private String alergias;
    private String antecedentes;

    public PacienteResponseDTO() {
    }
    public PacienteResponseDTO(Paciente paciente) {
        this.id = paciente.getId();
        this.nombre = paciente.getNombre();
        this.apellidoPaterno = paciente.getApellidoPaterno();
        this.apellidoMaterno = paciente.getApellidoMaterno();
        this.fechaNacimiento = paciente.getFechaNacimiento();
        this.telefono = paciente.getTelefono();
        this.correo = paciente.getCorreo();
        this.alergias = paciente.getAlergias();
        this.antecedentes = paciente.getAntecedentes();
    }
    //Getters and Setters
    public String getId() {return id;}
    public void setId(String id) {this.id = id;}
    
    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getApellidoPaterno() {return apellidoPaterno;}
    public void setApellidoPaterno(String apellidoPaterno) {this.apellidoPaterno = apellidoPaterno;}

    public String getApellidoMaterno() {return apellidoMaterno;}
    public void setApellidoMaterno(String apellidoMaterno) {this.apellidoMaterno = apellidoMaterno;}

    public LocalDate getFechaNacimiento() {return fechaNacimiento;}
    public void setFechaNacimiento(LocalDate fechaNacimiento) {this.fechaNacimiento = fechaNacimiento;}

    public String getTelefono() {return telefono;}
    public void setTelefono(String telefono) {this.telefono = telefono;}

    public String getCorreo() {return correo;}
    public void setCorreo(String correo) {this.correo = correo;}

    public String getAlergias() {return alergias;}
    public void setAlergias(String alergias) {this.alergias = alergias;}
    
    public String getAntecedentes() {return antecedentes;}
    public void setAntecedentes(String antecedentes) {this.antecedentes = antecedentes;}
}