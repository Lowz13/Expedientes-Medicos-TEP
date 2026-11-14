package mx.unam.aragon.tsp.medisync.repositories;

import mx.unam.aragon.tsp.medisync.models.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    // Método personalizado para buscar usuarios por su correo único
    Optional<Usuario> findByCorreo(String correo);
}