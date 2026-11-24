package mx.unam.aragon.tsp.medisync.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import mx.unam.aragon.tsp.medisync.dtos.MedicoRequestDTO;
import mx.unam.aragon.tsp.medisync.dtos.MedicoResponseDTO;
import mx.unam.aragon.tsp.medisync.services.MedicoService;

@RestController
@RequestMapping("/api/medicos")
public class MedicoController {

    private final MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MedicoResponseDTO> crearMedico(@Valid @RequestBody MedicoRequestDTO request) {
        MedicoResponseDTO creado = medicoService.crearMedico(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping
    @PreAuthorize ("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    public ResponseEntity<List<MedicoResponseDTO>> listarMedicos(
            @RequestParam(required = false) String especialidad) {
        return ResponseEntity.ok(medicoService.listarMedicos(especialidad));
    }

    @GetMapping("/{id}")
    @PreAuthorize ("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    public ResponseEntity<MedicoResponseDTO> obtenerMedicoPorId(@PathVariable String id) {
        return ResponseEntity.ok(medicoService.buscarMedicoPorId(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize ("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    public ResponseEntity<MedicoResponseDTO> actualizarMedico(
            @PathVariable String id,
            @Valid @RequestBody MedicoRequestDTO request) {
        return ResponseEntity.ok(medicoService.actualizarMedico(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize ("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> eliminarMedico(@PathVariable String id) {
        medicoService.eliminarMedico(id);
        return ResponseEntity.noContent().build();
    }
}