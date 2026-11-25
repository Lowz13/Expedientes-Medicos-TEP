package mx.unam.aragon.tsp.medisync;

import java.util.Optional;

import mx.unam.aragon.tsp.medisync.dtos.LoginRequestDTO;
import mx.unam.aragon.tsp.medisync.dtos.LoginResponseDto;
import mx.unam.aragon.tsp.medisync.models.Medico;
import mx.unam.aragon.tsp.medisync.models.Rol;
import mx.unam.aragon.tsp.medisync.models.Usuario;
import mx.unam.aragon.tsp.medisync.repositories.MedicoRepository;
import mx.unam.aragon.tsp.medisync.repositories.UsuarioRepository;
import mx.unam.aragon.tsp.medisync.security.JwtUtil;
import mx.unam.aragon.tsp.medisync.services.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private MedicoRepository medicoRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Spy
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @InjectMocks
    private AuthService authService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario(
                "Dr. Carlos Mendoza",
                "medico_01",
                "carlos.mendoza@clinica.com",
                passwordEncoder.encode("Password123!"),
                Rol.MEDICO,
                true
        );
        usuario.setId("usuario-123");
    }

    @Test
    @DisplayName("Debe iniciar sesión normalizando un username con mayúsculas y espacios")
    void loginConUsernameExitoso() {
        when(usuarioRepository.findByUsernameOrCorreo("medico_01", "medico_01"))
                .thenReturn(Optional.of(usuario));
        when(medicoRepository.findByUsuarioId("usuario-123"))
                .thenReturn(Optional.empty());
        when(jwtUtil.generarToken(usuario)).thenReturn("jwt-de-prueba");

        LoginResponseDto response = authService.login(login(" MEDICO_01 ", "Password123!"));

        assertEquals("jwt-de-prueba", response.getToken());
        assertEquals("Bearer", response.getTipo());
        assertEquals("usuario-123", response.getUsuarioId());
        assertEquals("Dr. Carlos Mendoza", response.getNombre());
        assertEquals("medico_01", response.getUsername());
        assertEquals("carlos.mendoza@clinica.com", response.getCorreo());
        assertEquals(Rol.MEDICO, response.getRol());
        assertNull(response.getMedicoId());
    }

    @Test
    @DisplayName("Debe permitir iniciar sesión usando correo electrónico")
    void loginConCorreoExitoso() {
        when(usuarioRepository.findByUsernameOrCorreo(
                "carlos.mendoza@clinica.com",
                "carlos.mendoza@clinica.com"))
                .thenReturn(Optional.of(usuario));
        when(medicoRepository.findByUsuarioId("usuario-123"))
                .thenReturn(Optional.empty());
        when(jwtUtil.generarToken(usuario)).thenReturn("jwt-de-prueba");

        LoginResponseDto response = authService.login(
                login("Carlos.Mendoza@Clinica.com", "Password123!"));

        assertEquals("carlos.mendoza@clinica.com", response.getCorreo());
    }

    @Test
    @DisplayName("Debe incluir el identificador del médico en la respuesta")
    void loginIncluyeMedicoId() {
        Medico medico = new Medico();
        medico.setId("medico-456");
        when(usuarioRepository.findByUsernameOrCorreo("medico_01", "medico_01"))
                .thenReturn(Optional.of(usuario));
        when(medicoRepository.findByUsuarioId("usuario-123"))
                .thenReturn(Optional.of(medico));
        when(jwtUtil.generarToken(usuario)).thenReturn("jwt-de-prueba");

        LoginResponseDto response = authService.login(login("medico_01", "Password123!"));

        assertEquals("medico-456", response.getMedicoId());
    }

    @Test
    @DisplayName("Debe rechazar el login si el usuario no existe")
    void loginUsuarioInexistente() {
        when(usuarioRepository.findByUsernameOrCorreo("desconocido", "desconocido"))
                .thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> authService.login(login("desconocido", "Password123!"))
        );

        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
        verify(jwtUtil, never()).generarToken(usuario);
    }

    @Test
    @DisplayName("Debe rechazar el login si la contraseña no coincide")
    void loginPasswordIncorrecta() {
        when(usuarioRepository.findByUsernameOrCorreo("medico_01", "medico_01"))
                .thenReturn(Optional.of(usuario));

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> authService.login(login("medico_01", "ClaveEquivocada"))
        );

        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
        verify(jwtUtil, never()).generarToken(usuario);
    }

    @Test
    @DisplayName("Debe rechazar el login si la cuenta está inactiva")
    void loginCuentaInactiva() {
        usuario.setActivo(false);
        when(usuarioRepository.findByUsernameOrCorreo("medico_01", "medico_01"))
                .thenReturn(Optional.of(usuario));

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> authService.login(login("medico_01", "Password123!"))
        );

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusCode());
        verify(medicoRepository, never()).findByUsuarioId("usuario-123");
        verify(jwtUtil, never()).generarToken(usuario);
    }

    private LoginRequestDTO login(String identificador, String password) {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setIdentificador(identificador);
        request.setPassword(password);
        return request;
    }
}
