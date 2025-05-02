package id.ac.ui.cs.advprog.authprofile.service;

import id.ac.ui.cs.advprog.authprofile.dto.RegisterRequest;
import id.ac.ui.cs.advprog.authprofile.exception.EmailAlreadyExistsException;
import id.ac.ui.cs.advprog.authprofile.model.User;
import id.ac.ui.cs.advprog.authprofile.repository.UserRepository;
import id.ac.ui.cs.advprog.authprofile.security.JwtUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void testRegisterUser_success() {
        RegisterRequest request = new RegisterRequest("test@mail.com", "password");
        when(userRepository.existsByEmail("test@mail.com")).thenReturn(false);
        when(jwtUtils.generateToken(any(User.class))).thenReturn("dummy-jwt");

        String token = authService.registerUser(request);
        assertEquals("dummy-jwt", token);
    }

    @Test
    void testRegisterUser_emailExists() {
        RegisterRequest request = new RegisterRequest("test@mail.com", "password");
        when(userRepository.existsByEmail("test@mail.com")).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> {
            authService.registerUser(request);
        });
    }
}
