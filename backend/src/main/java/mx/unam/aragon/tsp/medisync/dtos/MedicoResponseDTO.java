package mx.unam.aragon.tsp.medisync.dtos;

import java.util.List;
import mx.unam.aragon.tsp.medisync.models.Medico;

public class MedicoResponseDTO {

    private String id;
    private String usuarioId;
    private String username;
    private String correo;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String telefono;
    private String especialidad;
    private String cedulaProfesional;
    private List<String> horarios;
    private boolean activo;

    public MedicoResponseDTO() {}

    public MedicoResponseDTO(Medico medico, String username, String correo) {
        this.id = medico.getId();
        this.usuarioId = medico.getUsuarioId();
        this.username = username;
        this.correo = correo;
        this.nombre = medico.getNombre();
        this.apellidoPaterno = medico.getApellidoPaterno();
        this.apellidoMaterno = medico.getApellidoMaterno();
        this.telefono = medico.getTelefono();
        this.especialidad = medico.getEspecialidad();
        this.cedulaProfesional = medico.getCedulaProfesional();
        this.horarios = medico.getHorarios();
        this.activo = medico.isActivo();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidoPaterno() { return apellidoPaterno; }
    public void setApellidoPaterno(String apellidoPaterno) { this.apellidoPaterno = apellidoPaterno; }

    public String getApellidoMaterno() { return apellidoMaterno; }
    public void setApellidoMaterno(String apellidoMaterno) { this.apellidoMaterno = apellidoMaterno; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public String getCedulaProfesional() { return cedulaProfesional; }
    public void setCedulaProfesional(String cedulaProfesional) { this.cedulaProfesional = cedulaProfesional; }

    public List<String> getHorarios() { return horarios; }
    public void setHorarios(List<String> horarios) { this.horarios = horarios; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}