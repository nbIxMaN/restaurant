package com.testtask.restaurant.repository;

import com.testtask.restaurant.entity.PositionDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<PositionDAO, Integer> {

    boolean existsByIdOrName(int id, String name);

    boolean existsByNameAndIdNot(String name, int id);

}
