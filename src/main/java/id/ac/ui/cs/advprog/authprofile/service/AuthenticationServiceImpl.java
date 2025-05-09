package id.ac.ui.cs.advprog.authprofile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.ac.ui.cs.advprog.authprofile.dto.AuthRequest;
import id.ac.ui.cs.advprog.authprofile.dto.AuthResponse;
import id.ac.ui.cs.advprog.authprofile.dto.UserRegistrationDto;
import id.ac.ui.cs.advprog.authprofile.model.User;
import id.ac.ui.cs.advprog.authprofile.repository.AdminRepository;
import id.ac.ui.cs.advprog.authprofile.repository.UserRepository;
import id.ac.ui.cs.advprog.authprofile.security.JwtTokenProvider;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthenticationServiceImpl(AdminRepository adminRepository,
                                     UserRepository userRepository,
                                     JwtTokenProvider jwtTokenProvider) {
    }

    @Override
    public AuthResponse login(AuthRequest request) throws Exception {
    }

    @Override
    public User registerUser(UserRegistrationDto registrationDto) {
    }

}