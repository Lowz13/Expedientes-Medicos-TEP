package mx.unam.aragon.tsp.medisync.controllers;

import mx.unam.aragon.tsp.medisync.models.Consulta;
import mx.unam.aragon.tsp.medisync.services.ConsultaService;
import mx.unam.aragon.tsp.medisync.services.ReportePdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/api/consultas") // Ruta base para todas las peticiones de consultas
public class ConsultaController {

    private final ConsultaService consultaService;
    private final ReportePdfService reportePdfService;

    // Inyección de dependencias por constructor
    public ConsultaController(ConsultaService consultaService, ReportePdfService reportePdfService) {
        this.consultaService = consultaService;
        this.reportePdfService = reportePdfService;
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

    // GET: Descargar el reporte PDF de una consulta específica
    @GetMapping("/{id}/pdf")
    public ResponseEntity<org.springframework.core.io.InputStreamResource> descargarReportePdf(@PathVariable String id) {
        // Se busca la consulta; si no existe, se lanza una excepción
        Consulta consulta = consultaService.obtenerConsultaPorId(id)
                .orElseThrow(() -> new RuntimeException("Consulta no encontrada con ID: " + id));

        // Se genera el PDF en memoria (ByteArrayInputStream) usando el servicio
        ByteArrayInputStream bis = reportePdfService.generarReportePdf(consulta);

        // Cabeceras HTTP: se indica que es un PDF y cómo debe mostrarse/descargarse
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=reporte-consulta-" + id + ".pdf");

        // Se devuelve el PDF con el tipo de contenido correcto
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new org.springframework.core.io.InputStreamResource(bis));
    }
}