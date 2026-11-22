package mx.unam.aragon.tsp.medisync.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import mx.unam.aragon.tsp.medisync.models.Paciente;

@Repository 
public interface PacienteRepository extends MongoRepository<Paciente, String> {
    // Hereda métodos como save(), findAll(), findById(), deleteById(), etc.
    Optional<Paciente> findByIdAndActivoTrue(String id);

    List<Paciente> findByActivoTrue();

    Optional<Paciente> findByCorreoAndActivoTrue(String email);

    boolean existsByCorreoAndActivoTrue(String email);

    List<Paciente> findByActivoTrueAndNombreContainingIgnoreCaseOrActivoTrueAndApellidoPaternoContainingIgnoreCaseAndActivoTrueAndApellidoMaternoContainingIgnoreCase(
            String nombre, String apellidoPaterno, String apellidoMaterno);
    
    

  
    
  
}