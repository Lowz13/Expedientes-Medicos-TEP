package mx.unam.aragon.tsp.medisync.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

// Importaciones necesarias para la configuración de MongoDB y la creación del usuario administrador
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Value;
import mx.unam.aragon.tsp.medisync.models.Rol;
import mx.unam.aragon.tsp.medisync.models.Usuario;
import mx.unam.aragon.tsp.medisync.repositories.UsuarioRepository;

@Configuration
public class MongoConfig {

    private static final Logger log = LoggerFactory.getLogger(MongoConfig.class);
    // Valores de configuración para el usuario administrador, con valores por defecto
    @Value("${app.admin.username:administrador}")
    private String adminUsername;

    @Value("${app.admin.password:1234}")
    private String adminPassword;

    @Bean
    //metodo para iniciar un usuario administrador en la base de datos si no existe
    public CommandLineRunner init(
            MongoTemplate mongoTemplate,
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            try {
                String dbName = mongoTemplate.getDb().getName();
                log.info("====================================================");
                log.info(">>> Conexión EXITOSA a Mongo DB");
                log.info("Base de datos activa : {}", dbName);
                // Verifica si el usuario administrador ya existe en la base de datos
                if (usuarioRepository.findByUsername(adminUsername).isEmpty()) {
                    Usuario admin = new Usuario();
                    admin.setNombre("admin");
                    admin.setUsername(adminUsername);
                    admin.setCorreo("admin@admin.com");
                    admin.setActivo(true);
                    admin.setPasswordHash(passwordEncoder.encode(adminPassword));
                    admin.setRol(Rol.ADMIN);

                    usuarioRepository.save(admin);
                    log.info(">>> Usuario administrador creado: {}", adminUsername);
                    log.info(">>> Contraseña del administrador: {}", adminPassword);
                } else {
                    log.info(">>> Usuario administrador '{}' ya existe.", adminUsername);
                    log.info(">>> Contraseña del administrador: {}", adminPassword); 
                }
                // Log de éxito de la conexión a MongoDB
                log.info("====================================================");
                
            } catch (Exception e) {
                log.error("====================================================");
                log.error(">>> ERROR al inicializar Mongo DB");
                log.error("Detalle del error: {}", e.getMessage());
                log.error("====================================================");
            }
        };
    }
}
