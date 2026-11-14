package mx.unam.aragon.tsp.medisync.repositories;

import mx.unam.aragon.tsp.medisync.models.Medico;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface MedicoRepository extends MongoRepository<Medico, String> {
    Optional<Medico> findByUsuarioId(String usuarioId);
}