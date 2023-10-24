// package com.webshop.demo;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;

// import java.util.Optional;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mock;
// import org.springframework.boot.test.context.SpringBootTest;

// import com.webshop.demo.model.Position;
// import com.webshop.demo.model.Product;
// import com.webshop.demo.model.ShoppingCart;
// import com.webshop.demo.model.User;
// import com.webshop.demo.repository.PositionRepository;
// import com.webshop.demo.repository.UserRepository;
// import com.webshop.demo.service.PositionService;
// import com.webshop.demo.service.ProductService;
// import com.webshop.demo.service.ShoppingCartService;


// public class PositionServiceTests {

//     private PositionService positionService;

//     @Mock
//     private PositionRepository positionRepository;

//     @Mock
//     private UserRepository userRepository;

//     @Mock
//     private ShoppingCartService shoppingCartService;

//     @Mock
//     private ProductService productService;

//     @BeforeEach
//     public void setUp() {
//         positionService = new PositionService(positionRepository, userRepository, shoppingCartService, productService);
//     }

//     @Test
//     public void testFindPositionById() {
//         Long positionId = 1L;
//         Position position = new Position();
//         when(positionRepository.findById(positionId)).thenReturn(Optional.of(position));

//         Optional<Position> foundPosition = positionService.findById(positionId);

//         assertTrue(foundPosition.isPresent());
//         assertEquals(position, foundPosition.get());
//     }

//     @Test
//     public void testSavePositionWithNonExistentProduct() {
//         Long userId = 1L;
//         Long productId = 2L;

//         when(shoppingCartService.findByUserId(userId)).thenReturn(new ShoppingCart(new User()));
//         when(productService.findById(productId)).thenReturn(Optional.empty());

//         assertThrows(RuntimeException.class, () -> positionService.save(new Position(), userId, productId));
//     }

//     @Test
//     public void testSavePositionWithNewShoppingCartAndExistingProduct() {
//         Long userId = 1L;
//         Long productId = 2L;

//         Product product = new Product();

//         when(shoppingCartService.findByUserId(userId)).thenReturn(null); // Simulate no existing shopping cart
//         when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
//         when(productService.findById(productId)).thenReturn(Optional.of(product));
//         when(shoppingCartService.save(any(ShoppingCart.class))).thenAnswer(invocation -> invocation.getArgument(0));
//         when(positionRepository.save(any(Position.class))).thenAnswer(invocation -> invocation.getArgument(0));

//         Position newPosition = new Position();
//         Position savedPosition = positionService.save(newPosition, userId, productId);

//         assertNotNull(savedPosition);
//         assertEquals(product, savedPosition.getProduct());
//         verify(positionRepository, times(1)).save(newPosition);
//     }


//     @Test
//     public void testSavePositionWithExistingShoppingCartAndProduct() {
//         Long userId = 1L;
//         Long productId = 2L;

//         ShoppingCart shoppingCart = new ShoppingCart(new User());
//         Product product = new Product();

//         when(shoppingCartService.findByUserId(userId)).thenReturn(shoppingCart);
//         when(productService.findById(productId)).thenReturn(Optional.of(product));
//         when(positionRepository.save(any(Position.class))).thenAnswer(invocation -> invocation.getArgument(0));

//         Position newPosition = new Position();
//         Position savedPosition = positionService.save(newPosition, userId, productId);

//         assertNotNull(savedPosition);
//         assertEquals(shoppingCart, savedPosition.getShoppingCart());
//         assertEquals(product, savedPosition.getProduct());
//         verify(positionRepository, times(1)).save(newPosition);
//     }

// }
