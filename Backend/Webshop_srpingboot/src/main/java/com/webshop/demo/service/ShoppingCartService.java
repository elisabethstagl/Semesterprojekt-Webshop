package com.webshop.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.webshop.demo.model.Position;
import com.webshop.demo.model.Product;
import com.webshop.demo.model.ShoppingCart;
import com.webshop.demo.repository.PositionRepository;
import com.webshop.demo.repository.ProductRepository;
import com.webshop.demo.repository.ShoppingCartRepository;

/* Service ist für die Logik und Funktionalität verantwortlich.  */

@Service
public class ShoppingCartService {

    private ShoppingCartRepository shoppingCartRepos;
    private PositionRepository positionRepository;
    private ProductRepository productRepository;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepos, PositionRepository positionRepository, ProductRepository productRepository){
        this.shoppingCartRepos = shoppingCartRepos;
        this.positionRepository = positionRepository;
        this.productRepository = productRepository;
    }   

    // METHODEN

    public ShoppingCart save(ShoppingCart shoppingCart){
        return shoppingCartRepos.save(shoppingCart);
    }

    public ShoppingCart findByUserId(Long userId) {
        return shoppingCartRepos.findByUserId(userId);
    }


    public ShoppingCart addProductToCart(Long cartId, Long productId, int quantity) {
        ShoppingCart shoppingCart = shoppingCartRepos.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Shopping cart not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Check if the product is already in the shopping cart
        Optional<Position> existingPosition = shoppingCart.getPositions().stream()
                .filter(position -> position.getProduct().equals(product))
                .findFirst();

        if (existingPosition.isPresent()) {
            // Update the quantity if the product is already in the cart
            Position position = existingPosition.get();
            position.setQuantity(position.getQuantity() + quantity);
        } else {
            // Create a new position if the product is not in the cart
            Position newPosition = new Position(product, quantity);
            newPosition.setShoppingCart(shoppingCart);
            positionRepository.save(newPosition);
            shoppingCart.getPositions().add(newPosition);
        }

        return shoppingCartRepos.save(shoppingCart);
    }

    public ShoppingCart removeProductFromCart(Long cartId, Long positionId) {
        ShoppingCart shoppingCart = shoppingCartRepos.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Shopping cart not found"));

        Position positionToRemove = shoppingCart.getPositions().stream()
                .filter(position -> position.getId().equals(positionId))
                .findFirst()
                .orElse(null);

        if (positionToRemove != null) {
            shoppingCart.getPositions().remove(positionToRemove);
            positionRepository.delete(positionToRemove);
            return shoppingCartRepos.save(shoppingCart);
        }

        return shoppingCart;
    }

    public ShoppingCart updateProductQuantityInCart(Long cartId, Long positionId, int newQuantity) {
        ShoppingCart shoppingCart = shoppingCartRepos.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Shopping cart not found"));

        Position positionToUpdate = shoppingCart.getPositions().stream()
                .filter(position -> position.getId().equals(positionId))
                .findFirst()
                .orElse(null);

        if (positionToUpdate != null) {
            positionToUpdate.setQuantity(newQuantity);
            return shoppingCartRepos.save(shoppingCart);
        }

        return shoppingCart;
    }

}

