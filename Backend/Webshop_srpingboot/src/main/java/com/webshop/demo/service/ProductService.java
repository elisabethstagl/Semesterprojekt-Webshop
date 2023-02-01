package com.webshop.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.webshop.demo.model.Product;
import com.webshop.demo.repository.ProductRepository;

@Service
public class ProductService {
    
    private ProductRepository productRepos;

    public ProductService(ProductRepository productRepos){
        this.productRepos = productRepos;
    }

    public Product save(Product product) {
        return productRepos.save(product);
    }

    public Optional<Product> findById(Long id){
        return productRepos.findById(id);
    }

    public List<Product> findAll(){
        return productRepos.findAll();
    }

    public Product update(Long id, Product updatedProduct) {
        Product product = productRepos.findById(id) //abändern - funktioniert nicht, braucht get() oder .Else 

        product.setName(updatedProduct.getName());
        product.setPrice(updatedProduct.getPrice());
        product.setDescription(updatedProduct.getDescription());
        product.setCategory(updatedProduct.getCategory());
        product.setQuantity(updatedProduct.getQuantity());
        product.setSize(updatedProduct.getSize());

        return productRepos.save(product);
    }



}
