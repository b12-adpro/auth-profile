package id.ac.ui.cs.advprog.authprofile.service;

import id.ac.ui.cs.advprog.authprofile.model.dto.RegisterRequest;

public interface AuthService {
    String registerUser(RegisterRequest request);
}
