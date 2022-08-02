package com.testtask.restaurant.controler;


import com.testtask.restaurant.service.DishService;
import com.testtask.restaurant.transfer.DishTO;
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
@RequestMapping("/dish")
@Tag(name = "Dish", description = "Dish API")
public class DishController {

    private final DishService dishService;

    @Autowired
    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/")
    @Operation(summary = "Get dishes")
    public List<DishTO> getDishes(){
        return dishService.getDishes();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a dish by id")
    public DishTO getDishById(@PathVariable("id") int dishCategoryId){
        return dishService.getDishById(dishCategoryId);
    }

    @PostMapping("/")
    @Operation(summary = "Add a new dish")
    public DishTO addDish(@RequestBody DishTO dishCategory) {
        return dishService.addDish(dishCategory);
    }

    @PutMapping("/")
    @Operation(summary = "Edit an existing dish")
    public DishTO editDish(@RequestBody DishTO dishCategory) {
        return dishService.editDish(dishCategory);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove dish by id")
    public void removeDish(@PathVariable("id") int dishCategoryId) {
        dishService.deleteDish(dishCategoryId);
    }

}
