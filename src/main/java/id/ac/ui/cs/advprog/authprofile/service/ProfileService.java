package id.ac.ui.cs.advprog.authprofile.service;

import id.ac.ui.cs.advprog.authprofile.dto.ProfileUpdateDto;

public interface ProfileService {
    Object updateProfile(ProfileUpdateDto dto, String email, String role) throws Exception;

    Object getProfile(String email, String role) throws Exception;
}