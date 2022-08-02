package com.testtask.restaurant.repository;

import com.testtask.restaurant.entity.OrderDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderDAO, Integer> {

}
