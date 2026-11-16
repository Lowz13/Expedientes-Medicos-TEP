package mx.unam.aragon.tsp.medisync.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import mx.unam.aragon.tsp.medisync.models.Paciente;

@Repository 
public interface PacienteRepository extends MongoRepository<Paciente, String> {
    // Hereda métodos como save(), findAll(), findById(), deleteById(), etc.
    Optional<Paciente> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
    List<Paciente> findByNombreContainingIgnoreCaseOrApellidoPaternoContainingIgnoreCaseOrApellidoMaternoContainingIgnoreCase(String nombre, String apellidoPaterno, String apellidoMaterno);
}