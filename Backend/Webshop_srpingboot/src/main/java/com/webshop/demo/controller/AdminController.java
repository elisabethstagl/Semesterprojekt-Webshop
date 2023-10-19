package com.webshop.demo.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webshop.demo.dto.ProductDTO;
import com.webshop.demo.dto.UserDTO;
import com.webshop.demo.model.Product;
import com.webshop.demo.model.User;
import com.webshop.demo.service.UserService;

import jakarta.validation.Valid;

import com.webshop.demo.service.ProductService;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/admin")
public class AdminController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

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
            // System.out.println(updatedUserDTO.getRole());
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
        // Optional<Product> optionalProduct = productService.findById(id);
        // if (optionalProduct.isPresent()) {
        // Product product = optionalProduct.get();
        // // Delete the associated image file
        // String imagePath = product.getImageURL();
        // File imageFile = new File(uploadDir + imagePath);
        // if (imageFile.exists()) {
        // imageFile.delete();
        // }
        productService.deleteById(id);
    }
    // }

    @GetMapping("/products/{id}")
    public Product readProduct(@PathVariable Long id) {
        return productService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // Endpoint to create a product
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PostMapping(path = "/products/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(
            @RequestPart("product") @Valid String productJson,
            @RequestPart("productImage") MultipartFile file) throws IOException {

        // Deserializing product data
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDTO productDTO = objectMapper.readValue(productJson, ProductDTO.class);

        // Generating a unique file name using UUID
        String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        // Defining the path where you want to store the file
        File convertFile = new File(uploadDir + uniqueFileName);

        // Making sure the directory exists
        if (!convertFile.getParentFile().exists()) {
            convertFile.getParentFile().mkdirs();
        }
        convertFile.createNewFile();

        // Writing the file
        try (FileOutputStream fout = new FileOutputStream(convertFile)) {
            fout.write(file.getBytes());
        }

        // Creating and saving the product
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setQuantity(productDTO.getQuantity());
        product.setCategory(productDTO.getCategory());
        product.setImageURL("images/" + uniqueFileName);

        // Assuming there's a productService instance to handle saving
        return productService.save(product);
    }

    // Edit product
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PutMapping(path = "/products/edit/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Product edit(
            @PathVariable Long id,
            @RequestPart("product") @Valid String productJson,
            @RequestPart(value = "productImage", required = false) MultipartFile file) throws IOException {

        Product existingProduct;
        Optional<Product> optionalProduct = productService.findById(id);

        if (optionalProduct.isPresent()) {
            existingProduct = optionalProduct.get();
            // Handle existingProduct
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with ID: " + id);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        ProductDTO productDTO = objectMapper.readValue(productJson, ProductDTO.class);

        // Update the product details
        existingProduct.setName(productDTO.getName());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setQuantity(productDTO.getQuantity());
        existingProduct.setCategory(productDTO.getCategory());

        if (file != null && !file.isEmpty()) {
            // Generate a unique file name using UUID for the new image
            String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            // Define the path where you want to store the new image file
            File newImageFile = new File(uploadDir + uniqueFileName);

            if (!newImageFile.getParentFile().exists()) {
                newImageFile.getParentFile().mkdirs();
            }
            newImageFile.createNewFile();

            try (FileOutputStream fout = new FileOutputStream(newImageFile)) {
                fout.write(file.getBytes());
            }

            // Delete the old image file
            String oldImagePath = existingProduct.getImageURL();
            File oldImageFile = new File(uploadDir + oldImagePath);
            if (oldImageFile.exists()) {
                oldImageFile.delete();
            }

            existingProduct.setImageURL("images/" + uniqueFileName);
        }

        System.out.println(existingProduct.getImageURL());

        return productService.save(existingProduct);
    }

}
