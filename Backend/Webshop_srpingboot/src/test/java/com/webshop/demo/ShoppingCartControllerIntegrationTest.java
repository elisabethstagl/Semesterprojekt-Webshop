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

@SpringBootTest
@AutoConfigureMockMvc
public class ShoppingCartControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private ShoppingCartService shoppingCartService;

    @MockBean
    private UserService userService;

    @MockBean
    private PositionService positionService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testCreateShoppingCart() throws Exception {
        Long userId = 1L;

        // Create a mock ShoppingCart object
        ShoppingCart mockCart = new ShoppingCart();
        mockCart.setId(1L);

        when(shoppingCartService.saveShoppingCart(userId)).thenReturn(mockCart);

        mockMvc.perform(post("/shoppingCart/create/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testViewCartById() throws Exception {
        Long userId = 1L;

        // Create a mock ShoppingCart object
        ShoppingCart mockCart = new ShoppingCart();
        mockCart.setId(1L);

        when(shoppingCartService.viewCart(userId)).thenReturn(mockCart);

        mockMvc.perform(get("/shoppingCart/viewCart/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testAddProductToCart() throws Exception {
        Long userId = 1L;
        Long productId = 2L;

        // Create a mock ShoppingCart object
        ShoppingCart mockCart = new ShoppingCart();
        mockCart.setId(1L);
        mockCart.setPositions(new HashSet<>()); // Initialize the positions set

        when(shoppingCartService.addProductToCart(userId, productId)).thenReturn(mockCart);

        mockMvc.perform(post("/shoppingCart/add/{productId}?userId={userId}", productId, userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

}
