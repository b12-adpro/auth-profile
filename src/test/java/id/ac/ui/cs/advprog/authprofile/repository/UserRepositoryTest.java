package id.ac.ui.cs.advprog.authprofile.repository;

import id.ac.ui.cs.advprog.authprofile.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Should save and retrieve a user by email")
    void testSaveAndFindByEmail() {
        // Arrange
        User user = new User();
        UUID userId = UUID.randomUUID();
        user.setUserId(userId);
        user.setEmail("johndoe@example.com");
        user.setFullName("John Doe");
        user.setPassword("secret");
        user.setPhoneNumber("08123456789");
        user.setDescription("A donor");
        user.setRole("DONOR");

        // Act
        userRepository.save(user);
        Optional<User> result = userRepository.findByEmail("johndoe@example.com");

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getFullName()).isEqualTo("John Doe");
    }
}
