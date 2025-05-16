package id.ac.ui.cs.advprog.authprofile.controller;

import id.ac.ui.cs.advprog.authprofile.dto.ProfileUpdateDto;
import id.ac.ui.cs.advprog.authprofile.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
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

    // Helper method to extract id from the principal.
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

    // Helper method to extract role from the authorities.
    private String extractRole(Authentication authprofile) {
        return authprofile.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
    }
}