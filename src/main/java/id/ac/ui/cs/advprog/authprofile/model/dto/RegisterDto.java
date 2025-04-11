package id.ac.ui.cs.advprog.authprofile.model.dto;

import lombok.Data;

@Data
public class RegisterDto {
    private String email;
    private String password;
    private String role;
    private String address;
    private String phonenumber;
    private String name;
}