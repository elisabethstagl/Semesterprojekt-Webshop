package com.webshop.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;

/*  Model ist verantwortlich für die Daten.
    Verbundstabelle. 
*/

@Entity(name = "position")
public class Position {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "position_seq_generator")
    @SequenceGenerator(name = "position_seq_generator", sequenceName = "position_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "shoppingCart_id", nullable = false)
    private ShoppingCart shoppingCart;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // CONSTRUCTOR  

    public Position() {
        //default constructor for jpa
    }

    public Position(Integer quantity, ShoppingCart shoppingCart, Product product) {
        this.quantity = quantity;
        this.shoppingCart = shoppingCart;
        this.product = product;
    }

    public Long getId() {
        return this.id;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ShoppingCart getShoppingCart() {
        return this.shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Position quantity(Integer quantity) {
        setQuantity(quantity);
        return this;
    }

    public Position shoppingCart(ShoppingCart shoppingCart) {
        setShoppingCart(shoppingCart);
        return this;
    }

    public Position product(Product product) {
        setProduct(product);
        return this;
    }


}
