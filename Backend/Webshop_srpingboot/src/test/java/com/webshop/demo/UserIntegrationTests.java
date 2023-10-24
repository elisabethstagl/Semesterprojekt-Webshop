// package com.webshop.demo;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.test.context.TestPropertySource;

// import com.webshop.demo.dto.LoginRequest;
// import com.webshop.demo.dto.RegistrationRequest;
// import com.webshop.demo.model.User;
// import com.webshop.demo.repository.ShoppingCartRepository;
// import com.webshop.demo.repository.UserRepository;
// import com.webshop.demo.security.JwtUtil;
// import com.webshop.demo.service.UserService;

// import static org.junit.jupiter.api.Assertions.*;

// import java.util.Optional;

// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.anyString;
// import static org.mockito.Mockito.*;

// @TestPropertySource(locations = "classpath:application-integrationtest.properties")
// @SpringBootTest
// @AutoConfigureMockMvc
// public class UserIntegrationTests {

//     @Mock
//     private ShoppingCartRepository shoppingCartRepository;

//     @Mock
//     private UserRepository userRepository;

//     @Mock
//     private PasswordEncoder passwordEncoder;

//     @Mock
//     private JwtUtil jwtUtil;

//     @InjectMocks
//     private UserService userService;

//     @BeforeEach
//     public void setUp() {
//         MockitoAnnotations.openMocks(this);

//         // Mocking the behavior of userRepository and shoppingCartRepository
//         when(userRepository.existsByUsername(anyString())).thenReturn(false);

//         when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

//         when(shoppingCartRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
//     }

//     @Test
//     public void userRegistrationAndLoginFlowTest() {
//         // Register a New User
//         RegistrationRequest registrationRequest = new RegistrationRequest();
//         registrationRequest.setUsername("testUser");
//         registrationRequest.setPassword("testPassword");

//         // Filling out the remaining fields for registration
//         registrationRequest.setSex("Male"); // or "Female" or other appropriate values
//         registrationRequest.setFirstName("John");
//         registrationRequest.setLastName("Doe");
//         registrationRequest.setAddress("123 Main St");
//         registrationRequest.setDoornumber("1A");
//         registrationRequest.setPostalCode("12345");
//         registrationRequest.setCity("TestCity");
//         registrationRequest.setEmail("testUser@example.com");
//         registrationRequest.setUsername("testUser");
//         registrationRequest.setProfilePicture(null);

//         User registeredUser = userService.register(registrationRequest);
//         assertNotNull(registeredUser, "Expected user to be registered");
//         assertEquals("testUser", registeredUser.getUsername(), "Expected registered username to match");

//         // Login with the Newly Registered User's Credentials
//         LoginRequest loginRequest = new LoginRequest("testUser", "testPassword");
//         Optional<String> tokenOptional = userService.authenticateUser(loginRequest.getUsername(),
//                 loginRequest.getPassword());

//         assertTrue(tokenOptional.isPresent(), "Expected token to be present");
//         String token = tokenOptional.get();
//         assertNotNull(token, "Token should not be null");
//     }

// }
