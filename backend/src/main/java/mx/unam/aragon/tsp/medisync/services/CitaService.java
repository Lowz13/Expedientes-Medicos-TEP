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

    // Registrar una nueva cita (VERSIÓN OPTIMIZADA)
    public Cita crearCita(Cita cita) {
        // 1. Buscamos directamente en MongoDB si hay conflicto, sin traer toda la colección
        List<Cita> citasExistentes = citaRepository.findByMedicoIdAndFechaHoraAndEstado(
                cita.getMedicoId(), 
                cita.getFechaHora(), 
                "ACTIVA"
        );
        
        // 2. Si la lista NO está vacía, significa que ya hay una cita en ese horario
        if (!citasExistentes.isEmpty()) {
            throw new RuntimeException("El médico ya tiene una cita agendada en ese horario.");
        }
        
        // 3. Si está libre, le asignamos el estado y guardamos
        cita.setEstado("ACTIVA");
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

    // Cancelar una cita existente (NUEVO MÉTODO)
    public Cita cancelarCita(String id) {
        Optional<Cita> citaOpt = citaRepository.findById(id);
        
        // Si la cita existe en la base de datos...
        if (citaOpt.isPresent()) {
            Cita cita = citaOpt.get(); // La extraemos del Optional
            cita.setEstado("CANCELADA"); // Cambiamos su estado
            return citaRepository.save(cita); // Guardamos los cambios
        }
        
        // Si el Optional está vacío (no se encontró el ID), lanzamos el error
        throw new RuntimeException("Cita no encontrada con el ID proporcionado.");
    }
}