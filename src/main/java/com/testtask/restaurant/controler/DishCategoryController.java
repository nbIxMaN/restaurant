package com.testtask.restaurant.controler;


import com.testtask.restaurant.service.DishCategoryService;
import com.testtask.restaurant.transfer.DishCategoryTO;
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
@RequestMapping("/dish_category")
@Tag(name = "Dish category", description = "Dish category API")
public class DishCategoryController {

    private final DishCategoryService dishCategoryService;

    @Autowired
    public DishCategoryController(DishCategoryService dishCategoryService) {
        this.dishCategoryService = dishCategoryService;
    }

    @GetMapping("/")
    @Operation(summary = "Get dish categories")
    public List<DishCategoryTO> getDishCategories(){
        return dishCategoryService.getDishCategories();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a dish category by id")
    public DishCategoryTO getDishCategories(@PathVariable("id") int dishCategoryId){
        return dishCategoryService.getDishCategoryById(dishCategoryId);
    }

    @PostMapping("/")
    @Operation(summary = "Add a new dish category")
    public DishCategoryTO addDishCategory(@RequestBody DishCategoryTO dishCategory) {
        return dishCategoryService.addDishCategory(dishCategory);
    }

    @PutMapping("/")
    @Operation(summary = "Edit an existing dish category")
    public DishCategoryTO editDishCategory(@RequestBody DishCategoryTO dishCategory) {
        return dishCategoryService.editDishCategory(dishCategory);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove dish category by id")
    public void removeDishCategory(@PathVariable("id") int dishCategoryId) {
        dishCategoryService.deleteDishCategory(dishCategoryId);
    }

}
