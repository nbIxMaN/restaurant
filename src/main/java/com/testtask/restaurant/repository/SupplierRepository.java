package com.testtask.restaurant.repository;

import com.testtask.restaurant.entity.SupplierDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<SupplierDAO, Integer> {

    boolean existsByIdOrName(int id, String name);

    boolean existsByNameAndIdNot(String name, int id);

}
