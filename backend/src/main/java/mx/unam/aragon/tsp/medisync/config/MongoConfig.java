package mx.unam.aragon.tsp.medisync.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import mx.unam.aragon.tsp.medisync.models.Rol;
import mx.unam.aragon.tsp.medisync.models.Usuario;
import mx.unam.aragon.tsp.medisync.repositories.UsuarioRepository;

@Configuration
public class MongoConfig {

    private static final Logger log = LoggerFactory.getLogger(MongoConfig.class);

    @Value("${app.admin.username}")
    private String adminUsername;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Value("${app.recepcion.username}")
    private String recepcionUsername;

    @Value("${app.recepcion.password}")
    private String recepcionPassword;

    @Bean
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

                String adminUserLimpio = adminUsername.trim().toLowerCase(Locale.ROOT);
                if (usuarioRepository.findByUsername(adminUserLimpio).isEmpty()) {
                    Usuario admin = new Usuario();
                    admin.setUsername(adminUserLimpio);
                    admin.setNombre("Administrador");
                    admin.setApellidoPaterno("General");
                    admin.setApellidoMaterno("Sistema");
                    admin.setFechaNacimiento(LocalDate.of(1990, 1, 1));
                    admin.setCorreo("admin@admin.com");
                    admin.setTelefono("5500000000");
                    admin.setActivo(true);
                    admin.setPasswordHash(passwordEncoder.encode(adminPassword));
                    admin.setRol(Rol.ADMIN);
                    admin.setCreatedAt(LocalDateTime.now());

                    usuarioRepository.save(admin);
                    log.info(">>> Usuario administrador creado: {}", adminUserLimpio);
                } else {
                    log.info(">>> Usuario administrador '{}' ya existe.", adminUserLimpio);
                }

                String recepUserLimpio = recepcionUsername.trim().toLowerCase(Locale.ROOT);
                if (usuarioRepository.findByUsername(recepUserLimpio).isEmpty()) {
                    Usuario recepcionista = new Usuario();
                    recepcionista.setUsername(recepUserLimpio);
                    recepcionista.setNombre("Laura");
                    recepcionista.setApellidoPaterno("Morales");
                    recepcionista.setApellidoMaterno("García");
                    recepcionista.setFechaNacimiento(LocalDate.of(1996, 4, 12));
                    recepcionista.setCorreo("recepcion@medisync.local");
                    recepcionista.setTelefono("5511223344");
                    recepcionista.setActivo(true);
                    recepcionista.setPasswordHash(passwordEncoder.encode(recepcionPassword));
                    recepcionista.setRol(Rol.RECEPCIONISTA);
                    recepcionista.setCreatedAt(LocalDateTime.now());

                    usuarioRepository.save(recepcionista);
                    log.info(">>> Usuario recepcionista creado: {}", recepUserLimpio);
                } else {
                    log.info(">>> Usuario recepcionista '{}' ya existe.", recepUserLimpio);
                }

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