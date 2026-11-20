package mx.unam.aragon.tsp.medisync;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import mx.unam.aragon.tsp.medisync.models.Rol;
import mx.unam.aragon.tsp.medisync.dtos.RegistroDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RegistroDtoValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private RegistroDto crearDtoBase(String username) {
        RegistroDto dto = new RegistroDto();
        dto.setNombre("Ana López");
        dto.setUsername(username);
        dto.setCorreo("ana@clinica.com");
        dto.setPassword("Secreta123");
        dto.setRol(Rol.MEDICO);
        return dto;
    }

    @Test
    void usernameValidoNoGeneraErrores() {
        Set<ConstraintViolation<RegistroDto>> errores = validator.validate(crearDtoBase("dra.ana_01"));
        assertTrue(errores.isEmpty());
    }

    @Test
    void usernameMuyCortoFalla() {
        Set<ConstraintViolation<RegistroDto>> errores = validator.validate(crearDtoBase("abc"));
        assertFalse(errores.isEmpty());
    }

    @Test
    void usernameConArrobaOEspaciosFalla() {
        assertFalse(validator.validate(crearDtoBase("ana@lopez")).isEmpty());
        assertFalse(validator.validate(crearDtoBase("ana lopez")).isEmpty());
    }
}