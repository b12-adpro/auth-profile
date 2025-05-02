package id.ac.ui.cs.advprog.authprofile.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Test
    public void testRegisterUser_Failed() {
        UserRegistrationRequest request = new UserRegistrationRequest("user@email.com", "password");
        User result = authService.register(request);
        assertNull(result);  // RED
    }
}
