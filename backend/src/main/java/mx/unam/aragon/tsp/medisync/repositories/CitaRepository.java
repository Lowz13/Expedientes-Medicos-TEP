package mx.unam.aragon.tsp.medisync.repositories;

import mx.unam.aragon.tsp.medisync.models.Cita;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepository extends MongoRepository<Cita, String> {
    
    // MongoRepository ya incluye métodos como save(), findAll(), findById(), deleteById()
    
    // Método personalizado: Spring genera la consulta automáticamente buscando por estos 3 campos
    List<Cita> findByMedicoIdAndFechaHoraAndEstado(String medicoId, LocalDateTime fechaHora, String estado);
}