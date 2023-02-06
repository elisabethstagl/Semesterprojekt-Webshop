package com.webshop.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webshop.demo.model.TestUsers;

@Repository
public interface TestUserRepository extends JpaRepository<TestUsers, Long>{
    
}
