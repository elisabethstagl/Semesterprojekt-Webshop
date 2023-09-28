package com.webshop.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Optional;
import java.util.List;

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
    public void testGetUserById() throws Exception {
        List<User> users = userRepository.findAll();
        assertEquals(0, users.size());
        mockMvc.perform(get("/users/100")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound());
    }

    @Test
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testCreateUser() throws Exception {
        // Create a JSON representation of the user data
        String newUserJson = "{"
                + "\"id\":1,"
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
        assertEquals(newUserJson, user);

        // Optionally, you can perform additional checks here to verify the created
        // user.
        // For example, you can retrieve the user from the database and assert its
        // attributes.
        Optional<User> savedUser = userRepository.findByUsername("johndoe");
        assertTrue(savedUser.isPresent());
        assertEquals("password", savedUser.get().getPassword());
    }

    

    // @Test
    // public void testCreateUser() throws Exception {
    // // Hier musst du ein JSON-Objekt erstellen, das einen neuen Benutzer
    // darstellt
    // // und es als Request-Body senden.
    // String newUserJson = "{\"username\":\"newUser\",\"password\":\"password\"}";

    // mockMvc.perform(post("/users")
    // .content(newUserJson)
    // .contentType(MediaType.APPLICATION_JSON))
    // .andExpect(status().isCreated())
    // .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    // }

    // @Test
    // public void testUpdateUser() throws Exception {
    // // Hier musst du ein JSON-Objekt erstellen, das die aktualisierten
    // Benutzerdaten darstellt
    // // und es als Request-Body senden.
    // String updatedUserJson =
    // "{\"username\":\"updatedUser\",\"password\":\"newPassword\"}";

    // mockMvc.perform(put("/users/1")
    // .content(updatedUserJson)
    // .contentType(MediaType.APPLICATION_JSON))
    // .andExpect(status().isOk())
    // .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    // }

    // @Test
    // public void testDeleteUser() throws Exception {
    // mockMvc.perform(delete("/users/1"))
    // .andExpect(status().isOk());
    // }
}
