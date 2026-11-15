package mx.unam.aragon.tsp.medisync.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import mx.unam.aragon.tsp.medisync.models.Rol;
import mx.unam.aragon.tsp.medisync.models.Usuario;

@Repository 
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    // Método personalizado para buscar usuarios por su correo único
    Optional<Usuario> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
    List<Usuario> findByRol(Rol rol);
    List<Usuario> findByActivoTrue();
}