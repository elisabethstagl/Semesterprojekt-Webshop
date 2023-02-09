package com.webshop.demo.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity(name = "shoppingCart")
public class ShoppingCart {
    

    public ShoppingCart(User user) {
        
    }

    @Id
    @GeneratedValue
    @Column(name ="id")
    private Long id;

    @ManyToOne
    @JoinColumn(name ="user_id", nullable = true)
    private User user;

    @OneToMany(mappedBy = "shoppingCart")
    @JsonBackReference
    private Set<Position> positions;
}
