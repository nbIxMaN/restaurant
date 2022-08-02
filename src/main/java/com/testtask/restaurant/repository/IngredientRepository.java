package com.testtask.restaurant.repository;

import com.testtask.restaurant.entity.IngredientDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<IngredientDAO, Integer> {

    boolean existsByIdOrName(int id, String name);

    boolean existsByNameAndIdNot(String name, int id);

}
