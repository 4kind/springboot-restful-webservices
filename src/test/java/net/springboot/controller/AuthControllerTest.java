package net.springboot.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.springboot.payload.LoginDto;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;

@SpringBootTest
public class AuthControllerTest {

    @Autowired
    private AuthController controller;

    @Mock
    private LoginDto loginDto;

    @Test
    public void whenUsernameAndPasswordCorrect() {

        Mockito.when(loginDto.getPassword()).thenReturn("foobar");
        Mockito.when(loginDto.getUsernameOrEmail()).thenReturn("kind");

        assertThat(controller.authenticateUser(loginDto)).isInstanceOf(ResponseEntity.class);
    }

    @Test
    public void whenUsernameAndPasswordNotCorrect_ExceptionIsThrown() {
        BadCredentialsException thrown = assertThrows(
                BadCredentialsException.class,
                () -> controller.authenticateUser(loginDto),
                "Das Einloggen mit falschem Benutzernamen/Passwort schlägt fehl"
        );

        assertTrue(thrown.getMessage().contentEquals("Ungültige Anmeldedaten"));
    }
}