// package com.webshop.demo;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.springframework.security.crypto.password.PasswordEncoder;

// import com.webshop.demo.model.User;
// import com.webshop.demo.repository.UserRepository;
// import com.webshop.demo.security.JwtUtil;
// import com.webshop.demo.service.UserService;

// import java.util.Optional;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

// public class UserServiceUnitTest {

//     @Mock
//     private UserRepository userRepository;

//     @Mock
//     private PasswordEncoder passwordEncoder;

//     @Mock
//     private JwtUtil jwtUtil; // Assuming JwtUtil is a bean with a non-static generateToken method

//     @InjectMocks
//     private UserService userService;

//     @BeforeEach
//     public void setUp() {
//         MockitoAnnotations.openMocks(this);
//     }

//     @Test
//     public void testAuthenticateUser_ValidCredentials() {
//         // Given
//         User mockUser = new User();
//         mockUser.setUsername("testUser");
//         mockUser.setPassword("hashedPassword");
//         when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(mockUser));
//         when(passwordEncoder.matches("testPassword", "hashedPassword")).thenReturn(true);
//         when(jwtUtil.generateToken(mockUser)).thenReturn("mockToken");

//         // When
//         Optional<String> tokenOptional = userService.authenticateUser("testUser", "testPassword");

//         // Then
//         assertTrue(tokenOptional.isPresent());
//         assertEquals("mockToken", tokenOptional.get());
//     }

//     @Test
//     public void testAuthenticateUser_InvalidCredentials() {
//         // Given
//         User mockUser = new User();
//         mockUser.setUsername("testUser");
//         mockUser.setPassword("hashedPassword");
//         when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(mockUser));
//         when(passwordEncoder.matches("wrongPassword", "hashedPassword")).thenReturn(false);

//         // When
//         Optional<String> tokenOptional = userService.authenticateUser("testUser", "wrongPassword");

//         // Then
//         assertFalse(tokenOptional.isPresent());
//     }

//     @Test
//     public void testAuthenticateUser_UserNotFound() {
//         // Given
//         User mockUser = new User();
//         mockUser.setUsername("testUser");
//         mockUser.setPassword("1234");
//         when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(mockUser));
        
//         // When
//         Optional<String> tokenOptional = userService.authenticateUser("testUser", "1234");

//         // Then
//         assertFalse(tokenOptional.isPresent());
//     }
// }

