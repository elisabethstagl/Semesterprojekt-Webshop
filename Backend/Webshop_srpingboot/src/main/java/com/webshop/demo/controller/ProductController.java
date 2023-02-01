package com.webshop.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.webshop.demo.model.Product;
import com.webshop.demo.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
    
    private ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    // READ

    @GetMapping()
    public List<Product> readAll(){
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Product read(@PathVariable Long id) {
        return productService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    
    // CREATE

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody Product product) {
        return productService.save(product);
    }


    // UPDATE 

    @PutMapping("{id}")
    public Product update(@PathVariable Long id, @RequestBody Product product) {
        return productService.update(id, product);
    }


    // DELETE 

    @DeleteMapping("/{id}")
    public Product delete(@PathVariable Long id) {
        return null;
    }

}
