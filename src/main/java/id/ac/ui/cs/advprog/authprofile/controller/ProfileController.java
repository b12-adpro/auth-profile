package id.ac.ui.cs.advprog.authprofile.controller;

import id.ac.ui.cs.advprog.authprofile.dto.ProfileUpdateDto;
import id.ac.ui.cs.advprog.authprofile.service.ProfileService;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public ResponseEntity<?> getProfile(Authentication authprofile) {
        String userId = extractUserId(authprofile);
        String role = extractRole(authprofile);
        try {
            Object profile = profileService.getProfile(userId, role);
            return ResponseEntity.ok(profile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> updateProfile(@RequestBody ProfileUpdateDto dto, Authentication authprofile) {
        String userId = extractUserId(authprofile);
        String role = extractRole(authprofile);
        try {
            Object updatedProfile = profileService.updateProfile(dto, userId, role);
            return ResponseEntity.ok(updatedProfile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String extractUserId(Authentication authprofile) {
        Object principal = authprofile.getPrincipal();
        if (principal instanceof String) {
            return (String) principal;
        } else if (principal instanceof User) {
            return ((User) principal).getUsername();
        } else {
            throw new IllegalStateException("Unknown principal type: " + principal.getClass());
        }
    }

    private String extractRole(Authentication authprofile) {
        return authprofile.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllProfiles(Authentication authprofile) {
        String role = extractRole(authprofile);
        if (!role.equals("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }

        try {
            List<?> profiles = profileService.getAllUsers();
            return ResponseEntity.ok(profiles);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/id")
    public ResponseEntity<?> getUserIdByEmail(@RequestParam String email, Authentication authprofile) {
        String role = extractRole(authprofile);
        if (!role.equals("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        try {
            UUID userId = profileService.getUserIdByEmail(email);
            return ResponseEntity.ok(userId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}