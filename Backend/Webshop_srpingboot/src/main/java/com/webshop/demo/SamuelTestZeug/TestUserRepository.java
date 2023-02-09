package com.webshop.demo.SamuelTestZeug;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestUserRepository extends JpaRepository<TestUsers, Long>{
    
}
