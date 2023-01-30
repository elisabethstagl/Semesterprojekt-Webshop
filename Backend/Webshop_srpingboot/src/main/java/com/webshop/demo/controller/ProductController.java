package com.webshop.demo.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webshop.demo.model.Product;
import com.webshop.demo.repository.ProductRepository;

@RestController
@RequestMapping("/products")
public class ProductController {
    
    @Autowired
    private ProductRepository productRepos;

    @GetMapping
    public List<Product> findAllProducts(){
        return productRepos.findAll();
    }

    @GetMapping("/{type}")
    public List<Product> findAllProductsByType(@PathVariable String type) {
        return productRepos.findByType(type);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        product = productRepos.save(product);
        return ResponseEntity.created(URI.create("http://localhost:8080/products")).body(product);
    }

}
