package id.ac.ui.cs.advprog.authprofile.service;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import id.ac.ui.cs.advprog.authprofile.model.User;
import id.ac.ui.cs.advprog.authprofile.model.dto.UserRegistrationRequest;
// import id.ac.ui.cs.advprog.authprofile.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public User register(UserRegistrationRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
        user.setRole(Role.USER);
        return userRepository.save(user);
    }
}
