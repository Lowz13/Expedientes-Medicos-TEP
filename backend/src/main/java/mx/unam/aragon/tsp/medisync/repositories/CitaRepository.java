package mx.unam.aragon.tsp.medisync.repositories;

import mx.unam.aragon.tsp.medisync.models.Cita;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitaRepository extends MongoRepository<Cita, String> {
    // MongoRepository ya incluye métodos como save(), findAll(), findById(), deleteById()
}