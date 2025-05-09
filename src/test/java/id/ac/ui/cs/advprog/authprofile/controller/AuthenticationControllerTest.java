package id.ac.ui.cs.advprog.authprofile.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.authprofile.config.SecurityConfig;
import id.ac.ui.cs.advprog.authprofile.dto.AuthRequest;
import id.ac.ui.cs.advprog.authprofile.dto.AuthResponse;
import id.ac.ui.cs.advprog.authprofile.dto.UserRegistrationDto;
import id.ac.ui.cs.advprog.authprofile.model.User;
import id.ac.ui.cs.advprog.authprofile.security.JwtAuthenticationFilter;
import id.ac.ui.cs.advprog.authprofile.service.AuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthenticationController.class)
@Import(SecurityConfig.class)
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws Exception {
        doAnswer(invocation -> {
            HttpServletRequest request = invocation.getArgument(0);
            HttpServletResponse response = invocation.getArgument(1);
            FilterChain chain = invocation.getArgument(2);
            chain.doFilter(request, response);
            return null;
        }).when(jwtAuthenticationFilter).doFilter(any(HttpServletRequest.class),
                any(HttpServletResponse.class),
                any(FilterChain.class));
    }

    @Test
    @WithAnonymousUser
    void testLoginSuccess() throws Exception {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setEmail("user@example.com");
        authRequest.setPassword("password");

        AuthResponse authResponse = new AuthResponse("dummy-token");
        Mockito.when(authenticationService.login(any(AuthRequest.class))).thenReturn(authResponse);

        mockMvc.perform(post("/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("dummy-token"));
    }

    @Test
    @WithAnonymousUser
    void testRegisterUser() throws Exception {
        UserRegistrationDto dto = new UserRegistrationDto();
        dto.setRole("Fundraiser");
        dto.setFullName("John Doe");
        dto.setEmail("john@example.com");
        dto.setPhoneNumber("+123456789");
        dto.setPassword("password");
        dto.setAddress("123 Main St");

        User user = new User(dto.getRole(), dto.getFullName(), dto.getEmail(), dto.getPhoneNumber(),
                dto.getPassword(), dto.getAddress());
        UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        user.setId(userId);

        Mockito.when(authenticationService.registerUser(any(UserRegistrationDto.class)))
                .thenReturn(user);

        mockMvc.perform(post("/auth/register/user")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.fullName").value("John Doe"));
    }

}