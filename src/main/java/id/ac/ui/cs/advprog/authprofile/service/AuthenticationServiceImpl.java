package id.ac.ui.cs.advprog.authprofile.service;

import id.ac.ui.cs.advprog.authprofile.dto.AuthRequest;
import id.ac.ui.cs.advprog.authprofile.dto.AuthResponse;
import id.ac.ui.cs.advprog.authprofile.dto.UserRegistrationDto;
import id.ac.ui.cs.advprog.authprofile.model.User;
import id.ac.ui.cs.advprog.authprofile.model.Admin;
import id.ac.ui.cs.advprog.authprofile.repository.AdminRepository;
import id.ac.ui.cs.advprog.authprofile.repository.UserRepository;
import id.ac.ui.cs.advprog.authprofile.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthenticationServiceImpl(AdminRepository adminRepository,
                                     UserRepository userRepository,
                                     JwtTokenProvider jwtTokenProvider) {
                                                this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public AuthResponse login(AuthRequest request) throws Exception {
        String email = request.getEmail();
        String rawPassword = request.getPassword();
        String role = null;
        String storedHashedPassword = null;
        String userId = null;

        // Check Admins first
        Optional<Admin> adminOpt = adminRepository.findByEmail(email);
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            storedHashedPassword = admin.getPassword();
            role = "ADMIN";
            userId = admin.getId().toString();
        } else {
            Optional<User> userOpt = userRepository.findByEmail(email);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                storedHashedPassword = user.getPassword();
                role = "USER";
                userId = user.getId().toString();
            }
        }

        if (storedHashedPassword == null) {
            throw new Exception("No account found for email: " + email);
        }

        // Verify password using BCrypt.
        if (!BCrypt.checkpw(rawPassword, storedHashedPassword)) {
            throw new Exception("Invalid password for email: " + email);
        }

        String token = jwtTokenProvider.generateToken(userId, role);
        return new AuthResponse(token);
    }

    @Override
    public User registerUser(UserRegistrationDto registrationDto) {
        String hashedPassword = BCrypt.hashpw(registrationDto.getPassword(), BCrypt.gensalt());
        User user = new User(
                registrationDto.getRole(),
                registrationDto.getFullName(),
                registrationDto.getEmail(),
                registrationDto.getPhoneNumber(),
                hashedPassword,
                registrationDto.getAddress()
        );
        return userRepository.save(user);
    }

}