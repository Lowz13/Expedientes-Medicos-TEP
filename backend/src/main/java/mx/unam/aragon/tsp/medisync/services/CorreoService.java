package mx.unam.aragon.tsp.medisync.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class CorreoService {

    @Autowired
    private JavaMailSender mailSender;

    // Método auxiliar para enviar confirmaciones recibiendo el correo en texto (String)
    public void enviarCorreoConfirmacion(String correoDestino, String fechaCita) {
        String asunto = "Confirmación de Cita Médica - MediSync";
        String cuerpo = "Hola, tu cita médica ha sido registrada exitosamente.\n\n" +
                        "Fecha y hora programada: " + fechaCita + "\n\n" +
                        "¡Gracias por usar MediSync!";
        
        enviarCorreo(correoDestino, asunto, cuerpo);
    }

    // Método general para enviar cualquier correo electrónico
    public void enviarCorreo(String para, String asunto, String cuerpo) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(para);
            message.setSubject(asunto);
            message.setText(cuerpo);
            
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Error al enviar el correo electrónico: " + e.getMessage(), e);
        }
    }
}