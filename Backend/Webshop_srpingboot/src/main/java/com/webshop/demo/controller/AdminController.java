package com.webshop.demo.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webshop.demo.dto.ProductDTO;
import com.webshop.demo.dto.UserDTO;
import com.webshop.demo.model.Product;
import com.webshop.demo.model.User;
import com.webshop.demo.service.AdminServiceImpl;
import com.webshop.demo.service.UserService;

import jakarta.validation.Valid;

import com.webshop.demo.service.ProductService;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private AdminServiceImpl adminServiceImpl;

    // Endpoint to list all registered users
    @GetMapping("/users")
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    public ResponseEntity<List<User>> listUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO updatedUserDTO) {
        try {
            System.out.println(updatedUserDTO.getRole());
            User user = userService.update(id, updatedUserDTO);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint to get user details by ID
    @GetMapping("/users/{id}")
    public User read(@PathVariable Long id) {
        return userService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // Endpoint to delete a user by ID
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
    }

    // Endpoint to promote a user to admin
    @PostMapping("/users/{userId}/promote")
    public ResponseEntity<String> promoteToAdmin(@PathVariable Long userId) {
        boolean promoted = adminServiceImpl.promoteUserToAdmin(userId);
        if (promoted) {
            return ResponseEntity.ok("User promoted to admin.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ---------------------------------------------PRODUCTS---------------------------------------------

    // Endpoint to list all products
    @GetMapping("/products")
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    public List<Product> readAll() {
        return productService.findAll();
    }

    // Endpoint to delete a product by ID
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id,
            @RequestPart("updatedProductDTO") String updatedProductDTOJson,
            @RequestPart(required = false) MultipartFile product_img) {
        try {
            ProductDTO updatedProductDTO = new ObjectMapper().readValue(updatedProductDTOJson, ProductDTO.class);
            Product product = productService.update(id, updatedProductDTO);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // create new product
    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody @Valid Product product) {
        return productService.save(product);
    }

}
