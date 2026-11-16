package mx.unam.aragon.tsp.medisync.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import mx.unam.aragon.tsp.medisync.models.Medico;

@Repository 
public interface MedicoRepository extends MongoRepository<Medico, String> {
    Optional<Medico> findByUsuarioId(String usuarioId);
    Optional<Medico> findByCedulaProfesional(String cedulaProfesional);
    boolean existsByCedulaProfesional(String cedulaProfesional);
    List<Medico> findByEspecialidadContainingIgnoreCase(String especialidad);
    List<Medico> findByActivoTrue();
}