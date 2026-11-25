package mx.unam.aragon.tsp.medisync.services;

import java.util.Locale;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import mx.unam.aragon.tsp.medisync.dtos.LoginRequestDTO;
import mx.unam.aragon.tsp.medisync.dtos.LoginResponseDto;
import mx.unam.aragon.tsp.medisync.models.Medico;
import mx.unam.aragon.tsp.medisync.models.Rol;
import mx.unam.aragon.tsp.medisync.models.Usuario;
import mx.unam.aragon.tsp.medisync.repositories.MedicoRepository;
import mx.unam.aragon.tsp.medisync.repositories.UsuarioRepository;
import mx.unam.aragon.tsp.medisync.security.JwtUtil;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final MedicoRepository medicoRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UsuarioRepository usuarioRepository,
                       MedicoRepository medicoRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.medicoRepository = medicoRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponseDto login(LoginRequestDTO dto) {
        String idLimpio = dto.getIdentificador().trim().toLowerCase(Locale.ROOT);

        Usuario usuario = usuarioRepository.findByUsernameOrCorreo(idLimpio, idLimpio)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas"));

        if (!usuario.isActivo()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "La cuenta de usuario se encuentra desactivada");
        }

        if (!passwordEncoder.matches(dto.getPassword(), usuario.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
        }

        String medicoId = null;
        if (usuario.getRol() == Rol.MEDICO) {
            medicoId = medicoRepository.findByUsuarioId(usuario.getId())
                    .map(Medico::getId)
                    .orElse(null);
        }

        String token = jwtUtil.generarToken(usuario);

        return new LoginResponseDto(
                token,
                usuario.getId(),
                medicoId,
                usuario.getNombre(),
                usuario.getUsername(),
                usuario.getCorreo(),
                usuario.getRol()
        );
    }
}