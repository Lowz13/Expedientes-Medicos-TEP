package mx.unam.aragon.tsp.medisync.services;

import mx.unam.aragon.tsp.medisync.models.Cita;
import mx.unam.aragon.tsp.medisync.repositories.CitaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CitaService {

    private final CitaRepository citaRepository;
    private final CorreoService correoService;

    // Inyección de dependencias por constructor
    public CitaService(CitaRepository citaRepository, CorreoService correoService) {
        this.citaRepository = citaRepository;
        this.correoService = correoService;
    }

    // Registrar una nueva cita y enviar correo de confirmación
    public Cita crearCita(Cita cita) {
        Cita nuevaCita = citaRepository.save(cita);
        
        try {
            String cuerpoMensaje = "Hola, tu cita médica ha sido registrada exitosamente.\n\n" +
                                   "Detalles de la cita:\n" +
                                   "- ID de Cita: " + nuevaCita.getId() + "\n" +
                                   "- Fecha y Hora: " + nuevaCita.getFechaHora() + "\n" +
                                   "- ID de Paciente: " + nuevaCita.getPacienteId() + "\n\n" +
                                   "¡Gracias por usar MediSync!";
            
            correoService.enviarCorreo(
                "death888alucard@gmail.com", 
                "Confirmación de Cita Médica - MediSync", 
                cuerpoMensaje
            );
        } catch (Exception e) {
            System.err.println("Advertencia: No se pudo enviar el correo de confirmación: " + e.getMessage());
        }

        return nuevaCita;
    }

    // Consultar todas las citas registradas
    public List<Cita> obtenerTodasLasCitas() {
        return citaRepository.findAll();
    }

    // Consultar una cita específica por su ID
    public Optional<Cita> obtenerCitaPorId(String id) {
        return citaRepository.findById(id);
    }

    // Método corregido: devuelve la Cita eliminada para cumplir con el controlador
    public Cita cancelarCita(String id) {
        Optional<Cita> citaOpt = citaRepository.findById(id);
        if (citaOpt.isPresent()) {
            Cita cita = citaOpt.get();
            citaRepository.deleteById(id);
            return cita;
        } else {
            throw new RuntimeException("Cita no encontrada con el ID: " + id);
        }
    }
}