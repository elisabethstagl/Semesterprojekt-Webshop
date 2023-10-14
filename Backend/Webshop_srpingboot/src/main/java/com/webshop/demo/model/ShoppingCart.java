package com.webshop.demo.model;

import java.util.ArrayList;
import java.util.List;

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
    

     // CONSTRUCTOR

    public ShoppingCart() {
        // Default constructor for JPA
    }


    public ShoppingCart(User user) {
        this.user = user;
        this.positions = new ArrayList<>();
    }

    //RELATIONSHIP TABLES
    

    @Id //gibt Primärschlüssel an
    @GeneratedValue //id wird automatisch von DB generiert
    @Column(name ="id")
    private Long id;

    @ManyToOne
    @JoinColumn(name ="user_id", nullable = true)
    private User user;

    @OneToMany(mappedBy = "shoppingCart")
    @JsonBackReference
    private List<Position> positions;


    //GETTER & SETTER

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

}
