package mx.unam.aragon.tsp.medisync.repositories;

import mx.unam.aragon.tsp.medisync.models.Paciente;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PacienteRepository extends MongoRepository<Paciente, String> {
    // Hereda métodos como save(), findAll(), findById(), deleteById(), etc.
}