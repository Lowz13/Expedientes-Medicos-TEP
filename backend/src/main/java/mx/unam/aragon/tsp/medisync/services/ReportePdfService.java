package mx.unam.aragon.tsp.medisync.services;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import mx.unam.aragon.tsp.medisync.models.Consulta;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class ReportePdfService {

    public ByteArrayInputStream generarReportePdf(Consulta consulta) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Fuentes y estilos básicos
            Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

            // Contenido del PDF (Siguiendo estrictamente los datos de consulta)
            document.add(new Paragraph("MediSync - Reporte de Consulta Médica", tituloFont));
            document.add(new Paragraph("--------------------------------------------------------------------------------------------------"));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("ID de Consulta: " + consulta.getId(), bodyFont));
            document.add(new Paragraph("ID de Paciente: " + consulta.getPacienteId(), bodyFont));
            document.add(new Paragraph("ID de Médico: " + consulta.getMedicoId(), bodyFont));
            
            String fechaFormateada = consulta.getFechaConsulta() != null ? 
                consulta.getFechaConsulta().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : "No especificada";
            document.add(new Paragraph("Fecha y Hora: " + fechaFormateada, bodyFont));
            
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Diagnóstico:", headerFont));
            document.add(new Paragraph(consulta.getDiagnostico(), bodyFont));

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Tratamiento / Receta:", headerFont));
            document.add(new Paragraph(consulta.getTratamiento(), bodyFont));

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Notas Médicas:", headerFont));
            document.add(new Paragraph(consulta.getNotasMedicas(), bodyFont));

            document.close();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el reporte PDF: " + e.getMessage());
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}