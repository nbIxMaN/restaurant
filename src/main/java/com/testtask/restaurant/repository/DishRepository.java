package com.testtask.restaurant.repository;

import com.testtask.restaurant.entity.DishDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<DishDAO, Integer> {

    boolean existsByIdOrName(int id, String name);

    boolean existsByNameAndIdNot(String name, int id);

}
