package com.webshop.demo.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity(name = "shoppingCart")
public class ShoppingCart {
    

    //  // CONSTRUCTOR

    // public ShoppingCart() {
    //     // Default constructor for JPA
    // }


    // public ShoppingCart(User user) {
    //     this.user = user;
    //     this.positions = new ArrayList<>();
    // }

    //RELATIONSHIP TABLES
    

    @Id //gibt Primärschlüssel an
    @GeneratedValue //id wird automatisch von DB generiert
    @Column(name ="id")
    private Long id;

    @OneToOne
    @JoinColumn(name ="user_id", nullable = true)
    private User user;

    @OneToMany(mappedBy = "shoppingCart")
    // @JsonBackReference
    private List<Position> positions;


    //GETTER & SETTER

    // public Long getId() {
    //     return id;
    // }

    // public User getUser() {
    //     return user;
    // }

    // public void setUser(User user) {
    //     this.user = user;
    // }

    // public List<Position> getPositions() {
    //     return positions;
    // }

    // public void setPositions(List<Position> positions) {
    //     this.positions = positions;
    // }


    public ShoppingCart() {
    }

    public ShoppingCart(Long id, User user, List<Position> positions) {
        this.id = id;
        this.user = user;
        this.positions = positions;
    }

    public Long getId() {
        return this.id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Position> getPositions() {
        return this.positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    public ShoppingCart user(User user) {
        setUser(user);
        return this;
    }

    public ShoppingCart positions(List<Position> positions) {
        setPositions(positions);
        return this;
    }


    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", user='" + getUser() + "'" +
            ", positions='" + getPositions() + "'" +
            "}";
    }



}
