package mx.unam.aragon.tsp.medisync.repositories;

import mx.unam.aragon.tsp.medisync.models.Consulta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultaRepository extends MongoRepository<Consulta, String> {
    
    // Método personalizado para encontrar todas las consultas asociadas a un paciente específico (Historial Clínico)
    List<Consulta> findByPacienteId(String pacienteId);
}