package com.testtask.restaurant.repository;

import com.testtask.restaurant.entity.EmployeeDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<EmployeeDAO, Integer> {

}
