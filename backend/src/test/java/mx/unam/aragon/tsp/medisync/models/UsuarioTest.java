package mx.unam.aragon.tsp.medisync.models;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    static void configurar() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    static void cerrar() {
        factory.close();
    }

    private Usuario crearUsuario(String username) {
        Usuario usuario = new Usuario();
        usuario.setNombre("Usuario de prueba");
        usuario.setUsername(username);
        usuario.setCorreo("prueba@example.com");
        usuario.setPasswordHash("hash_prueba");
        usuario.setRol(Rol.RECEPCIONISTA);
        usuario.setActivo(true);
        return usuario;
    }

    @Test
    void debeAceptarUsernameValido() {
        Usuario usuario = crearUsuario("medico_01");

        assertTrue(validator.validate(usuario).isEmpty());
    }

    @Test
    void debeNormalizarMayusculas() {
        Usuario usuario = crearUsuario("Ana.Lopez");

        assertEquals("ana.lopez", usuario.getUsername());
        assertTrue(validator.validate(usuario).isEmpty());
    }

    @Test
    void debeRechazarEspacios() {
        Usuario usuario = crearUsuario("ana lopez");

        assertFalse(validator.validate(usuario).isEmpty());
    }

    @Test
    void debeRechazarCorreoComoUsername() {
        Usuario usuario = crearUsuario("ana@gmail.com");

        assertFalse(validator.validate(usuario).isEmpty());
    }

    @Test
    void debeRechazarUsernameCorto() {
        Usuario usuario = crearUsuario("abc");

        assertFalse(validator.validate(usuario).isEmpty());
    }

    @Test
    void debeRechazarUsernameLargo() {
        Usuario usuario = crearUsuario("a".repeat(31));

        assertFalse(validator.validate(usuario).isEmpty());
    }

    @Test
    void debeRechazarUsernameVacio() {
        Usuario usuario = crearUsuario("");

        assertFalse(validator.validate(usuario).isEmpty());
    }

    @Test
    void debeRechazarUsernameNulo() {
        Usuario usuario = crearUsuario(null);

        assertFalse(validator.validate(usuario).isEmpty());
    }
}
