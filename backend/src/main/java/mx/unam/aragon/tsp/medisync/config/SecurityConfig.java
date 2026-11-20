package mx.unam.aragon.tsp.medisync.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; // 1. Importa la anotación
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true) // 2. Agrégala aquí con esta propiedad opcional pero recomendada
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF si usas APIs REST / JWT
            .authorizeHttpRequests(auth -> auth
                // Aquí defines tus rutas públicas si las hay, por ejemplo:
                .requestMatchers("/api/auth/**").permitAll() 
                // Todas las demás requerirán autenticación y los roles los manejará @PreAuthorize
                .anyRequest().authenticated()
            );
            
        return http.build();
    }
    
}