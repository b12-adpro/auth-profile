package id.ac.ui.cs.advprog.authprofile.service;

import id.ac.ui.cs.advprog.authprofile.model.User;
import id.ac.ui.cs.advprog.authprofile.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void testCreateUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setFullName("John Doe");

        when(userRepository.save(user)).thenReturn(user);

        User created = userService.createUser(user);

        assertThat(created.getEmail()).isEqualTo("test@example.com");
        assertThat(created.getFullName()).isEqualTo("John Doe");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testFindUserByEmail() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setFullName("Jane Doe");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        Optional<User> found = userService.findByEmail("test@example.com");

        assertThat(found).isPresent();
        assertThat(found.get().getFullName()).isEqualTo("Jane Doe");
    }

    @Test
    void testFindUserById() {
        UUID id = UUID.randomUUID();
        User user = new User();
        user.setUserId(id);
        user.setEmail("findme@example.com");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findById(id);

        assertThat(result).isPresent();
        assertThat(result.get().getEmail()).isEqualTo("findme@example.com");
    }
}
