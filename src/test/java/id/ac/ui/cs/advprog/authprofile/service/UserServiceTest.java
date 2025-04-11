package id.ac.ui.cs.advprog.authprofile.service;

import id.ac.ui.cs.advprog.authprofile.model.User;
import id.ac.ui.cs.advprog.authprofile.repository.UserRepository;
import id.ac.ui.cs.advprog.authprofile.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);

        sampleUser = new User();
        sampleUser.setUserId(UUID.randomUUID());
        sampleUser.setEmail("test@example.com");
        sampleUser.setPassword("secret");
        sampleUser.setFullName("Test User");
        sampleUser.setPhoneNumber("123456789");
        sampleUser.setBio("Sample bio");
    }

    @Test
    void testCreateUser() {
        when(userRepository.save(sampleUser)).thenReturn(sampleUser);

        User createdUser = userService.create(sampleUser);

        assertThat(createdUser).isEqualTo(sampleUser);
        verify(userRepository, times(1)).save(sampleUser);
    }

    @Test
    void testFindAllUsers() {
        List<User> users = List.of(sampleUser);
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.findAll();

        assertThat(result).hasSize(1).contains(sampleUser);
    }

    @Test
    void testFindByEmail() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(sampleUser));

        Optional<User> result = userService.findByEmail("test@example.com");

        assertThat(result).isPresent().contains(sampleUser);
    }

    @Test
    void testFindById() {
        UUID userId = sampleUser.getUserId();
        when(userRepository.findById(userId)).thenReturn(Optional.of(sampleUser));

        Optional<User> result = userService.findById(userId);

        assertThat(result).isPresent().contains(sampleUser);
    }

    @Test
    void testUpdateUser_Success() {
        UUID userId = sampleUser.getUserId();
        when(userRepository.existsById(userId)).thenReturn(true);
        when(userRepository.save(any(User.class))).thenReturn(sampleUser);

        boolean updated = userService.update(userId, sampleUser);

        assertThat(updated).isTrue();
    }

    @Test
    void testUpdateUser_Failure() {
        UUID userId = sampleUser.getUserId();
        when(userRepository.existsById(userId)).thenReturn(false);

        boolean updated = userService.update(userId, sampleUser);

        assertThat(updated).isFalse();
    }

    @Test
    void testDeleteUser() {
        UUID userId = sampleUser.getUserId();

        userService.deleteUserById(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }
}
