package id.ac.ui.cs.advprog.authprofile.config;

import id.ac.ui.cs.advprog.authprofile.model.Admin;
import id.ac.ui.cs.advprog.authprofile.repository.AdminRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadAdmin(AdminRepository adminRepository) {
        return args -> {
            // Credentials
            String adminEmail = "admin@example.com";
            // Check admin acc
            if (adminRepository.findByEmail(adminEmail).isEmpty()) {
                String rawPassword = "admin123";
                // Encrypt pw
                String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());

                Admin admin = new Admin("Default Admin", adminEmail, "1234567890", hashedPassword);

                adminRepository.save(admin);
                System.out.println("Default admin account created.");
            } else {
                System.out.println("Admin account already exists.");
            }
        };
    }
}