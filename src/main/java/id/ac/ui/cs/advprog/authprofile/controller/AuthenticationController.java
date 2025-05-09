package id.ac.ui.cs.advprog.authprofile.controller;

import jakarta.annotation.security.PermitAll;
import id.ac.ui.cs.advprog.authprofile.dto.AuthRequest;
import id.ac.ui.cs.advprog.authprofile.dto.AuthResponse;
import id.ac.ui.cs.advprog.authprofile.dto.UserRegistrationDto;
import id.ac.ui.cs.advprog.authprofile.model.User;
import id.ac.ui.cs.advprog.authprofile.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PermitAll
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            AuthResponse response = authenticationService.login(authRequest);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PermitAll
    @PostMapping("/register/user")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDto registrationDto) {
        User user = authenticationService.registerUser(registrationDto);
        return ResponseEntity.ok(user);
    }

}