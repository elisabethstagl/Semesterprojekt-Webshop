package com.webshop.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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

    @NotBlank
    @Column(name = "category")
    private String category;

    @Lob
    @Column(name = "product_img", length = 100000)
    private byte[] product_img;


    // Konstruktor
    public Product() {
    }

    public Product(String name, Double price, String description, Integer quantity, String category, byte[] product_img) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
        this.category = category;
        this.product_img = product_img;
    }

    // Getter und Setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getProduct_img() {
        return product_img;
    }

    public void setProduct_img(byte[] product_img) {
        this.product_img = product_img;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String productName) {
        this.name = productName;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double productPrice) {
        this.price = productPrice;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String productDescription) {
        this.description = productDescription;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer productQuantity) {
        this.quantity = productQuantity;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String productCategory) {
        this.category = productCategory;
    }

    
}
