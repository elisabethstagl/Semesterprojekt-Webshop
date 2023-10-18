package com.webshop.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webshop.demo.model.Position;
import com.webshop.demo.model.Product;
import com.webshop.demo.model.ShoppingCart;
import com.webshop.demo.model.User;
import com.webshop.demo.repository.PositionRepository;
// import com.webshop.demo.repository.PositionRepository;
import com.webshop.demo.repository.ProductRepository;
import com.webshop.demo.repository.ShoppingCartRepository;
import com.webshop.demo.repository.UserRepository;

/* Service ist für die Logik und Funktionalität verantwortlich.  */

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepos;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;


    public ShoppingCart saveShoppingCart(Long userId) {

        // User userOpt = userRepository.findById(userId).orElseThrow(RuntimeException::new);
        // Long userId = userOpt.getId();
        ShoppingCart cartOpt = shoppingCartRepos.findByUserId(userId);
        if(!(cartOpt == null)){
            return cartOpt;
        }

        ShoppingCart shoppingCart = new ShoppingCart();
        User user = userRepository.findById(userId).get();
        user.setShoppingCart(shoppingCart);
        shoppingCart.setUser(user);
        System.out.println("shopping cart " + shoppingCart);
        return shoppingCartRepos.save(shoppingCart);
    }

    public ShoppingCart viewCart(Long userId) {
        userRepository.findById(userId).orElseThrow(RuntimeException::new);
        // Long userId = userOpt.getId();
        ShoppingCart cartOpt = shoppingCartRepos.findByUserId(userId);
        return cartOpt;
    }


    public ShoppingCart addProductToCart(Long userId, Long productId) {
        // Retrieve the user and product
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
    
        ShoppingCart shoppingCart = user.getShoppingCart();
        List<Position> positions = shoppingCart.getPositions();
    
        boolean productFoundInCart = false;
    
        // Check if the product is in the cart
        for (Position position : positions) {
            if (position.getProduct().equals(product)) {
                position.setQuantity(position.getQuantity() + 1);
                productFoundInCart = true;
                positionRepository.save(position);
                break;
            }
        }
    
        if (!productFoundInCart) {
            // Create a new position if the product is not in the cart
            Position newPosition = new Position();
            newPosition.setShoppingCart(shoppingCart);
            newPosition.setProduct(product);
            newPosition.setQuantity(1);
            positions.add(newPosition);
            positionRepository.save(newPosition);
        }
    
        // Save the updated shopping cart
        shoppingCart.setPositions(positions);
        shoppingCartRepos.save(shoppingCart);
        return shoppingCart;
    }
    

    public ShoppingCart removeProductFromCart(String userName, Long productId){
        User userOpt = userRepository.findByUsername(userName).orElseThrow(RuntimeException::new);

        Optional<Product> productOpt = productRepository.findById(productId);
        if(productOpt.isEmpty()){
            throw new RuntimeException("Product not found");
            }

        Long userId = userOpt.getId();
        ShoppingCart shoppingCart = shoppingCartRepos.findByUserId(userId);
        List<Position> positions = shoppingCart.getPositions();

        boolean productFoundInCart = false;
        Position positionToRemove = null;

        //Find position to remove
        for (Position position : positions){
            if (position.getProduct().equals(productOpt.get())){
                if (position.getQuantity() > 1){
                    position.setQuantity(position.getQuantity() - 1);
                } else {
                    positionToRemove = position;
                }
                productFoundInCart = true;
                break;
            }
        }

        if(positionToRemove != null){
            if(productFoundInCart){
            positions.remove(positionToRemove);
            positionRepository.delete(positionToRemove);
        } else{
            throw new RuntimeException("Product not found in cart");
        }
    }
        shoppingCart.setPositions(positions);
        shoppingCartRepos.save(shoppingCart);
        return shoppingCart;
    

    }

    public Integer viewQuantity(String username, Long productId){
        Integer quantity = 0;

        Optional<Product> productOpt = productRepository.findById(productId);
        if(productOpt.isEmpty()){
            throw new RuntimeException("Product not found");
        }

        User userOpt = userRepository.findByUsername(username).orElseThrow(RuntimeException::new);
        Long userId = userOpt.getId();
        ShoppingCart cartOpt = shoppingCartRepos.findByUserId(userId);
        List<Position> positions = cartOpt.getPositions();
        
        for(Position position : positions){
            System.out.println("position" + position.getProduct().getId());
            if (position.getProduct().getId().equals(productOpt.get().getId())){
                quantity += position.getQuantity();
                System.out.println("Quantity increased: " + quantity);
            }
        }

        return quantity;

    }


        // public ShoppingCart increaseQuantity (Long userId, Long productId){
        // Optional<User> userOptional = userRepository.findById(userId);
        // if (userOptional.isEmpty()){
        //     throw new RuntimeException("User not found");
        // }

        // Optional<Product> productOpt = productRepository.findById(productId);
        // if(productOpt.isEmpty()){
        //     throw new RuntimeException("Product not found");
        //     }

        // User user = userOptional.get();
        // ShoppingCart shoppingCart = user.getShoppingCart();
        // List<Position> positions = shoppingCart.getPositions();

        // boolean productFoundInCart = false;

        // //Find position to increase quantity
        // for(Position position : positions){
        //     if(position.getProduct().equals(productOpt.get())){
        //         position.setQuantity(position.getQuantity() + 1);
        //         productFoundInCart = true;
        //         break;
        //     }
        // }

        // if(!productFoundInCart){
        //     throw new RuntimeException("Product not found in cart");
        // }

        // shoppingCart.setPositions(positions);
        // shoppingCartRepos.save(shoppingCart);
        // return shoppingCart;
        // }


        // public ShoppingCart decreaseQuantity (Long userId, Long productId){
        
        //     Optional<User> userOptional = userRepository.findById(userId);
        // if (userOptional.isEmpty()){
        //     throw new RuntimeException("User not found");
        // }

        // Optional<Product> productOpt = productRepository.findById(productId);
        // if(productOpt.isEmpty()){
        //     throw new RuntimeException("Product not found");
        //     }

        // User user = userOptional.get();
        // ShoppingCart shoppingCart = user.getShoppingCart();
        // List<Position> positions = shoppingCart.getPositions();

        // boolean productFoundInCart = false;

        // //Find position to decrease quantity
        // for(Position position : positions){
        //     if(position.getProduct().equals(productOpt.get())){
        //         if (position.getQuantity() > 1){
        //             position.setQuantity(position.getQuantity() - 1);
        //         } else {
        //             positions.remove(position);
        //         }
        //         productFoundInCart = true;
        //         break;
        //     }
        // }

        // if(!productFoundInCart){
        //     throw new RuntimeException("Product not found in cart");
        // }

        // shoppingCart.setPositions(positions);
        // shoppingCartRepos.save(shoppingCart);
        // return shoppingCart;
        // }


    // public ShoppingCartService(ShoppingCartRepository shoppingCartRepos, PositionRepository positionRepository, ProductRepository productRepository){
    //     this.shoppingCartRepos = shoppingCartRepos;
    //     this.positionRepository = positionRepository;
    //     this.productRepository = productRepository;
    // }   

    // METHODEN

    // public ShoppingCart save(ShoppingCart shoppingCart){
    //     return shoppingCartRepos.save(shoppingCart);
    // }

    // public ShoppingCart findByUserId(Long userId) {
    //     return shoppingCartRepos.findByUserId(userId);
    // }


    // public ShoppingCart addProductToCart(Long cartId, Long productId, int quantity) {
    //     ShoppingCart shoppingCart = shoppingCartRepos.findById(cartId)
    //             .orElseThrow(() -> new RuntimeException("Shopping cart not found"));

    //     Product product = productRepository.findById(productId)
    //             .orElseThrow(() -> new RuntimeException("Product not found"));

    //     // Check if the product is already in the shopping cart
    //     Optional<Position> existingPosition = shoppingCart.getPositions().stream()
    //             .filter(position -> position.getProduct().equals(product))
    //             .findFirst();

    //     if (existingPosition.isPresent()) {
    //         // Update the quantity if the product is already in the cart
    //         Position position = existingPosition.get();
    //         position.setQuantity(position.getQuantity() + quantity);
    //     } else {
    //         // Create a new position if the product is not in the cart
    //         Position newPosition = new Position(product, quantity);
    //         newPosition.setShoppingCart(shoppingCart);
    //         positionRepository.save(newPosition);
    //         shoppingCart.getPositions().add(newPosition);
    //     }

    //     return shoppingCartRepos.save(shoppingCart);
    // }

    // public ShoppingCart removeProductFromCart(Long cartId, Long positionId) {
    //     ShoppingCart shoppingCart = shoppingCartRepos.findById(cartId)
    //             .orElseThrow(() -> new RuntimeException("Shopping cart not found"));

    //     Position positionToRemove = shoppingCart.getPositions().stream()
    //             .filter(position -> position.getId().equals(positionId))
    //             .findFirst()
    //             .orElse(null);

    //     if (positionToRemove != null) {
    //         shoppingCart.getPositions().remove(positionToRemove);
    //         positionRepository.delete(positionToRemove);
    //         return shoppingCartRepos.save(shoppingCart);
    //     }

    //     return shoppingCart;
    // }

    // public ShoppingCart updateProductQuantityInCart(Long cartId, Long positionId, int newQuantity) {
    //     ShoppingCart shoppingCart = shoppingCartRepos.findById(cartId)
    //             .orElseThrow(() -> new RuntimeException("Shopping cart not found"));

    //     Position positionToUpdate = shoppingCart.getPositions().stream()
    //             .filter(position -> position.getId().equals(positionId))
    //             .findFirst()
    //             .orElse(null);

    //     if (positionToUpdate != null) {
    //         positionToUpdate.setQuantity(newQuantity);
    //         return shoppingCartRepos.save(shoppingCart);
    //     }

    //     return shoppingCart;
    // }

}

