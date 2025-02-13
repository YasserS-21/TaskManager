package com.example.userauth.service;

import com.example.userauth.model.Users;
import com.example.userauth.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserService userService; // Service to be tested
    private UserRepository userRepository; // Mocked repository

    @BeforeEach
    void setUp() {
        // Create a mock UserRepository
        userRepository = Mockito.mock(UserRepository.class);

        // Inject the mock into UserService
        userService = new UserService(userRepository);
    }

    @Test
    void testRegisterUserSuccess() {
        // Arrange
        String username = "testuser";
        String email = "testuser@example.com";
        String password = "password123";

        // Mock the repository behavior (no duplicate username or email)
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        Users userToSave = new Users(username, email, password);
        when(userRepository.save(any(Users.class))).thenReturn(userToSave);

        // Act
        Users newUser = userService.registerUser(username, email, password);

        // Assert
        assertNotNull(newUser);
        assertEquals(username, newUser.getUsername());
        assertEquals(email, newUser.getEmail());
        assertEquals(password, newUser.getPassword());

        // Verify repository interactions
        verify(userRepository, times(1)).findByUsername(username);
        verify(userRepository, times(1)).findByEmail(email);
        verify(userRepository, times(1)).save(any(Users.class));
    }

    @Test
    void testRegisterUserFailsWhenUsernameExists() {
        // Arrange
        String username = "testuser";
        String email = "testuser@example.com";
        String password = "password123";

        // Mock repository to simulate existing username
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(new Users(username, email, password)));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(username, email, password);
        });

        assertEquals("Username is already taken!", exception.getMessage());

        // Verify repository interactions
        verify(userRepository, times(1)).findByUsername(username);
        verify(userRepository, never()).findByEmail(email); // Should not check email since username fails first
        verify(userRepository, never()).save(any(Users.class));
    }

    @Test
    void testRegisterUserFailsWhenEmailExists() {
        // Arrange
        String username = "testuser";
        String email = "testuser@example.com";
        String password = "password123";

        // Mock repository to simulate existing email
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty()); // Username is unique
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(new Users("anotheruser", email, password)));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(username, email, password);
        });

        assertEquals("Email is already in use!", exception.getMessage());

        // Verify repository interactions
        verify(userRepository, times(1)).findByUsername(username);
        verify(userRepository, times(1)).findByEmail(email);
        verify(userRepository, never()).save(any(Users.class));
    }
}