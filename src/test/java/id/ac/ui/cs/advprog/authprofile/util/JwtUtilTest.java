package id.ac.ui.cs.advprog.authprofile.util;

import id.ac.ui.cs.advprog.authprofile.model.User;
import id.ac.ui.cs.advprog.authprofile.model.enums.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

public class JwtUtilTest {

    private JwtUtil jwtUtil;
    private User user;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil("secretkeytest123", 3600000); // secret & expiration 1 hour

        user = new User();
        user.setUserId(UUID.randomUUID());
        user.setUsername("john@example.com");
        user.setFullName("John Doe");
        user.setPhoneNumber("081234567890");
        user.setRole(UserType.FUNDRAISER);
    }

    @Test
    void testGenerateTokenFromUser() {
        String token = jwtUtil.generateToken(user);
        assertThat(token).isNotBlank();
    }

    @Test
    void testValidateTokenSuccess() {
        String token = jwtUtil.generateToken(user);
        boolean isValid = jwtUtil.validateToken(token, user);
        assertThat(isValid).isTrue();
    }

    @Test
    void testExtractUsernameFromToken() {
        String token = jwtUtil.generateToken(user);
        String username = jwtUtil.extractUsername(token);
        assertThat(username).isEqualTo(user.getUsername());
    }
}
