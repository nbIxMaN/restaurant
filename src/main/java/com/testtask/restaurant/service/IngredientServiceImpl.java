package com.testtask.restaurant.service;

import com.testtask.restaurant.convertion.IngredientConversion;
import com.testtask.restaurant.entity.IngredientDAO;
import com.testtask.restaurant.entity.SupplierDAO;
import com.testtask.restaurant.repository.IngredientRepository;
import com.testtask.restaurant.repository.SupplierRepository;
import com.testtask.restaurant.transfer.IngredientTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final SupplierRepository supplierRepository;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository, SupplierRepository supplierRepository){
        this.ingredientRepository = ingredientRepository;
        this.supplierRepository = supplierRepository;
    }

    @Override
    @Transactional
    public List<IngredientTO> getIngredients() {
        return ingredientRepository.findAll()
                .stream()
                .map(IngredientConversion::convertToTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public IngredientTO getIngredientById(int ingredientId) {
        return ingredientRepository.findById(ingredientId)
                .map(IngredientConversion::convertToTO)
                .orElse(null);
    }

    @Override
    @Transactional
    public IngredientTO addIngredient(IngredientTO ingredient) {
        if (ingredientRepository.existsByIdOrName(ingredient.getId(), ingredient.getName())) {
            throw new IllegalArgumentException(
                    String.format("Ingredient with id = %d or name = %s is already created",
                            ingredient.getId(),
                            ingredient.getName()));
        }
        return IngredientConversion.convertToTO(saveIngredient(ingredient));
    }

    @Override
    @Transactional
    public IngredientTO editIngredient(IngredientTO ingredient) {
        if (!ingredientRepository.existsById(ingredient.getId())) {
            throw new IllegalArgumentException(
                    String.format("ingredient with id = %d is non created",
                            ingredient.getId()));
        }
        //Checking if another id has same name
        if (ingredientRepository.existsByNameAndIdNot(ingredient.getName(), ingredient.getId())) {
            throw new IllegalArgumentException(
                    String.format("ingredient with name = %s is already created",
                            ingredient.getName()));
        }
        return IngredientConversion.convertToTO(saveIngredient(ingredient));
    }

    private IngredientDAO saveIngredient(IngredientTO ingredient) {
        Set<SupplierDAO> supplierDAOS = Optional.ofNullable(ingredient.getSupplierIds())
                .map(ingredientIds -> ingredientIds.stream()
                        .map(this::getSupplierDAO)
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
        IngredientDAO ingredientDAO = IngredientConversion.convertToDTO(ingredient, supplierDAOS);
        return ingredientRepository.save(ingredientDAO);
    }

    @Override
    @Transactional
    public void deleteIngredient(int ingredientId) {
        ingredientRepository.deleteById(ingredientId);
    }

    private SupplierDAO getSupplierDAO(int supplierId) {
        Optional<SupplierDAO> supplierDAO = supplierRepository.findById(supplierId);
        return supplierDAO.orElseThrow(() -> new IllegalArgumentException(
                String.format("Supplier with id %d not found", supplierId)
        ));
    }
}
