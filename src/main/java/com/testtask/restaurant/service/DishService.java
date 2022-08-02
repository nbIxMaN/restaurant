package com.testtask.restaurant.service;

import com.testtask.restaurant.transfer.DishTO;

import java.util.List;

public interface DishService {

    List<DishTO> getDishes();

    DishTO getDishById(int dishId);

    DishTO addDish(DishTO dish);

    DishTO editDish(DishTO dish);

    void deleteDish(int dishId);

}
