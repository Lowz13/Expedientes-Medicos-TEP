package mx.unam.aragon.tsp.medisync;

import mx.unam.aragon.tsp.medisync.services.AuthService;
import mx.unam.aragon.tsp.medisync.dtos.AuthResponseDto;
import mx.unam.aragon.tsp.medisync.dtos.LoginDto;
import mx.unam.aragon.tsp.medisync.dtos.RegistroDto;
import mx.unam.aragon.tsp.medisync.models.Rol;
import mx.unam.aragon.tsp.medisync.models.Usuario;
import mx.unam.aragon.tsp.medisync.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Spy
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @InjectMocks
    private AuthService authService;

    private RegistroDto registroValido;

    @BeforeEach
    void setUp() {
        registroValido = new RegistroDto();
        registroValido.setNombre("Dr. Carlos Mendoza");
        registroValido.setUsername("Medico_01"); // Con mayúsculas para probar normalización
        registroValido.setCorreo("Carlos.Mendoza@Clinica.com");
        registroValido.setPassword("Password123!");
        registroValido.setRol(Rol.MEDICO);
    }

    @Test
    @DisplayName("Debe registrar un usuario normalizando username y correo a minúsculas")
    void registrarUsuarioExitoso() {
        when(usuarioRepository.existsByUsername("medico_01")).thenReturn(false);
        when(usuarioRepository.existsByCorreo("carlos.mendoza@clinica.com")).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(inv -> inv.getArgument(0));

        Usuario resultado = authService.registrar(registroValido);

        assertNotNull(resultado);
        assertEquals("medico_01", resultado.getUsername());
        assertEquals("carlos.mendoza@clinica.com", resultado.getCorreo());
        assertTrue(passwordEncoder.matches("Password123!", resultado.getPasswordHash()));
        assertTrue(resultado.isActivo());
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Debe rechazar registro si el username ya existe")
    void registrarUsernameDuplicado() {
        when(usuarioRepository.existsByUsername("medico_01")).thenReturn(true);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> authService.registrar(registroValido));

        assertTrue(ex.getMessage().contains("username"));
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe rechazar registro si el correo ya existe")
    void registrarCorreoDuplicado() {
        when(usuarioRepository.existsByUsername("medico_01")).thenReturn(false);
        when(usuarioRepository.existsByCorreo("carlos.mendoza@clinica.com")).thenReturn(true);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> authService.registrar(registroValido));

        assertTrue(ex.getMessage().contains("correo"));
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe permitir iniciar sesión usando username en mayúsculas/minúsculas")
    void loginConUsernameExitoso() {
        String hash = passwordEncoder.encode("Password123!");
        Usuario mockUsuario = new Usuario("Dr. Carlos Mendoza", "medico_01", "carlos@clinica.com", hash, Rol.MEDICO, true);

        when(usuarioRepository.findByUsernameOrCorreo("medico_01", "medico_01"))
                .thenReturn(Optional.of(mockUsuario));

        LoginDto loginDto = new LoginDto();
        loginDto.setIdentificador("MEDICO_01");
        loginDto.setPassword("Password123!");

        AuthResponseDto response = authService.login(loginDto);

        assertNotNull(response);
        assertEquals("medico_01", response.getUsername());
        assertEquals(Rol.MEDICO, response.getRol());
    }

    @Test
    @DisplayName("Debe permitir iniciar sesión usando correo electrónico")
    void loginConCorreoExitoso() {
        String hash = passwordEncoder.encode("Password123!");
        Usuario mockUsuario = new Usuario("Dr. Carlos Mendoza", "medico_01", "carlos@clinica.com", hash, Rol.MEDICO, true);

        when(usuarioRepository.findByUsernameOrCorreo("carlos@clinica.com", "carlos@clinica.com"))
                .thenReturn(Optional.of(mockUsuario));

        LoginDto loginDto = new LoginDto();
        loginDto.setIdentificador("Carlos@Clinica.com");
        loginDto.setPassword("Password123!");

        AuthResponseDto response = authService.login(loginDto);

        assertNotNull(response);
        assertEquals("carlos@clinica.com", response.getCorreo());
    }

    @Test
    @DisplayName("Debe rechazar login si la contraseña no coincide")
    void loginPasswordIncorrecta() {
        String hash = passwordEncoder.encode("Password123!");
        Usuario mockUsuario = new Usuario("Dr. Carlos Mendoza", "medico_01", "carlos@clinica.com", hash, Rol.MEDICO, true);

        when(usuarioRepository.findByUsernameOrCorreo("medico_01", "medico_01"))
                .thenReturn(Optional.of(mockUsuario));

        LoginDto loginDto = new LoginDto();
        loginDto.setIdentificador("medico_01");
        loginDto.setPassword("ClaveEquivocada");

        assertThrows(IllegalArgumentException.class, () -> authService.login(loginDto));
    }

    @Test
    @DisplayName("Debe rechazar login si la cuenta está inactiva")
    void loginCuentaInactiva() {
        String hash = passwordEncoder.encode("Password123!");
        Usuario mockUsuario = new Usuario("Dr. Carlos Mendoza", "medico_01", "carlos@clinica.com", hash, Rol.MEDICO, false);

        when(usuarioRepository.findByUsernameOrCorreo("medico_01", "medico_01"))
                .thenReturn(Optional.of(mockUsuario));

        LoginDto loginDto = new LoginDto();
        loginDto.setIdentificador("medico_01");
        loginDto.setPassword("Password123!");

        assertThrows(IllegalStateException.class, () -> authService.login(loginDto));
    }
}