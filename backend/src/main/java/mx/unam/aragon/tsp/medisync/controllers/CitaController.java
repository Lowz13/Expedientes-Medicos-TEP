package mx.unam.aragon.tsp.medisync.controllers;

import mx.unam.aragon.tsp.medisync.models.Cita;
import mx.unam.aragon.tsp.medisync.services.CitaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas") // Ruta base para todas las peticiones de este controlador
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    // POST: Registrar una nueva cita
    // Solo ADMIN y RECEPCIONISTA pueden registrar citas
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    public ResponseEntity<Cita> crearCita(@RequestBody Cita cita) {
        Cita nuevaCita = citaService.crearCita(cita);
        return new ResponseEntity<>(nuevaCita, HttpStatus.CREATED);
    }

    // GET: Consultar todas las citas
    // Accesible para ADMIN, RECEPCIONISTA y MEDICO
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA', 'MEDICO')")
    public ResponseEntity<List<Cita>> listarCitas() {
        List<Cita> citas = citaService.obtenerTodasLasCitas();
        return ResponseEntity.ok(citas);
    }

    // GET: Consultar una cita por ID
    // Accesible para ADMIN, RECEPCIONISTA y MEDICO
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA', 'MEDICO')")
    public ResponseEntity<Cita> obtenerCitaPorId(@PathVariable String id) {
        return citaService.obtenerCitaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PATCH: Cancelar una cita por ID
    // Solo ADMIN y RECEPCIONISTA pueden cancelar citas
    @PatchMapping("/{id}/cancelar")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    public ResponseEntity<Cita> cancelarCita(@PathVariable String id) {
        try {
            Cita citaCancelada = citaService.cancelarCita(id);
            return ResponseEntity.ok(citaCancelada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}