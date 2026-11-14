package mx.unam.aragon.tsp.medisync.services;

import mx.unam.aragon.tsp.medisync.models.Consulta;
import mx.unam.aragon.tsp.medisync.repositories.ConsultaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepository;

    // Inyección de dependencias por constructor
    public ConsultaService(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    // Registrar una nueva consulta clínica
    public Consulta crearConsulta(Consulta consulta) {
        return consultaRepository.save(consulta);
    }

    // Consultar el historial clínico de un paciente específico
    public List<Consulta> obtenerHistorialPorPaciente(String pacienteId) {
        return consultaRepository.findByPacienteId(pacienteId);
    }

    // Consultar una consulta individual por su ID
    public Optional<Consulta> obtenerConsultaPorId(String id) {
        return consultaRepository.findById(id);
    }
}