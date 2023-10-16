package com.webshop.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/*  Model ist verantwortlich für die Daten.
    Verbundstabelle. 
*/

@Entity(name = "position")
public class Position {
    
    @Id //gibt Primärschlüssel an
    @GeneratedValue //id wird automatisch von DB generiert
    @Column(name = "position_id")
    private Long positionId;

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

    public Position(Long positionId, Integer quantity, ShoppingCart shoppingCart, Product product) {
        this.positionId = positionId;
        this.quantity = quantity;
        this.shoppingCart = shoppingCart;
        this.product = product;
    }


    // GETTERS & SETTERS


    // public Long getPositionId() {
    //     return this.positionId;
    // }

    // public Integer getQuantity() {
    //     return this.quantity;
    // }

    // public void setQuantity(Integer quantity) {
    //     this.quantity = quantity;
    // }

    // public ShoppingCart getShoppingCart() {
    //     return this.shoppingCart;
    // }

    // public void setShoppingCart(ShoppingCart shoppingCart) {
    //     this.shoppingCart = shoppingCart;
    // }

    // public Product getProduct() {
    //     return this.product;
    // }

    // public void setProduct(Product product) {
    //     this.product = product;
    // }

    public Long getPositionId() {
        return this.positionId;
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
