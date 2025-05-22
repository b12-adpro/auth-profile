package id.ac.ui.cs.advprog.authprofile.model;

import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserGettersAndSettersFundraiser() {
        User user = new User();
        user.setId(UUID.fromString("00000000-0000-0000-0000-000000000005"));
        user.setRole("Fundraiser");
        user.setFullName("User Name Fund");
        user.setEmail("userf@example.com");
        user.setPhoneNumber("1112223333");
        user.setPassword("userSecret");
        user.setAddress("User address");

        assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000005"), user.getId());
        assertEquals("Fundraiser", user.getRole());
        assertEquals("User Name Fund", user.getFullName());
        assertEquals("userf@example.com", user.getEmail());
        assertEquals("1112223333", user.getPhoneNumber());
        assertEquals("userSecret", user.getPassword());
        assertEquals("User address", user.getAddress());
    }

    @Test
    void testUserToStringFundraiser() {
        User user = new User("Fundraiser", "User Name Fund", "userf@example.com", "1112223333", "userSecret", "User address");
        user.setId(UUID.fromString("00000000-0000-0000-0000-000000000006"));
        String toString = user.toString();
        assertTrue(toString.contains("User"));
        assertTrue(toString.contains("userf@example.com"));
    }

    @Test
    void testUserGettersAndSettersDonatur() {
        User user = new User();
        user.setId(UUID.fromString("00000000-0000-0000-0000-100000000005"));
        user.setRole("Donatur");
        user.setFullName("User Name Don");
        user.setEmail("userd@example.com");
        user.setPhoneNumber("1112223333");
        user.setPassword("userSecret");
        user.setAddress("User address");

        assertEquals(UUID.fromString("00000000-0000-0000-0000-100000000005"), user.getId());
        assertEquals("Donatur", user.getRole());
        assertEquals("User Name Don", user.getFullName());
        assertEquals("userd@example.com", user.getEmail());
        assertEquals("1112223333", user.getPhoneNumber());
        assertEquals("userSecret", user.getPassword());
        assertEquals("User address", user.getAddress());
    }

    @Test
    void testUserToStringDonatur() {
        User user = new User("Donatur", "User Name Don", "userd@example.com", "1112223333", "userSecret", "User address");
        user.setId(UUID.fromString("00000000-0000-0000-0000-100000000006"));
        String toString = user.toString();
        assertTrue(toString.contains("User"));
        assertTrue(toString.contains("userd@example.com"));
    }

}