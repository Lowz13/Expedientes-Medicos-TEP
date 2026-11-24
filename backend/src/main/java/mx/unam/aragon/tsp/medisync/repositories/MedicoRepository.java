package mx.unam.aragon.tsp.medisync.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import mx.unam.aragon.tsp.medisync.models.Medico;

@Repository
public interface MedicoRepository extends MongoRepository<Medico, String> {

    Optional<Medico> findByIdAndActivoTrue(String id);

    Optional<Medico> findByUsuarioId(String usuarioId);

    boolean existsByCedulaProfesional(String cedulaProfesional);

    Optional<Medico> findByCedulaProfesional(String cedulaProfesional);

    List<Medico> findByActivoTrue();

    List<Medico> findByActivoTrueAndEspecialidadIgnoreCase(String especialidad);
}