package com.webshop.demo.repository;

import org.springframework.data.repository.CrudRepository;
import com.webshop.demo.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    
}
