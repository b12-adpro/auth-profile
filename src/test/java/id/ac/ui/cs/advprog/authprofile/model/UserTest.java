package id.ac.ui.cs.advprog.authprofile.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import id.ac.ui.cs.advprog.authprofile.model.enums.UserType;

public class UserTest {

    @Test
    void testUserModelGettersAndSetters() {
        // Arrange
        User user = new User();
        UUID userId = UUID.randomUUID();

        // Act
        user.setUserId(userId);
        user.setUsername("test@example.com");
        user.setPassword("password123");
        user.setRole(UserType.ADMIN);
        user.setDescription("Description test");
        user.setPhoneNumber("12341234");
        user.setFullName("John Doe");

        // Assert
        assertThat(user.getUserId()).isEqualTo(userId);
        assertThat(user.getUsername()).isEqualTo("test@example.com");
        assertThat(user.getPassword()).isEqualTo("password123");
        assertThat(user.getRole()).isEqualTo(UserType.ADMIN);
        assertThat(user.getDescription()).isEqualTo("Description test");
        assertThat(user.getPhoneNumber()).isEqualTo("12341234");
        assertThat(user.getFullName()).isEqualTo("John Doe");
    }
}
