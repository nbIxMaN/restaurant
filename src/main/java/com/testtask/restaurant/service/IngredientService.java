package com.testtask.restaurant.service;

import com.testtask.restaurant.transfer.IngredientTO;

import java.util.List;

public interface IngredientService {

    List<IngredientTO> getIngredients();

    IngredientTO getIngredientById(int ingredientId);

    IngredientTO addIngredient(IngredientTO ingredient);

    IngredientTO editIngredient(IngredientTO ingredient);

    void deleteIngredient(int ingredientId);

}
