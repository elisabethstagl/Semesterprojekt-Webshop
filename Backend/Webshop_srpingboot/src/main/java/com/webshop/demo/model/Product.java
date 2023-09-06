package com.webshop.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity(name = "Product")
public class Product {

    @Positive
    @Id // gibt Primärschlüssel an
    @GeneratedValue // id wird automatisch von DB generiert
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "product_name")
    private String name;

    @NotNull
    @Column(name = "price")
    private Double price;

    @NotBlank
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "quantity")
    private Integer quantity;

    @NotNull
    @Column(name = "size")
    private Character size;

    @NotBlank
    @Column(name = "category")
    private String category;

    @Column(name = "image_url")
    private String imageUrl;

    // CONSTRUCTOR

    public Product(String name, double price, String description, int quantity, Character size, String category, String imageUrl) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
        this.size = size;
        this.category = category;
        this.imageUrl = imageUrl;
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

    public Character getSize() {
        return this.size;
    }

    public void setSize(Character size) {
        this.size = size;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    

}
