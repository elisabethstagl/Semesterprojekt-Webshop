// package com.webshop.demo;

// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
// import static org.hamcrest.Matchers.containsString;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.MediaType;
// import org.springframework.test.context.junit.jupiter.SpringExtension;
// import org.springframework.test.web.servlet.MockMvc;

// public class UserRegistrationTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @BeforeEach
//     public void setUp() {
//         // Hier können Sie Dinge einrichten, die vor jedem Testfall benötigt werden.
//     }

//     @Test
//     public void testRegisterNewUser() throws Exception {
//         // JSON string with all required fields
//         String userJson = "{"
//                 + "\"username\":\"newuser\", "
//                 + "\"password\":\"password\", "
//                 + "\"sex\":\"male\", " 
//                 + "\"firstName\":\"John\", "
//                 + "\"lastName\":\"Doe\", "
//                 + "\"address\":\"123 Main St\", "
//                 + "\"doornumber\":\"101\", "
//                 + "\"postalCode\":\"12345\", "
//                 + "\"city\":\"Anytown\", "
//                 + "\"email\":\"johndoe@example.com\""
//                 + "\"role\":\"USER\""
//                 + "}";

//         mockMvc.perform(post("/users/register")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(userJson))
//                 .andExpect(status().isCreated())
//                 .andExpect(content().string(containsString("Benutzer erfolgreich registriert")));
//     }

// }
