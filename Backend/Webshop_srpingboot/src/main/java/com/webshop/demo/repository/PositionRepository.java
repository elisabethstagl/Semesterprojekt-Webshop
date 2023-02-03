package com.webshop.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webshop.demo.model.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    
}
