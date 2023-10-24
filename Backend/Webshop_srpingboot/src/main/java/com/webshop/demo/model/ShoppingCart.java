package com.webshop.demo.model;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.validator.internal.util.stereotypes.Lazy;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.webshop.demo.dto.ShoppingCartDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity(name = "shoppingCart")
public class ShoppingCart {

    @Id // gibt Primärschlüssel an
    @GeneratedValue // id wird automatisch von DB generiert
    @Column(name = "id")
    private Long id;

    @OneToOne(mappedBy = "shoppingCart")
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Position> positions;

    // GETTER & SETTER

    // public Long getId() {
    // return id;
    // }

    // public User getUser() {
    // return user;
    // }

    // public void setUser(User user) {
    // this.user = user;
    // }

    // public List<Position> getPositions() {
    // return positions;
    // }

    // public void setPositions(List<Position> positions) {
    // this.positions = positions;
    // }

    public ShoppingCart() {
    }

    public ShoppingCart(Long id, User user, Set<Position> positions) {
        this.id = id;
        this.user = user;
        this.positions = positions;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Position> getPositions() {
        return this.positions;
    }

    public void setPositions(Set<Position> positions) {
        this.positions = positions;
    }

    public ShoppingCart user(User user) {
        setUser(user);
        return this;
    }

    public ShoppingCart positions(Set<Position> positions) {
        setPositions(positions);
        return this;
    }

    public ShoppingCartDto convertToDto() {
        ShoppingCartDto dto = new ShoppingCartDto();
        dto.setId(this.id);
        dto.setPositions(this.getPositions().stream()
        .map(Position::convertToDto)
        .collect(Collectors.toList()));   return dto;

    }

    public void addPosition(Position position) {
        if (this.positions == null) {
            setPositions(new HashSet<>());
        } 
        if (!this.positions.contains(position)) {
            this.positions.add(position);
        }
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
