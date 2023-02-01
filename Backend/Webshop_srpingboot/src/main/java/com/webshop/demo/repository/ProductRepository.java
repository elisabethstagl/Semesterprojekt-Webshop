package com.webshop.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webshop.demo.model.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    

}
