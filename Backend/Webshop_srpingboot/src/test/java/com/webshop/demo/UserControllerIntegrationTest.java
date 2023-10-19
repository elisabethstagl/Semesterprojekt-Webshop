package com.webshop.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webshop.demo.dto.LoginRequest;
import com.webshop.demo.dto.RegistrationRequest;
import com.webshop.demo.model.User;
import com.webshop.demo.repository.UserRepository;
import com.webshop.demo.service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    // @Test
    // public void testGetAllUsers() throws Exception {
    // mockMvc.perform(get("/users")
    // .contentType(MediaType.APPLICATION_JSON))
    // .andExpect(status().isOk());
    // }

    @Test
    public void testCreateUser() throws Exception {
        // Create a JSON representation of the user data
        String newUserJson = "{"
                + "\"sex\":\"Male\","
                + "\"firstName\":\"John\","
                + "\"lastName\":\"Doe\","
                + "\"address\":\"123 Main St\","
                + "\"doornumber\":\"Apt 4B\","
                + "\"postalCode\":\"12345\","
                + "\"city\":\"Sample City\","
                + "\"email\":\"john.doe@example.com\","
                + "\"username\":\"johndoe\","
                + "\"password\":\"password\","
                + "\"role\":\"USER\","
                + "\"profilePicture\":\"null\""
                + "}";

        // Perform an HTTP POST request to create a new user
        MvcResult result = mockMvc.perform(post("/users")
                .content(newUserJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String user = result.getResponse().getContentAsString();
        assertTrue(user.contains("johndoe"));

        Optional<User> savedUser = userRepository.findByUsername("johndoe");
        assertTrue(savedUser.isPresent());
        assertEquals("password", savedUser.get().getPassword());
    }

    // @Test
    // public void testAuthenticateUser() throws Exception {
    // // First, register a new user
    // RegistrationRequest registrationRequest = new RegistrationRequest();
    // registrationRequest.setUsername("testUser");
    // registrationRequest.setPassword("testPassword");
    // String jsonRequest = objectMapper.writeValueAsString(registrationRequest);
    // mockMvc.perform(post("/users/register")
    // .content(jsonRequest)
    // .contentType(MediaType.APPLICATION_JSON));

    // // Now, authenticate the user and expect a token
    // LoginRequest loginRequest = new LoginRequest();
    // loginRequest.setUsername("testUser");
    // loginRequest.setPassword("testPassword");
    // String loginJson = objectMapper.writeValueAsString(loginRequest);

    // mockMvc.perform(post("/users/login")
    // .content(loginJson)
    // .contentType(MediaType.APPLICATION_JSON))
    // .andExpect(status().isOk())
    // .andExpect(jsonPath("$.token", notNullValue()));
    // }

}
