package mx.unam.aragon.tsp.medisync.services;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ArchivoService {

    // Definimos la carpeta "uploads" en la raíz de tu proyecto o backend
    private final Path fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();

    public ArchivoService() {
        try {
            // Creamos la carpeta de almacenamiento si no existe
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("No se pudo crear el directorio donde se almacenarán los archivos.", ex);
        }
    }

    // Método para guardar la imagen o PDF
    public String guardarArchivo(MultipartFile file) {
        // Validar si el archivo está vacío
        if (file.isEmpty()) {
            throw new RuntimeException("No se puede almacenar un archivo vacío.");
        }

        try {
            // Limpiar el nombre original y añadir un UUID único para evitar duplicados
            String originalFileName = file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString() + "_" + originalFileName;

            // Copiar el archivo a la ruta de destino
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName; // Retornamos el nombre con el que se guardó
        } catch (IOException ex) {
            throw new RuntimeException("Fallo al almacenar el archivo. Intente de nuevo.", ex);
        }
    }

    // Método para cargar/descargar el archivo como recurso
    public Resource cargarArchivoComoRecursos(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("No se encontró el archivo: " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("No se encontró el archivo: " + fileName, ex);
        }
    }
}