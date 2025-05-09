package id.ac.ui.cs.advprog.authprofile.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import id.ac.ui.cs.advprog.authprofile.dto.AuthRequest;
import id.ac.ui.cs.advprog.authprofile.dto.UserRegistrationDto;
import id.ac.ui.cs.advprog.authprofile.service.AuthenticationService;

public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
    }

    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
    }

    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDto registrationDto) {
    }

}