package mx.unam.aragon.tsp.medisync.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import mx.unam.aragon.tsp.medisync.dtos.UsuarioRequestDto;
import mx.unam.aragon.tsp.medisync.dtos.UsuarioResponseDto;
import mx.unam.aragon.tsp.medisync.models.Rol;
import mx.unam.aragon.tsp.medisync.models.Usuario;
import mx.unam.aragon.tsp.medisync.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioResponseDto crearUsuario(UsuarioRequestDto dto) {
        if (dto.getRol() == Rol.MEDICO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Los médicos deben registrarse desde /api/medicos para vincular su perfil clínico");
        }

        String usernameLimpio = dto.getUsername().trim().toLowerCase(Locale.ROOT);
        String correoLimpio = dto.getCorreo().trim().toLowerCase(Locale.ROOT);

        if (usuarioRepository.existsByUsername(usernameLimpio)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El nombre de usuario ya está en uso");
        }
        if (usuarioRepository.existsByCorreo(correoLimpio)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo electrónico ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(usernameLimpio);
        usuario.setNombre(dto.getNombre().trim());
        usuario.setApellidoPaterno(dto.getApellidoPaterno().trim());
        usuario.setApellidoMaterno(dto.getApellidoMaterno() != null ? dto.getApellidoMaterno().trim() : null);
        usuario.setFechaNacimiento(dto.getFechaNacimiento());
        usuario.setCorreo(correoLimpio);
        usuario.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        usuario.setTelefono(dto.getTelefono().trim());
        usuario.setRol(dto.getRol());
        usuario.setActivo(true);
        usuario.setCreatedAt(LocalDateTime.now());

        return new UsuarioResponseDto(usuarioRepository.save(usuario));
    }

    public List<UsuarioResponseDto> listarUsuariosActivos() {
        return usuarioRepository.findByActivoTrue()
                .stream()
                .map(UsuarioResponseDto::new)
                .collect(Collectors.toList());
    }

    public void desactivarUsuario(String id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }
}