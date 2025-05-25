package id.ac.ui.cs.advprog.authprofile.service;

import id.ac.ui.cs.advprog.authprofile.dto.ProfileUpdateDto;
import id.ac.ui.cs.advprog.authprofile.model.User;
import id.ac.ui.cs.advprog.authprofile.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

class ProfileServiceImplTest {

    private UserRepository userRepository;
    private ProfileServiceImpl profileService;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        profileService = new ProfileServiceImpl(userRepository);
    }

    @Test
    void testUpdateUserProfileSuccess() throws Exception {
        User user = new User("John Doe", "john@example.com", "+123456789", "oldPassword", "Old Address");
        UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        user.setId(userId);
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        ProfileUpdateDto dto = new ProfileUpdateDto();
        dto.setFullName("John Smith");
        dto.setPhoneNumber("+987654321");
        dto.setAddress("New Address");
        dto.setPassword("newPassword");
        // Stub save method to return the updated user.
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        User updatedUser = (User) profileService.updateProfile(dto, userId.toString(), "USER");
        // Verify that fields are updated.
        assertEquals("John Smith", updatedUser.getFullName());
        assertEquals("+987654321", updatedUser.getPhoneNumber());
        assertEquals("New Address", updatedUser.getAddress());
        // Verify that the password is hashed (i.e. not equal to plain "newPassword").
        assertNotEquals("newPassword", updatedUser.getPassword());
    }

    @Test
    void testUpdateUserProfile_UserNotFound() {
        UUID nonExistentId = UUID.fromString("00000000-0000-0000-0000-000000000099");
        Mockito.when(userRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        ProfileUpdateDto dto = new ProfileUpdateDto();
        Exception exception = assertThrows(Exception.class, () ->
                profileService.updateProfile(dto, nonExistentId.toString(), "USER"));
        assertTrue(exception.getMessage().contains("User not found"));
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        User user1 = new User("John Doe 1", "john1@example.com", "+123456789", "password", "Address");
        User user2 = new User("John Doe 2", "john2@example.com", "+123456789", "password", "Address");

        List<User> expectedUsers = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(expectedUsers);

        // Act
        List<User> actualUsers = profileService.getAllUsers();

        // Assert
        assertEquals(expectedUsers.size(), actualUsers.size(), "The number of users should match");
        assertEquals(expectedUsers, actualUsers, "The returned list of users should match the expected list");

        // Verify that userRepository.findAll() was called exactly once
        verify(userRepository, times(1)).findAll();
    }

}
