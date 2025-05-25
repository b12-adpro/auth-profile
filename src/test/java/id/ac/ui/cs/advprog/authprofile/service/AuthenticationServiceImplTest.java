package id.ac.ui.cs.advprog.authprofile.service;

import id.ac.ui.cs.advprog.authprofile.dto.AuthRequest;
import id.ac.ui.cs.advprog.authprofile.dto.AuthResponse;
import id.ac.ui.cs.advprog.authprofile.dto.UserRegistrationDto;
import id.ac.ui.cs.advprog.authprofile.model.Admin;
import id.ac.ui.cs.advprog.authprofile.model.User;
import id.ac.ui.cs.advprog.authprofile.repository.AdminRepository;
import id.ac.ui.cs.advprog.authprofile.repository.UserRepository;
import id.ac.ui.cs.advprog.authprofile.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthenticationServiceImplTest {

    private AdminRepository adminRepository;
    private UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;
    private AuthenticationServiceImpl authService;

    @BeforeEach
    void setUp() {
        adminRepository = mock(AdminRepository.class);
        userRepository = mock(UserRepository.class);
        jwtTokenProvider = mock(JwtTokenProvider.class);
        authService = new AuthenticationServiceImpl(adminRepository, userRepository, jwtTokenProvider);
    }

    @Test
    void testLogin_AdminSuccess() throws Exception {
        String email = "admin@example.com";
        String rawPassword = "admin123";
        String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
        Admin admin = new Admin("Default Admin", email, "1234567890", hashedPassword);
        UUID adminId = UUID.randomUUID();
        admin.setId(adminId);

        when(adminRepository.findByEmail(email)).thenReturn(Optional.of(admin));
        when(jwtTokenProvider.generateToken(adminId.toString(), "ADMIN")).thenReturn("dummy-admin-token");

        AuthRequest request = new AuthRequest();
        request.setEmail(email);
        request.setPassword(rawPassword);

        AuthResponse response = authService.login(request);
        assertNotNull(response);
        assertEquals("dummy-admin-token", response.getToken());
    }

    @Test
    void testLogin_UserSuccess() throws Exception {
        String email = "user@example.com";
        String rawPassword = "userpass";
        String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
        User user = new User("Fundraiser", "User Name", email, "1112223333", hashedPassword, "User Address");
        UUID userId = UUID.randomUUID();
        user.setId(userId);

        when(adminRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(jwtTokenProvider.generateToken(userId.toString(), "USER")).thenReturn("dummy-user-token");

        AuthRequest request = new AuthRequest();
        request.setEmail(email);
        request.setPassword(rawPassword);

        AuthResponse response = authService.login(request);
        assertNotNull(response);
        assertEquals("dummy-user-token", response.getToken());
    }

    @Test
    void testLogin_InvalidPassword() {
        String email = "admin@example.com";
        String rawPassword = "admin123";
        String hashedPassword = BCrypt.hashpw("differentPassword", BCrypt.gensalt());
        Admin admin = new Admin("Default Admin", email, "1234567890", hashedPassword);
        admin.setId(UUID.randomUUID());

        when(adminRepository.findByEmail(email)).thenReturn(Optional.of(admin));

        AuthRequest request = new AuthRequest();
        request.setEmail(email);
        request.setPassword(rawPassword);

        Exception exception = assertThrows(Exception.class, () -> authService.login(request));
        assertTrue(exception.getMessage().contains("Invalid password"));
    }

    @Test
    void testLogin_NoAccountFound() {
        String email = "nonexistent@example.com";
        when(adminRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        AuthRequest request = new AuthRequest();
        request.setEmail(email);
        request.setPassword("anyPassword");

        Exception exception = assertThrows(Exception.class, () -> authService.login(request));
        assertTrue(exception.getMessage().contains("No account found"));
    }

    @Test
    void testRegisterUser() {
        UserRegistrationDto dto = new UserRegistrationDto();
        dto.setRole("Fundraiser");
        dto.setFullName("John Doe");
        dto.setEmail("john@example.com");
        dto.setPhoneNumber("+123456789");
        dto.setPassword("password");
        dto.setAddress("123 Main St");

        User savedUser = new User(dto.getRole(), dto.getFullName(), dto.getEmail(), dto.getPhoneNumber(),
                BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt()), dto.getAddress());
        savedUser.setId(UUID.randomUUID());

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = authService.registerUser(dto);
        assertNotNull(result);
        assertEquals("John Doe", result.getFullName());
    }

}