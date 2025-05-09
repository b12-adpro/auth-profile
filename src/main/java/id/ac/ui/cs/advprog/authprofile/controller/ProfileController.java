package id.ac.ui.cs.advprog.authprofile.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authprofile;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.ac.ui.cs.advprog.authprofile.dto.ProfileUpdateDto;
import id.ac.ui.cs.advprog.authprofile.service.ProfileService;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
    }

    public ResponseEntity<?> getProfile(authprofile authprofile) {
    }

    public ResponseEntity<?> updateProfile(@RequestBody ProfileUpdateDto dto, authprofile authprofile) {
    }
}