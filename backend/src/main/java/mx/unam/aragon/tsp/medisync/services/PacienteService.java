package mx.unam.aragon.tsp.medisync.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

import mx.unam.aragon.tsp.medisync.repositories.PacienteRepository;
import mx.unam.aragon.tsp.medisync.dtos.PacienteRequestDTO;
import mx.unam.aragon.tsp.medisync.dtos.PacienteResponseDTO;
import mx.unam.aragon.tsp.medisync.models.Paciente;

@Service 
public class PacienteService {
    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public PacienteResponseDTO crearPaciente(PacienteRequestDTO dto){
        String correoNormalizado = dto.getCorreo() == null ? null : dto.getCorreo().trim();
        if (correoNormalizado != null && !correoNormalizado.isBlank() &&
            pacienteRepository.existsByCorreoAndActivoTrue(correoNormalizado)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo electrónico ya está registrado");
            }
        Paciente paciente = new Paciente();
        paciente.setNombre(dto.getNombre().trim());
        paciente.setApellidoPaterno(dto.getApellidoPaterno().trim());
        paciente.setApellidoMaterno(dto.getApellidoMaterno().trim());
        paciente.setFechaNacimiento(dto.getFechaNacimiento());
        paciente.setTelefono(dto.getTelefono().trim());
        paciente.setCorreo(dto.getCorreo() != null ? dto.getCorreo().trim() : null);
        paciente.setAlergias(dto.getAlergias() != null ? dto.getAlergias().trim() : null);
        paciente.setAntecedentes(dto.getAntecedentes());
        paciente.setActivo(true);
            
        Paciente guardado = pacienteRepository.save(paciente);
        return new PacienteResponseDTO(guardado);
    }

    public List<PacienteResponseDTO> listarPacientes(String busqueda){
        List<Paciente> pacientes;
        if (busqueda != null && !busqueda.isBlank()) {
            String termino = busqueda.trim();
            pacientes = pacienteRepository.
            findByActivoTrueAndNombreContainingIgnoreCaseOrActivoTrueAndApellidoPaternoContainingIgnoreCaseAndActivoTrueAndApellidoMaternoContainingIgnoreCase(termino, termino, termino);
        } else {
            pacientes = pacienteRepository.findByActivoTrue();
        }
        return pacientes.stream()
                .map(PacienteResponseDTO::new)
                .toList();
    }

    public PacienteResponseDTO buscarPacientePorId(String id){
        Paciente paciente = pacienteRepository.findByIdAndActivoTrue(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente no encontrado"));
        return new PacienteResponseDTO(paciente);
    }

    public PacienteResponseDTO actualizarPaciente(String id, PacienteRequestDTO dto){
        Paciente paciente = pacienteRepository.findByIdAndActivoTrue(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente no encontrado"));
        
        if (dto.getCorreo() != null && !dto.getCorreo().isBlank()){
            pacienteRepository.findByCorreoAndActivoTrue(dto.getCorreo().trim()).ifPresent(existente-> {
                if (!existente.getId().equals(id)) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo electrónico ya está registrado");
                }
            });
        }

        paciente.setNombre(dto.getNombre().trim());
        paciente.setApellidoPaterno(dto.getApellidoPaterno().trim());
        paciente.setApellidoMaterno(dto.getApellidoMaterno().trim());
        paciente.setFechaNacimiento(dto.getFechaNacimiento());
        paciente.setTelefono(dto.getTelefono().trim());
        paciente.setCorreo(dto.getCorreo() != null ? dto.getCorreo().trim() : null);
        paciente.setAlergias(dto.getAlergias() != null ? dto.getAlergias().trim() : null);
        paciente.setAntecedentes(dto.getAntecedentes());

        Paciente actualizado = pacienteRepository.save(paciente);
        return new PacienteResponseDTO(actualizado);
    }

    public void eliminarPaciente(String id){
        Paciente paciente = pacienteRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente no encontrado"));

        paciente.setActivo(false);
        pacienteRepository.save(paciente);
    }
}
