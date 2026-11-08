package mx.unam.aragon.tsp.medisync.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {
    private static final Logger log = LoggerFactory.getLogger(MongoConfig.class);

    @Bean
    public CommandLineRunner init(MongoTemplate mongoTemplate) {
        return args -> {
            try {
                String dbName = mongoTemplate.getDb().getName();
                log.info("=========================================================");
                log.info(">>> Conexión EXITOSA a Mongo DB");
                log.info("Base de datos activa : {}", dbName);
                log.info("=========================================================");
            } catch (Exception e) {
                log.error("=========================================================");
                log.error(">>> ERROR al inicializar Mongo DB");
                log.error("Detalle del error: {}", e.getMessage());
                log.error("=========================================================");
            }
        };
    }
}
