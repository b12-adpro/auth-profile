package id.ac.ui.cs.advprog.authprofile.model.dto;

public class UserRegistrationRequest {
    private String email;
    private String password;

    public UserRegistrationRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() { return email; }
    public String getPassword() { return password; }
}
