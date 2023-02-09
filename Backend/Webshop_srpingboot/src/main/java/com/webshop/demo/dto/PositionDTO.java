package com.webshop.demo.dto;

// import com.webshop.demo.model.Position;

// DTO for {@link Position}

/*DTO (Data Transfer Object): Ein DTO ist ein Objekt, das verwendet wird, 
um Daten zwischen verschiedenen Schichten einer Anwendung zu übertragen.
Es enthält die Daten, die für eine bestimmte Anfrage oder Antwort benötigt werden, 
und dient als Container für diese Daten.
*/

public class PositionDTO {
    
    private Long id;
    private Long productId;
    private Integer quantity;

    // GETTERS & SETTERS


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return this.productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
