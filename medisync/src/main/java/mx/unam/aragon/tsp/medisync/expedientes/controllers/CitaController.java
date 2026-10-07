package mx.unam.aragon.tsp.medisync.expedientes.controllers;
import mx.unam.aragon.tsp.medisync.expedientes.dtos.CitaDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    private final List<CitaDto> citas = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<CitaDto>> obtenerTodas() {
        return ResponseEntity.ok(citas);
    }

    @PostMapping
    public ResponseEntity<CitaDto> agendar(@RequestBody CitaDto dto) {
        dto.setId(String.valueOf(citas.size() + 1));
        citas.add(dto);
        // Simulación: Confirmación por correo
        System.out.println("Enviando correo de confirmación a: " + dto.getCorreoPaciente());
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}