package mx.unam.aragon.tsp.medisync.controllers;

import mx.unam.aragon.tsp.medisync.models.Consulta;
import mx.unam.aragon.tsp.medisync.services.ConsultaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultas") // Ruta base para todas las peticiones de consultas
public class ConsultaController {

    private final ConsultaService consultaService;

    // Inyección de dependencias por constructor
    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    // POST: Registrar una nueva consulta clínica
    @PostMapping
    public ResponseEntity<Consulta> crearConsulta(@RequestBody Consulta consulta) {
        Consulta nuevaConsulta = consultaService.crearConsulta(consulta);
        return new ResponseEntity<>(nuevaConsulta, HttpStatus.CREATED);
    }

    // GET: Consultar el historial clínico de un paciente específico por su ID
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<Consulta>> obtenerHistorialPorPaciente(@PathVariable String pacienteId) {
        List<Consulta> historial = consultaService.obtenerHistorialPorPaciente(pacienteId);
        return ResponseEntity.ok(historial);
    }

    // GET: Consultar una consulta individual por su ID único
    @GetMapping("/{id}")
    public ResponseEntity<Consulta> obtenerConsultaPorId(@PathVariable String id) {
        return consultaService.obtenerConsultaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}