package com.webshop.demo.dto;

import com.webshop.demo.model.Product;

// DTO for {@link Product}

/*DTO (Data Transfer Object): Ein DTO ist ein Objekt, das verwendet wird, 
um Daten zwischen verschiedenen Schichten einer Anwendung zu übertragen.
Es enthält die Daten, die für eine bestimmte Anfrage oder Antwort benötigt werden, 
und dient als Container für diese Daten.
*/

public class ProductDTO {

    private Long id;
    private String name;
    private Double price;
    private String description;
    private Integer quantity;
    private Character size;
    private String category;


    // GETTERS & SETTERS    


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
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

}
