package mx.unam.aragon.tsp.medisync.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import mx.unam.aragon.tsp.medisync.dtos.PacienteRequestDTO;
import mx.unam.aragon.tsp.medisync.dtos.PacienteResponseDTO;
import mx.unam.aragon.tsp.medisync.services.PacienteService;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {
private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping
    public ResponseEntity<PacienteResponseDTO> crearPaciente(@Valid @RequestBody PacienteRequestDTO request) {
        PacienteResponseDTO creado = pacienteService.crearPaciente(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping
    public ResponseEntity<List<PacienteResponseDTO>> listarPacientes(
            @RequestParam(required = false) String busqueda) {
        return ResponseEntity.ok(pacienteService.listarPacientes(busqueda));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> obtenerPacientePorId(@PathVariable String id) {
        return ResponseEntity.ok(pacienteService.buscarPacientePorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> actualizarPaciente(
            @PathVariable String id,
            @Valid @RequestBody PacienteRequestDTO request) {
        return ResponseEntity.ok(pacienteService.actualizarPaciente(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPaciente(@PathVariable String id) {
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.noContent().build();
    }
}