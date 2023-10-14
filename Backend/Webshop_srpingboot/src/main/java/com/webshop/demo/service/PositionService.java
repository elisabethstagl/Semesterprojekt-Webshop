package com.webshop.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.webshop.demo.model.Position;
import com.webshop.demo.model.Product;
import com.webshop.demo.model.ShoppingCart;
import com.webshop.demo.model.User;
import com.webshop.demo.repository.PositionRepository;
import com.webshop.demo.repository.UserRepository;

/* Service ist für die Logik und Funktionalität verantwortlich.  */

@Service
public class PositionService {

    private final PositionRepository positionRepos;
    private final UserRepository userRepository;

    private final ShoppingCartService shoppingCartService;
    private final ProductService productService;
    

    // CONSTRUCTOR

    public PositionService(PositionRepository positionRepos,
            UserRepository userRepos,
            ShoppingCartService shoppingCartService,
            ProductService productService) {
        this.positionRepos = positionRepos;
        this.userRepository = userRepos;
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
    }

    // METHODEN


    public Position save(Position position, Long userId, Long productId) {
        ShoppingCart shoppingCart = shoppingCartService.findByUserId(userId);
    
        if (shoppingCart == null) {
            Optional<User> user = userRepository.findById(userId);
    
            if (user.isPresent()) {
                shoppingCart = shoppingCartService.save(new ShoppingCart(user.get()));
            } else {
                // TO DO: throw 403 error, user does not exist
                throw new RuntimeException("User does not exist");
            }
        }
    
        Optional<Product> product = productService.findById(productId);
    
        if (product.isEmpty()) {
            // TO DO: throw 400 bad request, product does not exist
            throw new RuntimeException("Product does not exist");
        }
    
        position.setShoppingCart(shoppingCart);
        position.setProduct(product.get());
    
        return positionRepos.save(position);
    }
    

    public void deletePosition(Long positionId) {
        Position position = positionRepos.findById(positionId)
            .orElseThrow(() -> new RuntimeException("Position not found"));
        
        positionRepos.delete(position);
    }
    
    public Position adjustQuantity(Long positionId, int newQuantity) {
        Position position = positionRepos.findById(positionId)
            .orElseThrow(() -> new RuntimeException("Position not found"));
        
        if (newQuantity <= 0) {
            // You may want to handle this case differently, such as removing the position.
            throw new RuntimeException("Invalid quantity");
        }
    
        position.setQuantity(newQuantity);
        return positionRepos.save(position);
    }
    

    

    

    

}
