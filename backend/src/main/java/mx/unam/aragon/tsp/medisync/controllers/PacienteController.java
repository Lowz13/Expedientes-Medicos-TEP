package mx.unam.aragon.tsp.medisync.controllers;
import mx.unam.aragon.tsp.medisync.dtos.PacienteDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final List<PacienteDto> pacientes = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<PacienteDto>> obtenerTodos() {
        return ResponseEntity.ok(pacientes);
    }

    @PostMapping
    public ResponseEntity<PacienteDto> crear(@RequestBody PacienteDto dto) {
        dto.setId(String.valueOf(pacientes.size() + 1));
        pacientes.add(dto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}