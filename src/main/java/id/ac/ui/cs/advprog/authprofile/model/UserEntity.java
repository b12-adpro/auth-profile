package id.ac.ui.cs.advprog.authprofile.model;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Setter @Getter
@EntityScan
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "userEntity")
public class UserEntity {
    @Id
    private String email;
    private String password;
    private String name;
    private String address;
    private String phonenumber;
    private String role;
}