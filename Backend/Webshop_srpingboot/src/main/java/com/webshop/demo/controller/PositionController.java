package com.webshop.demo.controller;

// import javax.validation.constraints.PositiveOrZero;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.webshop.demo.dto.PositionDTO;
import com.webshop.demo.model.Position;
import com.webshop.demo.service.PositionService;

@RestController
@RequestMapping("/positions")
public class PositionController {
    
    private final PositionService positionService;

    public PositionController(PositionService positionService){
        this.positionService = positionService;
    }

    //Methoden

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Position createPosition(@RequestBody PositionDTO positionDTO){
        return positionService.save(fromDTO(positionDTO), 0L, positionDTO.getProductId()); //abändern weil id 0L
    }

    private static Position fromDTO(PositionDTO positionDTO){
        return new Position(positionDTO.getId(), positionDTO.getQuantity());
    }

}
