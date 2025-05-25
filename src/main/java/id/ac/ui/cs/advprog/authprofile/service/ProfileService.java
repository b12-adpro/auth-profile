package id.ac.ui.cs.advprog.authprofile.service;

import java.util.List;
import id.ac.ui.cs.advprog.authprofile.model.User;
import id.ac.ui.cs.advprog.authprofile.dto.ProfileUpdateDto;

public interface ProfileService {
    Object updateProfile(ProfileUpdateDto dto, String userId, String role) throws Exception;
    Object getProfile(String userId, String role) throws Exception;
    List<User> getAllUsers();
}
