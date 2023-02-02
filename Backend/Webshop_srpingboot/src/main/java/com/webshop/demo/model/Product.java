package com.webshop.demo.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity(name = "Product")
public class Product {

    @Positive
    @Id //gibt Primärschlüssel an
    @GeneratedValue //id wird automatisch von DB generiert
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "product_name")
    private String name;

    @NotBlank
    @Column(name = "price")
    private Double price;

    @Column(name = "description")
    private String description;

    @NotBlank
    @Column(name = "quantity")
    private Integer quantity;

    @NotBlank
    @Column(name = "size")
    private Character size;

    @NotBlank
    @Column(name = "category")
    private String category;


    // CONSTRUCTOR


    public Product(String name, double price, String description, int quantity, char size, String category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
        this.size = size;
        this.category = category;
    }
    

    public Product() {
    }


    // GETTERS & SETTERS


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return this.id;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public char getSize() {
        return this.size;
    }

    public void setSize(char size) {
        this.size = size;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


}
