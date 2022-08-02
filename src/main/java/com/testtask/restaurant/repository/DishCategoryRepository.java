package com.testtask.restaurant.repository;

import com.testtask.restaurant.entity.DishCategoryDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishCategoryRepository extends JpaRepository<DishCategoryDAO, Integer> {

    boolean existsByIdOrName(int id, String name);

    boolean existsByNameAndIdNot(String name, int id);

}
