package mx.unam.aragon.tsp.medisync.controllers;

import mx.unam.aragon.tsp.medisync.services.ArchivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletRequest; // <-- AQUÍ SE AGREGÓ EL "IMPORT"

@RestController
@RequestMapping("/api/archivos")
public class ArchivoController {

    @Autowired
    private ArchivoService archivoService;

    // Endpoint POST para subir una imagen o PDF
    @PostMapping("/subir")
    public ResponseEntity<String> subirArchivo(@RequestParam("file") MultipartFile file) {
        String fileName = archivoService.guardarArchivo(file);
        return ResponseEntity.ok("Archivo subido exitosamente con el nombre: " + fileName);
    }

    // Endpoint GET para descargar o visualizar el archivo
    @GetMapping("/descargar/{fileName:.+}")
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