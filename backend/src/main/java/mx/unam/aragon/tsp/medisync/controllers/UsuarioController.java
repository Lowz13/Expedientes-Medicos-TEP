package mx.unam.aragon.tsp.medisync.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import mx.unam.aragon.tsp.medisync.dtos.UsuarioRequestDto;
import mx.unam.aragon.tsp.medisync.dtos.UsuarioResponseDto;
import mx.unam.aragon.tsp.medisync.services.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
@PreAuthorize("hasRole('ADMIN')")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> crearUsuario(@Valid @RequestBody UsuarioRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.crearUsuario(request));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuariosActivos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivarUsuario(@PathVariable String id) {
        usuarioService.desactivarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}