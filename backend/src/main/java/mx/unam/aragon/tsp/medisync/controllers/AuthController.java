package mx.unam.aragon.tsp.medisync.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mx.unam.aragon.tsp.medisync.dtos.AuthResponseDto;
import mx.unam.aragon.tsp.medisync.dtos.LoginDto;
import mx.unam.aragon.tsp.medisync.dtos.RegistroDto;
import mx.unam.aragon.tsp.medisync.models.Usuario;
import mx.unam.aragon.tsp.medisync.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@Valid @RequestBody RegistroDto dto) {
        Usuario creado = authService.registrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "mensaje", "Usuario registrado correctamente",
                "id", creado.getId(),
                "username", creado.getUsername()
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginDto dto) {
        return ResponseEntity.ok(authService.login(dto));
    }
}