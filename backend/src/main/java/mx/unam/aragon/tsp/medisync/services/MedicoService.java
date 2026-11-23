package mx.unam.aragon.tsp.medisync.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import mx.unam.aragon.tsp.medisync.dtos.MedicoRequestDTO;
import mx.unam.aragon.tsp.medisync.dtos.MedicoResponseDTO;
import mx.unam.aragon.tsp.medisync.models.Medico;
import mx.unam.aragon.tsp.medisync.models.Rol;
import mx.unam.aragon.tsp.medisync.models.Usuario;
import mx.unam.aragon.tsp.medisync.repositories.MedicoRepository;
import mx.unam.aragon.tsp.medisync.repositories.UsuarioRepository;

@Service
public class MedicoService {

    private final MedicoRepository medicoRepository;
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public MedicoService(MedicoRepository medicoRepository, UsuarioRepository usuarioRepository) {
        this.medicoRepository = medicoRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public MedicoResponseDTO crearMedico(MedicoRequestDTO dto) {
        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La contraseña inicial es obligatoria al registrar un médico");
        }

        String usernameLimpio = dto.getUsername().trim().toLowerCase(Locale.ROOT);
        String correoLimpio = dto.getCorreo().trim().toLowerCase(Locale.ROOT);
        String cedulaLimpia = dto.getCedulaProfesional().trim();

        if (usuarioRepository.existsByUsername(usernameLimpio)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El nombre de usuario ya está en uso");
        }

        if (usuarioRepository.existsByCorreo(correoLimpio)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un usuario registrado con ese correo electrónico");
        }

        if (medicoRepository.existsByCedulaProfesional(cedulaLimpia)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un médico registrado con esa cédula profesional");
        }

        String apellidoMat = dto.getApellidoMaterno() != null ? dto.getApellidoMaterno().trim() : "";
        String nombreCompleto = (dto.getNombre().trim() + " " + dto.getApellidoPaterno().trim() + " " + apellidoMat).trim();

        Usuario usuario = new Usuario(
                nombreCompleto,
                usernameLimpio,
                correoLimpio,
                passwordEncoder.encode(dto.getPassword()),
                Rol.MEDICO,
                true
        );
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        Medico medico = new Medico();
        medico.setUsuarioId(usuarioGuardado.getId());
        medico.setNombre(dto.getNombre().trim());
        medico.setApellidoPaterno(dto.getApellidoPaterno().trim());
        medico.setApellidoMaterno(apellidoMat.isEmpty() ? null : apellidoMat);
        medico.setTelefono(dto.getTelefono().trim());
        medico.setEspecialidad(dto.getEspecialidad().trim());
        medico.setCedulaProfesional(cedulaLimpia);
        medico.setHorarios(dto.getHorarios() != null ? dto.getHorarios() : new ArrayList<>());
        medico.setActivo(true);

        Medico medicoGuardado = medicoRepository.save(medico);

        return new MedicoResponseDTO(medicoGuardado, usuarioGuardado.getUsername(), usuarioGuardado.getCorreo());
    }

    public List<MedicoResponseDTO> listarMedicos(String especialidad) {
        List<Medico> medicos = (especialidad != null && !especialidad.isBlank())
                ? medicoRepository.findByActivoTrueAndEspecialidadIgnoreCase(especialidad.trim())
                : medicoRepository.findByActivoTrue();

        return medicos.stream()
                .map(medico -> {
                    Usuario usuario = usuarioRepository.findById(medico.getUsuarioId()).orElse(null);
                    String username = usuario != null ? usuario.getUsername() : null;
                    String correo = usuario != null ? usuario.getCorreo() : null;
                    return new MedicoResponseDTO(medico, username, correo);
                })
                .collect(Collectors.toList());
    }

    public MedicoResponseDTO buscarMedicoPorId(String id) {
        Medico medico = medicoRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico no encontrado"));

        Usuario usuario = usuarioRepository.findById(medico.getUsuarioId()).orElse(null);
        String username = usuario != null ? usuario.getUsername() : null;
        String correo = usuario != null ? usuario.getCorreo() : null;

        return new MedicoResponseDTO(medico, username, correo);
    }

    public MedicoResponseDTO actualizarMedico(String id, MedicoRequestDTO dto) {
        Medico medico = medicoRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico no encontrado"));

        Usuario usuario = usuarioRepository.findById(medico.getUsuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta de usuario asociada no encontrada"));

        String usernameLimpio = dto.getUsername().trim().toLowerCase(Locale.ROOT);
        String correoLimpio = dto.getCorreo().trim().toLowerCase(Locale.ROOT);
        String cedulaLimpia = dto.getCedulaProfesional().trim();

        usuarioRepository.findByUsername(usernameLimpio).ifPresent(existente -> {
            if (!existente.getId().equals(usuario.getId())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "El nombre de usuario ya está en uso por otra cuenta");
            }
        });

        usuarioRepository.findByCorreo(correoLimpio).ifPresent(existente -> {
            if (!existente.getId().equals(usuario.getId())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo electrónico ya está en uso por otro usuario");
            }
        });

        medicoRepository.findByCedulaProfesional(cedulaLimpia).ifPresent(existente -> {
            if (!existente.getId().equals(medico.getId())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "La cédula profesional ya está registrada con otro médico");
            }
        });

        String apellidoMat = dto.getApellidoMaterno() != null ? dto.getApellidoMaterno().trim() : "";
        String nombreCompleto = (dto.getNombre().trim() + " " + dto.getApellidoPaterno().trim() + " " + apellidoMat).trim();

        usuario.setNombre(nombreCompleto);
        usuario.setUsername(usernameLimpio);
        usuario.setCorreo(correoLimpio);
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            usuario.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        }
        usuarioRepository.save(usuario);

        medico.setNombre(dto.getNombre().trim());
        medico.setApellidoPaterno(dto.getApellidoPaterno().trim());
        medico.setApellidoMaterno(apellidoMat.isEmpty() ? null : apellidoMat);
        medico.setTelefono(dto.getTelefono().trim());
        medico.setEspecialidad(dto.getEspecialidad().trim());
        medico.setCedulaProfesional(cedulaLimpia);
        if (dto.getHorarios() != null) {
            medico.setHorarios(dto.getHorarios());
        }
        Medico actualizado = medicoRepository.save(medico);

        return new MedicoResponseDTO(actualizado, usuario.getUsername(), usuario.getCorreo());
    }

    public void eliminarMedico(String id) {
        Medico medico = medicoRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico no encontrado"));

        medico.setActivo(false);
        medicoRepository.save(medico);

        usuarioRepository.findById(medico.getUsuarioId()).ifPresent(usuario -> {
            usuario.setActivo(false);
            usuarioRepository.save(usuario);
        });
    }
}