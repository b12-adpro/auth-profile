package id.ac.ui.cs.advprog.authprofile.repository;

import id.ac.ui.cs.advprog.authprofile.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByEmailFundraiser() {
        User user = new User("Fundraiser", "User Name Fund", "userf@example.com", "1112223333", "hashedPassword", "Address");
        userRepository.save(user);

        Optional<User> found = userRepository.findByEmail("userf@example.com");
        assertTrue(found.isPresent());
        assertEquals("User Name Fund", found.get().getFullName());
    }

    @Test
    void testFindByEmailDonatur() {
        User user = new User("Donatur", "User Name Don", "userd@example.com", "1112223333", "hashedPassword", "Address");
        userRepository.save(user);

        Optional<User> found = userRepository.findByEmail("userd@example.com");
        assertTrue(found.isPresent());
        assertEquals("User Name Don", found.get().getFullName());
    }

}