package id.ac.ui.cs.advprog.authprofile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.ac.ui.cs.advprog.authprofile.dto.ProfileUpdateDto;
import id.ac.ui.cs.advprog.authprofile.repository.UserRepository;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;

    @Autowired
    public ProfileServiceImpl(UserRepository userRepository) {
    }

    @Override
    public Object updateProfile(ProfileUpdateDto dto, String email, String role) throws Exception {
    }

    @Override
    public Object getProfile(String email, String role) throws Exception {
    }
}
