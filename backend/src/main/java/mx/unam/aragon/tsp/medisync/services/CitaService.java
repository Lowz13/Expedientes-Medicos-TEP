package mx.unam.aragon.tsp.medisync.services;

import mx.unam.aragon.tsp.medisync.models.Cita;
import mx.unam.aragon.tsp.medisync.repositories.CitaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CitaService {

    private final CitaRepository citaRepository;

    // Inyección de dependencias por constructor
    public CitaService(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    // Registrar una nueva cita
    public Cita crearCita(Cita cita) {
        return citaRepository.save(cita);
    }

    // Consultar todas las citas registradas
    public List<Cita> obtenerTodasLasCitas() {
        return citaRepository.findAll();
    }

    // Consultar una cita específica por su ID
    public Optional<Cita> obtenerCitaPorId(String id) {
        return citaRepository.findById(id);
    }
}
