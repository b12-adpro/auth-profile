package id.ac.ui.cs.advprog.authprofile.service;

import id.ac.ui.cs.advprog.authprofile.dto.ProfileUpdateDto;
import id.ac.ui.cs.advprog.authprofile.model.User;
import id.ac.ui.cs.advprog.authprofile.repository.UserRepository;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;


@Service
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;

    @Autowired
    public ProfileServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Object updateProfile(ProfileUpdateDto dto, String userId, String role) throws Exception {
        if ("USER".equalsIgnoreCase(role)) {
            User user = userRepository.findById(UUID.fromString(userId))
                    .orElseThrow(() -> new Exception("User not found"));
            if(dto.getFullName() != null) {
                user.setFullName(dto.getFullName());
            }
            if(dto.getPhoneNumber() != null) {
                user.setPhoneNumber(dto.getPhoneNumber());
            }
            if(dto.getAddress() != null) {
                user.setAddress(dto.getAddress());
            }
            if(dto.getPassword() != null) {
                String hashed = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt());
                user.setPassword(hashed);
            }
            return userRepository.save(user);
        } else {
            throw new Exception("Profile update not allowed for role: " + role);
        }
    }

    @Override
    public Object getProfile(String userId, String role) throws Exception {
        if ("USER".equalsIgnoreCase(role)) {
            return userRepository.findById(UUID.fromString(userId))
                    .orElseThrow(() -> new Exception("User not found"));
        } else {
            throw new Exception("Profile retrieval not allowed for role: " + role);
        }
    }
}
