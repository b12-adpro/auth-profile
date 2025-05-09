package id.ac.ui.cs.advprog.authprofile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import id.ac.ui.cs.advprog.authprofile.security.JwtTokenProvider;
import id.ac.ui.cs.advprog.authprofile.service.ProfileService;

@WebMvcTest(ProfileController.class)
@Import(id.ac.ui.cs.advprog.authprofile.config.SecurityConfig.class)
class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfileService profileService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ObjectMapper objectMapper;

    void testUpdateProfile_UserSuccess() throws Exception {
    }


}