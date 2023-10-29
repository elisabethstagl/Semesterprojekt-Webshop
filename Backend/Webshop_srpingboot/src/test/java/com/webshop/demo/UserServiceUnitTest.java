package com.webshop.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.webshop.demo.dto.RegistrationRequest;
import com.webshop.demo.model.ShoppingCart;
import com.webshop.demo.model.User;
import com.webshop.demo.repository.ShoppingCartRepository;
import com.webshop.demo.repository.UserRepository;
import com.webshop.demo.security.JwtUtil;
import com.webshop.demo.service.EntityNotFoundException;
import com.webshop.demo.service.UserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


public class UserServiceUnitTest {

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil; 
    
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

        // Mock the userRepository's behavior
        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(1L); // Simulate an ID assigned by the repository
            return savedUser;
        });

        // Mock the shoppingCartRepository's behavior
        when(shoppingCartRepository.save(any(ShoppingCart.class))).thenAnswer(invocation -> {
            ShoppingCart savedCart = invocation.getArgument(0);
            savedCart.setId(1L); // Simulate an ID assigned by the repository
            return savedCart;
        });

        // Mock the passwordEncoder's behavior
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");

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

    // Testfälle für authenticateUser(String username, String password)
    @Test
    void testAuthenticateUserWithEmptyCredentials() {
        Optional<String> tokenOptional = userService.authenticateUser("", "");
        assertFalse(tokenOptional.isPresent());
    }

    @Test
    void testAuthenticateUserWithInvalidCredentials() {
        when(userRepository.findByUsername("invalidUser")).thenReturn(Optional.empty());
        Optional<String> tokenOptional = userService.authenticateUser("invalidUser", "invalidPassword");
        assertFalse(tokenOptional.isPresent());
    }

    @Test
    void testAuthenticateUserWithNullCredentials() {
        Optional<String> tokenOptional = userService.authenticateUser(null, null);
        assertFalse(tokenOptional.isPresent());
    }

    // Testfälle für register(RegistrationRequest registrationRequest)
    @Test
    void testRegisterDuplicateUser() {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setUsername("existingUser");
        registrationRequest.setPassword("password");
        when(userRepository.existsByUsername("existingUser")).thenReturn(true);
        // Implementieren Sie Logik, um eine erwartete Exception zu handhaben, wenn Ihre
        // Anwendung eine solche wirft
    }

    @Test
    void testRegisterUserWithInvalidData() {
        RegistrationRequest invalidRequest = new RegistrationRequest();
        invalidRequest.setUsername(""); // Ungültiger Benutzername
        invalidRequest.setPassword(""); // Ungültiges Passwort
        // Implementieren Sie Logik, um eine erwartete Exception zu handhaben, wenn Ihre
        // Anwendung eine solche wirft
    }

    // Testfälle für deleteById(Long userId)
    @Test
    void testDeleteNonExistentUser() {
        Long nonExistentUserId = 999L;
        userService.deleteById(nonExistentUserId);
        verify(userRepository, times(1)).deleteById(nonExistentUserId);
    }

    @Test
    void testDeleteUserByNullId() {
        Long nullId = null;
        userService.deleteById(nullId);
        verify(userRepository, times(1)).deleteById(nullId);
    }

    // Testfälle für findAll()
    @Test
    void testFindAllUsersInEmptyDatabase() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());
        List<User> foundUsers = userService.findAll();
        assertTrue(foundUsers.isEmpty());
    }

    // Testfälle für findById(Long userId) und findByUsername(String username)
    @Test
    void testFindUserByInvalidId() {
        Long invalidId = -1L;
        when(userRepository.findById(invalidId)).thenReturn(Optional.empty());
        Optional<User> foundUser = userService.findById(invalidId);
        assertFalse(foundUser.isPresent());
    }

    @Test
    void testFindUserByInvalidUsername() {
        String invalidUsername = "nonExistentUser";
        when(userRepository.findByUsername(invalidUsername)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            userService.findByUsername(invalidUsername);
        });
    }

    @Test
    void testFindUserByNullId() {
        Long nullId = null;
        when(userRepository.findById(nullId)).thenReturn(Optional.empty());
        Optional<User> foundUser = userService.findById(nullId);
        assertFalse(foundUser.isPresent());
    }

    @Test
    void testFindUserByNullUsername() {
        String nullUsername = null;
        when(userRepository.findByUsername(nullUsername)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            userService.findByUsername(nullUsername);
        });
    }

}
