package id.ac.ui.cs.advprog.authprofile.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Generated;

@Entity
@Table(name = "users")
@Generated
@Data
public class User {

    public User() {
    }

    public User(String role, String fullName, String email, String phoneNumber, String password, String address) {
    }
}