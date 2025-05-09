package id.ac.ui.cs.advprog.authprofile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import id.ac.ui.cs.advprog.authprofile.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}