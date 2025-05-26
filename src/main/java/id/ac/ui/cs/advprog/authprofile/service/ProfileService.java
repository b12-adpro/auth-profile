package id.ac.ui.cs.advprog.authprofile.service;

import java.util.List;
import java.util.UUID;

import id.ac.ui.cs.advprog.authprofile.model.User;
import id.ac.ui.cs.advprog.authprofile.dto.ProfileUpdateDto;

public interface ProfileService {
    Object updateProfile(ProfileUpdateDto dto, String userId, String role);
    Object getProfile(String userId, String role);
    List<User> getAllUsers();
    UUID getUserIdByEmail(String email);
}
