package com.webshop.demo.controller;

// import javax.validation.constraints.PositiveOrZero;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webshop.demo.service.PositionService;

/*Ein Controller ist eine Schicht in der Anwendungsarchitektur, 
die als Schnittstelle zwischen der Benutzeroberfläche und dem Backend dient. 
Es empfängt Anfragen von der Benutzeroberfläche und entscheidet, wie diese Anfragen verarbeitet werden sollen.
*/

@CrossOrigin
@RestController
@RequestMapping("/positions")
public class PositionController {
    
    private final PositionService positionService;

    public PositionController(PositionService positionService){
        this.positionService = positionService;
    }

    //Methoden

    // @PostMapping
    // @ResponseStatus(HttpStatus.CREATED)
    // public Position createPosition(@RequestBody PositionDTO positionDTO) {
    //     Position position = new Position();
    //     position.setQuantity(positionDTO.getQuantity());
    //     return positionService.save(position, positionDTO.getId(), positionDTO.getProductId());
    // }


}
