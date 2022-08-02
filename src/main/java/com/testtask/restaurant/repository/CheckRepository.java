package com.testtask.restaurant.repository;

import com.testtask.restaurant.entity.CheckDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckRepository extends JpaRepository<CheckDAO, Integer> {

}
