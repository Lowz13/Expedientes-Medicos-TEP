package mx.unam.aragon.tsp.medisync.services;

import lombok.RequiredArgsConstructor;
import mx.unam.aragon.tsp.medisync.dtos.AuthResponseDto;
import mx.unam.aragon.tsp.medisync.dtos.LoginDto;
import mx.unam.aragon.tsp.medisync.dtos.RegistroDto;
import mx.unam.aragon.tsp.medisync.models.Usuario;
import mx.unam.aragon.tsp.medisync.repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario registrar(RegistroDto dto) {
        String usernameNorm = dto.getUsername().trim().toLowerCase();
        String correoNorm = dto.getCorreo().trim().toLowerCase();

        if (usuarioRepository.existsByUsername(usernameNorm)) {
            throw new IllegalArgumentException("El username '" + usernameNorm + "' ya está registrado.");
        }
        if (usuarioRepository.existsByCorreo(correoNorm)) {
            throw new IllegalArgumentException("El correo '" + correoNorm + "' ya está registrado.");
        }

        String passwordHash = passwordEncoder.encode(dto.getPassword());

        Usuario nuevoUsuario = new Usuario(
                dto.getNombre().trim(),
                usernameNorm,
                correoNorm,
                passwordHash,
                dto.getRol(),
                true
        );

        return usuarioRepository.save(nuevoUsuario);
    }

    public AuthResponseDto login(LoginDto dto) {
        String idNorm = dto.getIdentificador().trim().toLowerCase();

        Usuario usuario = usuarioRepository.findByUsernameOrCorreo(idNorm, idNorm)
                .orElseThrow(() -> new IllegalArgumentException("Credenciales inválidas"));

        if (!passwordEncoder.matches(dto.getPassword(), usuario.getPasswordHash())) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }

        if (!usuario.isActivo()) {
            throw new IllegalStateException("La cuenta está desactivada.");
        }

        // Token temporal hasta conectar el módulo de security
        String tokenTemporal = "TOKEN_PENDIENTE_" + usuario.getId();

        return new AuthResponseDto(
                tokenTemporal,
                "Bearer",
                usuario.getId(),
                usuario.getNombre(),
                usuario.getUsername(),
                usuario.getCorreo(),
                usuario.getRol()
        );
    }
}