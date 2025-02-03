package com.example.taskmanager.repository;

import com.example.taskmanager.model.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByEmail() {
        Users user = new Users("testing", "testing@example.com", "password");
        userRepository.save(user);

        Optional<Users> userOptional = userRepository.findByEmail("testing@example.com");
        assertTrue(userOptional.isPresent());
        assertEquals(userOptional.get().getEmail(), user.getEmail());
    }

    @Test
    public void testFindByUsername() {
        Users user = new Users("testing", "testing@example.com", "password");
        userRepository.save(user);

        Optional<Users> userOptional = userRepository.findByUsername("testing");
        assertTrue(userOptional.isPresent());
        assertEquals(userOptional.get().getUsername(), user.getUsername());
    }

    @Test
    public void testSaveAndRetrieveUser() {
        Users user = new Users("testing", "testing@example.com", "password");
        userRepository.save(user);

        Optional<Users> userOptional = userRepository.findByUsername("testing");
        assertTrue(userOptional.isPresent());

        Users savedUser = userOptional.get();
        assertEquals(savedUser.getUsername(), user.getUsername());
        assertEquals(savedUser.getEmail(), user.getEmail());
        assertNotNull(savedUser.getId());
    }

    @Test
    public void testSaveAndRetrieveAllUsers() {
        userRepository.save(new Users("john_doe", "john@example.com", "password123"));
        userRepository.save(new Users("jane_doe", "jane@example.com", "mypassword"));

        assertEquals(2, userRepository.findAll().size());
    }

    @Test
    public void testSaveDuplicateUsernameThrowsException() {
        // Save a user
        userRepository.save(new Users("duplicate_user", "email1@example.com", "password123"));

        // Try saving another user with the same username
        assertThrows(Exception.class, () -> {
            userRepository.save(new Users("duplicate_user", "email2@example.com", "mypassword"));
        });
    }

    @Test
    public void testSaveDuplicateEmailThrowsException() {
        // Save a user
        userRepository.save(new Users("user1", "duplicate_email@example.com", "password123"));

        // Try saving another user with the same email
        assertThrows(Exception.class, () -> {
            userRepository.save(new Users("user2", "duplicate_email@example.com", "mypassword"));
        });
    }
}
