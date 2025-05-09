package id.ac.ui.cs.advprog.authprofile.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import id.ac.ui.cs.advprog.authprofile.repository.AdminRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadAdmin(AdminRepository adminRepository) {
    }
}