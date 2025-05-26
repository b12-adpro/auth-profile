package id.ac.ui.cs.advprog.authprofile.controller;

import id.ac.ui.cs.advprog.authprofile.model.User;
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

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    @WithMockUser(username = "00000000-0000-0000-0000-000000000003", roles = {"USER"})
    void testUpdateProfile_UserSuccess() throws Exception {
        ProfileUpdateDto dto = new ProfileUpdateDto();
        dto.setFullName("Updated User");
        dto.setPhoneNumber("+1112223333");
        dto.setAddress("Updated Address");
        dto.setPassword("newPassword");

        id.ac.ui.cs.advprog.authprofile.model.User updatedUser =
                new id.ac.ui.cs.advprog.authprofile.model.User("Updated User", "user@example.com", "+1112223333", "hashedNewPassword", "Updated Address");
        UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000003");
        updatedUser.setId(userId);

        Mockito.when(profileService.updateProfile(any(ProfileUpdateDto.class), eq("00000000-0000-0000-0000-000000000003"), eq("USER")))
                .thenReturn(updatedUser);

        mockMvc.perform(put("/profile")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.fullName").value("Updated User"));
    }

    @Test
    @WithMockUser(username = "adminId123", roles = {"ADMIN"})
    void testGetAllProfiles_AdminAccessSuccess() throws Exception {
        User user1 = new User("User One", "user1@example.com", "111", "pass1", "Address 1");
        user1.setId(UUID.randomUUID());
        User user2 = new User("User Two", "user2@example.com", "222", "pass2", "Address 2");
        user2.setId(UUID.randomUUID());
        List<User> mockUsers = Arrays.asList(user1, user2);

        Mockito.when(profileService.getAllUsers()).thenReturn(mockUsers);

        mockMvc.perform(get("/profile/all")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].fullName").value("User One"))
                .andExpect(jsonPath("$[1].email").value("user2@example.com"));

        Mockito.verify(profileService, Mockito.times(1)).getAllUsers();
    }

    @Test
    @WithMockUser(username = "userId123", roles = {"USER"})
    void testGetAllProfiles_UserAccessDenied() throws Exception {
        mockMvc.perform(get("/profile/all")
                        .with(csrf()))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Access denied"));

        Mockito.verify(profileService, Mockito.never()).getAllUsers();
    }

    @Test
    @WithMockUser(username = "adminId123", roles = {"ADMIN"})
    void testGetAllProfiles_ServiceThrowsException() throws Exception {
        Mockito.when(profileService.getAllUsers()).thenThrow(new RuntimeException("Database error"));

        mockMvc.perform(get("/profile/all")
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Database error")); // Check if the error message is returned

        Mockito.verify(profileService, Mockito.times(1)).getAllUsers();
    }
}