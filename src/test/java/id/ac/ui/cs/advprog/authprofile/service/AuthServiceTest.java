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
