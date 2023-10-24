package com.webshop.demo;

import com.webshop.demo.dto.ShoppingCartDto;
import com.webshop.demo.model.ShoppingCart;
import com.webshop.demo.service.ShoppingCartService;
import com.webshop.demo.service.UserService;
import com.webshop.demo.service.PositionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest  // Signifies that this is an integration test and starts the whole Spring context.
@AutoConfigureMockMvc  // Configures MockMvc which is used to test web layer without starting HTTP server.
public class ShoppingCartControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;  // MockMvc to simulate HTTP requests and responses.

    @Autowired
    private WebApplicationContext webApplicationContext;  // The web application context.

    @MockBean  // Mocks the ShoppingCartService bean in the Spring context.
    private ShoppingCartService shoppingCartService;

    @MockBean  // Mocks the UserService bean in the Spring context.
    private UserService userService;

    @MockBean  // Mocks the PositionService bean in the Spring context.
    private PositionService positionService;

    @BeforeEach  // Setup that runs before each test.
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();  // Initializes mockMvc with the web application context.
    }

    @Test  // Test annotation indicating this is a test method.
    public void testCreateShoppingCart() throws Exception {
        Long userId = 1L;
        ShoppingCart mockCart = new ShoppingCart();
        mockCart.setId(1L);
        // Mocking the service call to return our mockCart when saveShoppingCart is called with userId.
        when(shoppingCartService.saveShoppingCart(userId)).thenReturn(mockCart);

        // Perform POST request and validate the response.
        mockMvc.perform(post("/shoppingCart/create/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testViewCartById() throws Exception {
        Long userId = 1L;
        ShoppingCart mockCart = new ShoppingCart();
        mockCart.setId(1L);
        // Mocking the service call to return our mockCart when viewCart is called with userId.
        when(shoppingCartService.viewCart(userId)).thenReturn(mockCart);

        // Perform GET request and validate the response.
        mockMvc.perform(get("/shoppingCart/viewCart/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testAddProductToCart() throws Exception {
        Long userId = 1L;
        Long productId = 2L;
        ShoppingCart mockCart = new ShoppingCart();
        mockCart.setId(1L);
        mockCart.setPositions(new HashSet<>());  // Initializing the positions set in our mockCart.

        // Mocking the service call to return our mockCart when addProductToCart is called with userId and productId.
        when(shoppingCartService.addProductToCart(userId, productId)).thenReturn(mockCart);

        // Perform POST request and validate the response.
        mockMvc.perform(post("/shoppingCart/add/{productId}?userId={userId}", productId, userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

}

