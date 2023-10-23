package com.webshop.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.webshop.demo.model.User;
import com.webshop.demo.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

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

}
