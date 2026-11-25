package mx.unam.aragon.tsp.medisync.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import mx.unam.aragon.tsp.medisync.dtos.LoginRequestDTO;
import mx.unam.aragon.tsp.medisync.dtos.LoginResponseDto;
import mx.unam.aragon.tsp.medisync.services.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout() {
        // Al usar JWT stateless, el cliente descarta el token; este endpoint confirma el cierre en el flujo
        return ResponseEntity.ok(Map.of("mensaje", "Sesión cerrada correctamente"));
    }
}