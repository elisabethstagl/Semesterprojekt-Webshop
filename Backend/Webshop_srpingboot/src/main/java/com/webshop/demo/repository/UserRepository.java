package com.webshop.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.webshop.demo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    
}
