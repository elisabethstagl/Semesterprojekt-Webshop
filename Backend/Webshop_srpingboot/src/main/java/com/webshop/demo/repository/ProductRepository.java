package com.webshop.demo.repository;

import org.springframework.data.repository.CrudRepository;
import com.webshop.demo.model.Product;


public interface ProductRepository extends CrudRepository<Product, Long> {
 
    
}
