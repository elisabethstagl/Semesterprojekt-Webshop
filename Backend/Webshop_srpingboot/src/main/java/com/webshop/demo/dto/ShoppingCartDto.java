package com.webshop.demo.dto;

import com.webshop.demo.model.User;

import java.util.List;

public class ShoppingCartDto {

    private Long id;

    private List<PositionDTO> positions;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public List<PositionDTO> getPositions() {
        return positions;
    }

    public void setPositions(List<PositionDTO> positions) {
        this.positions = positions;
    }
}
