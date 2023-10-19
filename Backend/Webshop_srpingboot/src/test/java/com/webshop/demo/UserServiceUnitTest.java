package com.webshop.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.webshop.demo.dto.RegistrationRequest;
import com.webshop.demo.model.User;
import com.webshop.demo.repository.UserRepository;
import com.webshop.demo.security.JwtUtil;
import com.webshop.demo.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceUnitTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil; // Assuming JwtUtil is a bean with a non-static generateToken method

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAuthenticateUser_ValidCredentials() {
        // Given
        User mockUser = new User();
        mockUser.setUsername("testUser");
        mockUser.setPassword("hashedPassword");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("testPassword", "hashedPassword")).thenReturn(true);
        when(jwtUtil.generateToken(mockUser)).thenReturn("mockToken");

        // When
        Optional<String> tokenOptional = userService.authenticateUser("testUser", "testPassword");

        // Then
        assertTrue(tokenOptional.isPresent());
        assertEquals("mockToken", tokenOptional.get());
    }

    @Test
    public void testAuthenticateUser_InvalidCredentials() {
        // Given
        User mockUser = new User();
        mockUser.setUsername("testUser");
        mockUser.setPassword("hashedPassword");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("wrongPassword", "hashedPassword")).thenReturn(false);

        // When
        Optional<String> tokenOptional = userService.authenticateUser("testUser", "wrongPassword");

        // Then
        assertFalse(tokenOptional.isPresent());
    }

    @Test
    public void testAuthenticateUser_UserNotFound() {
        // Given
        User mockUser = new User();
        mockUser.setUsername("testUser");
        mockUser.setPassword("1234");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(mockUser));

        // When
        Optional<String> tokenOptional = userService.authenticateUser("testUser", "1234");

        // Then
        assertFalse(tokenOptional.isPresent());
    }

    @Test
    public void testRegisterUser() {
        // Prepare a registration request
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setUsername("newuser");
        registrationRequest.setPassword("password");

        // Mock the repository's behavior
        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(1L); // Simulate an ID assigned by the repository
            return savedUser;
        });

        // Register a new user
        User registeredUser = userService.register(registrationRequest);

        assertNotNull(registeredUser);
        assertEquals("newuser", registeredUser.getUsername());
        // You can add more assertions to verify other properties of the registered
        // user.
    }

    @Test
    public void testDeleteUser() {
        // Mock the repository's behavior to delete a user
        Long userId = 1L;
        userService.deleteById(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void testFindAllUsers() {
        // Mock the repository's behavior to return a list of users
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());

        when(userRepository.findAll()).thenReturn(users);

        List<User> foundUsers = userService.findAll();

        assertEquals(users.size(), foundUsers.size());
    }

    @Test
    public void testFindUserByIdNotFound() {
        // Mock the repository's behavior to return an empty optional for a non-existent
        // user
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Optional<User> foundUser = userService.findById(userId);

        assertTrue(foundUser.isEmpty());
    }

    @Test
    public void testFindUserById() {
        // Mock the repository's behavior to return a user by ID
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.findById(userId);

        assertTrue(foundUser.isPresent());
        assertEquals(userId, foundUser.get().getId());
    }

    @Test
    public void testFindByUsername() {
        // Mock the repository's behavior to return a user by username
        String username = "knownuser";
        User user = new User();
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        User foundUser = userService.findByUsername(username);

        assertNotNull(foundUser);
        assertEquals(username, foundUser.getUsername());
    }

    
    @Test
    public void testSaveUser() {
        User user = new User();
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.save(user);

        assertEquals(user, savedUser);
    }

}
