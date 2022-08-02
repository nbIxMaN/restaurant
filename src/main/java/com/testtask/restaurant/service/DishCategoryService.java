package com.testtask.restaurant.service;

import com.testtask.restaurant.transfer.DishCategoryTO;

import java.util.List;

public interface DishCategoryService {

    List<DishCategoryTO> getDishCategories();

    DishCategoryTO getDishCategoryById(int dishCategoryId);

    DishCategoryTO addDishCategory(DishCategoryTO dishCategory);

    DishCategoryTO editDishCategory(DishCategoryTO dishCategory);

    void deleteDishCategory(int dishCategoryId);

}
