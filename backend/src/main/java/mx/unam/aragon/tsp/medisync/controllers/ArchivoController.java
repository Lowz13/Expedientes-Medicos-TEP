package mx.unam.aragon.tsp.medisync.controllers;

import mx.unam.aragon.tsp.medisync.services.ArchivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/archivos")
public class ArchivoController {

    @Autowired
    private ArchivoService archivoService;

    // POST: Subir archivos clínicos
    // Permitido para ADMIN, MEDICO y RECEPCIONISTA
    @PostMapping("/subir")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO', 'RECEPCIONISTA')")
    public ResponseEntity<String> subirArchivo(@RequestParam("file") MultipartFile file) {
        String fileName = archivoService.guardarArchivo(file);
        return ResponseEntity.ok("Archivo subido exitosamente con el nombre: " + fileName);
    }

    // GET: Descargar o visualizar recursos clínicos sensibles
    // Permitido únicamente para ADMIN y MEDICO
    @GetMapping("/descargar/{fileName:.+}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO')")
    public ResponseEntity<Resource> descargarArchivo(@PathVariable String fileName, HttpServletRequest request) {
        // Cargar el archivo como recurso
        Resource resource = archivoService.cargarArchivoComoRecursos(fileName);

        // Intentar determinar el tipo de contenido (ContentType) del archivo
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (Exception ex) {
            // Tipo por defecto si no se reconoce
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}