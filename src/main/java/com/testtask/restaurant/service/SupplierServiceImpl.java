package com.testtask.restaurant.service;

import com.testtask.restaurant.convertion.SupplierConversion;
import com.testtask.restaurant.entity.IngredientDAO;
import com.testtask.restaurant.entity.SupplierDAO;
import com.testtask.restaurant.repository.IngredientRepository;
import com.testtask.restaurant.repository.SupplierRepository;
import com.testtask.restaurant.transfer.SupplierTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final IngredientRepository ingredientRepository;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, IngredientRepository ingredientRepository){
        this.supplierRepository = supplierRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    @Transactional
    public List<SupplierTO> getSuppliers() {
        return supplierRepository.findAll()
                .stream()
                .map(SupplierConversion::convertToTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SupplierTO getSupplierById(int supplierId) {
        return supplierRepository.findById(supplierId)
                .map(SupplierConversion::convertToTO)
                .orElse(null);
    }

    @Override
    @Transactional
    public SupplierTO addSupplier(SupplierTO supplier) {
        if (supplierRepository.existsByIdOrName(supplier.getId(), supplier.getName())) {
            throw new IllegalArgumentException(
                    String.format("Supplier with id = %d or name = %s is already created",
                            supplier.getId(),
                            supplier.getName()));
        }
        return SupplierConversion.convertToTO(saveSupplier(supplier));
    }

    @Override
    @Transactional
    public SupplierTO editSupplier(SupplierTO supplier) {
        if (!supplierRepository.existsById(supplier.getId())) {
            throw new IllegalArgumentException(
                    String.format("ingredient with id = %d is non created",
                            supplier.getId()));
        }
        //Checking if another id has same name
        if (supplierRepository.existsByNameAndIdNot(supplier.getName(), supplier.getId())) {
            throw new IllegalArgumentException(
                    String.format("ingredient with name = %s is already created",
                            supplier.getName()));
        }
        return SupplierConversion.convertToTO(saveSupplier(supplier));
    }

    private SupplierDAO saveSupplier(SupplierTO supplier) {
        Set<IngredientDAO> ingredientDAOS = Optional.ofNullable(supplier.getIngredientIds())
                .map(ingredientIds -> ingredientIds.stream()
                        .map(this::getIngredientDAO)
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
        SupplierDAO supplierDAO = SupplierConversion.convertToDTO(supplier, ingredientDAOS);
        return supplierRepository.save(supplierDAO);
    }

    @Override
    @Transactional
    public void deleteSupplier(int supplierId) {
        supplierRepository.deleteById(supplierId);
    }

    private IngredientDAO getIngredientDAO(int ingredientId) {
        Optional<IngredientDAO> ingredientDAO = ingredientRepository.findById(ingredientId);
        return ingredientDAO.orElseThrow(() -> new IllegalArgumentException(
                String.format("Ingredient with id %d not found", ingredientId)
        ));
    }
}
