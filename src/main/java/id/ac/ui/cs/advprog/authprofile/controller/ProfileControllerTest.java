package id.ac.ui.cs.advprog.authprofile.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.authprofile.dto.ProfileUpdateDto;
import id.ac.ui.cs.advprog.authprofile.security.JwtTokenProvider;
import id.ac.ui.cs.advprog.authprofile.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @Test
    @WithMockUser(username = "user@example.com", roles = {"USER"})
    void testUpdateProfile_UserSuccess() throws Exception {
        ProfileUpdateDto dto = new ProfileUpdateDto();
        dto.setFullName("Updated User");
        dto.setPhoneNumber("+1112223333");
        dto.setAddress("Updated Address");
        dto.setProfilePhoto("updated-user.png");
        dto.setPassword("newPassword");

        id.ac.ui.cs.advprog.authprofile.model.User updatedUser =
                new id.ac.ui.cs.advprog.authprofile.model.User("Updated User", "user@example.com", "+1112223333", "hashedNewPassword", "Updated Address");
        UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000003");
        updatedUser.setId(userId);
        updatedUser.setProfilePhoto("updated-user.png");

        Mockito.when(profileService.updateProfile(any(ProfileUpdateDto.class), eq("user@example.com"), eq("USER")))
                .thenReturn(updatedUser);

        mockMvc.perform(put("/profile")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.fullName").value("Updated User"))
                .andExpect(jsonPath("$.profilePhoto").value("updated-user.png"));
    }

   
}