package com.testtask.restaurant.controler;


import com.testtask.restaurant.service.IngredientService;
import com.testtask.restaurant.transfer.IngredientTO;
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
@RequestMapping("/ingredient")
@Tag(name = "Ingredient", description = "Ingredient API")
public class IngredientController {

    private final IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/")
    @Operation(summary = "Get ingredients")
    public List<IngredientTO> getIngredients(){
        return ingredientService.getIngredients();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a ingredient by id")
    public IngredientTO getIngredientById(@PathVariable("id") int ingredientId){
        return ingredientService.getIngredientById(ingredientId);
    }

    @PostMapping("/")
    @Operation(summary = "Add a new ingredient")
    public IngredientTO addIngredient(@RequestBody IngredientTO ingredientTO) {
        return ingredientService.addIngredient(ingredientTO);
    }

    @PutMapping("/")
    @Operation(summary = "Edit an existing ingredient")
    public IngredientTO editIngredient(@RequestBody IngredientTO ingredientTO) {
        return ingredientService.editIngredient(ingredientTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove ingredient by id")
    public void removeIngredient(@PathVariable("id") int ingredientId) {
        ingredientService.deleteIngredient(ingredientId);
    }

}
