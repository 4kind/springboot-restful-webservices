package net.springboot.service;

import net.springboot.entity.User;
import net.springboot.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CustomUserDetailsServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailsService service;

    @Test
    public void whenUserNameWasFoundReturnIt() {
        String mailExists = "exists@mail.com";
        String password = "12345";

        User user = Mockito.mock(User.class);

        Mockito.when(userRepository.findByUsernameOrEmail(mailExists, mailExists)).thenReturn(Optional.of(user));
        Mockito.when(user.getEmail()).thenReturn(mailExists);
        Mockito.when(user.getPassword()).thenReturn(password);

        service.loadUserByUsername(mailExists);
    }

    @Test
    public void whenUserNameWasNotFoundExceptionIsThrown() {
        Exception exception = Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            service.loadUserByUsername("mail_does@not.exist");
        });

        assertThat(exception.getMessage()).isEqualTo(
                String.format("User not found with username or email: %s", "mail_does@not.exist")
        );
    }
}
