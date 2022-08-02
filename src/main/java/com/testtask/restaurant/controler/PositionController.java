package com.testtask.restaurant.controler;


import com.testtask.restaurant.service.PositionService;
import com.testtask.restaurant.transfer.PositionTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/position")
@Tag(name = "Position", description = "Position API")
public class PositionController {

    private final PositionService positionService;

    @Autowired
    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @GetMapping("/")
    @Operation(summary = "Get positions")
    public List<PositionTO> getPositions(){
        return positionService.getPositions();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a position by id")
    public PositionTO getPositions(@PathVariable("id") int positionId){
        return positionService.getPositionById(positionId);
    }

    @PostMapping("/")
    @Operation(summary = "Add a new position")
    public PositionTO addPosition(@RequestBody PositionTO positionTO) {
        return positionService.addPosition(positionTO);
    }

    @PutMapping("/")
    @Operation(summary = "Edit an existing position")
    public PositionTO editPosition(@RequestBody PositionTO positionTO) {
        return positionService.editPosition(positionTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove position by id")
    public void removePosition(@PathVariable("id") int positionId) {
        positionService.deletePosition(positionId);
    }

}
