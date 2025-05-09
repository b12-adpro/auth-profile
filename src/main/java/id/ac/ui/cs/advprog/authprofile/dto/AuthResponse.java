package id.ac.ui.cs.advprog.authprofile.dto;

import lombok.Data;
import lombok.Generated;

@Generated
@Data
public class AuthResponse {
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }
}